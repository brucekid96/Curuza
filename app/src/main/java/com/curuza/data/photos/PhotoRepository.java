package com.curuza.data.photos;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.curuza.data.MainDatabase;
import com.curuza.data.s3.S3Transfer;
import com.curuza.data.s3.S3TransferRepository;
import com.curuza.data.s3.S3TransferState;
import com.curuza.domain.onboarding.auth.AuthService;
import com.curuza.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class PhotoRepository {
  private static final String DBG_TAG = PhotoRepository.class.getSimpleName();

  private Context mContext;
  private S3TransferRepository mS3TransferRepository;
  private MainDatabase mDatabase;


  // UPLOAD OPERATIONS

  public PhotoRepository(Context context) {
    mContext = context;
    mS3TransferRepository = new S3TransferRepository(context);

  }

  public Completable saveProductPhoto(String productId, Uri photoUri) {
    return savePhotoLocally(productId, PhotoType.PRODUCT_PHOTO, photoUri).ignoreElement()
        .andThen (uploadPhotoUpstream(productId, PhotoType.PRODUCT_PHOTO, photoUri));
  }

  public Completable saveProductThumbnail(String productId, Uri photoUri) {
    return   savePhotoLocally(productId, PhotoType.PRODUCT_THUMBNAIL, photoUri).ignoreElement()
        .andThen(uploadPhotoUpstream(productId, PhotoType.PRODUCT_THUMBNAIL, photoUri));
  }


  public Maybe<Uri> getOwnProfilePhotoUri() {
    return AuthService.getCurrentUser()
        .map(AuthUser::getUserId)
        .map(this::getUserProfilePhotoUri);
  }



  public Single<Uri> uploadUserProfilePhoto(Uri photoUri) {
    return AuthService.getCurrentUser()
        .map(AuthUser::getUserId)
        .flatMapSingle(userId ->
            uploadPhotoUpstream(userId, PhotoType.USER_PROFILE_PHOTO, photoUri)
                .andThen(savePhotoLocally(userId, PhotoType.USER_PROFILE_PHOTO, photoUri)));
  }

  public Uri getUserProfilePhotoUri(String userId) {
    return Uri.fromFile(getLocalPhotoFile(userId, PhotoType.USER_PROFILE_PHOTO));
  }

  public Completable removeOwnProfilePhoto() {
    return AuthService.getCurrentUser()
        .map(AuthUser::getUserId)
        .flatMapCompletable(userId -> removePhoto(userId, PhotoType.USER_PROFILE_PHOTO));
  }

  private Completable removePhoto(String photoId, PhotoType photoType) {
    return Completable.create(source ->
        Amplify.Storage.remove(
            getS3PhotoKey(photoId, photoType),
            result -> {
              // Delete local copy of profile photo, since it is no longer associated with the current user
              File localPhoto = getLocalPhotoFile(photoId, photoType);
              localPhoto.delete();
              Log.d(DBG_TAG, "Profile photo removal succeeded: S3 key = " + result.getKey());
              source.onComplete();
            },
            error -> Log.d(DBG_TAG, "Profile photo removal failed: " + error.toString())));
  }

  private Single<Uri> generateThumbnail(Uri coverPhotoUri, PhotoType thumbnailType) {
    return Single.create(source ->
        Glide.with(mContext.getApplicationContext())
            .asBitmap()
            .load(coverPhotoUri)
            .fitCenter()
            .into(new SimpleTarget<Bitmap>(
                thumbnailType.getWidth(),
                thumbnailType.getHeight()) {
              @Override
              public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                File tempFile = new File(mContext.getCacheDir(), UUID.randomUUID().toString() + ".jpg");
                try {
                  FileOutputStream outputStream = new FileOutputStream(tempFile);
                  resource.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
                  outputStream.flush();
                  outputStream.close();
                  source.onSuccess(Uri.fromFile(tempFile));
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }));
  }

  public Completable uploadProductPhoto(String productId, Uri photoUri) {
    return generateThumbnail(photoUri, PhotoType.PRODUCT_THUMBNAIL)
        .flatMapCompletable(thumbnailUri ->
            Completable.concatArray(
                Completable.mergeArray(
                    uploadPhotoUpstream(productId, PhotoType.PRODUCT_PHOTO, photoUri),
                    uploadPhotoUpstream(productId, PhotoType.PRODUCT_THUMBNAIL, thumbnailUri)),
                Completable.mergeArray(
                    savePhotoLocally(productId, PhotoType.PRODUCT_PHOTO, photoUri).ignoreElement(),
                    savePhotoLocally(productId, PhotoType.PRODUCT_THUMBNAIL, thumbnailUri).ignoreElement())


                .subscribeOn(Schedulers.io())
            ));
  }

  /**
   * Uploads a photo of a given type and id to S3 and saves a local copy of it upon upload completion
   *
   * @return The URI of the local copy of the uploaded photo
   */
  private Completable uploadPhotoUpstream(String photoId, PhotoType photoType, Uri selectedPhotoUri) {
    return Completable.create(source ->
        Amplify.Storage.uploadFile(
            getS3PhotoKey(photoId, photoType),
            new File(selectedPhotoUri.getPath()),
            result -> {
              Log.d(DBG_TAG, "Photo upload successful: S3 Key = " + result.getKey());
              source.onComplete();
            },
            error -> {
              Log.d(DBG_TAG, "Photo upload failed: " + error.getCause().toString());
              Log.d(DBG_TAG, "Selected photo URI: " + selectedPhotoUri.getPath());
              source.onError(error);
            }
        ));
  }

  public Completable uploadPendingPhoto(String photoId, PhotoType photoType, Uri selectedPhotoUri) {
    return Completable.create(source -> {
      Log.d(DBG_TAG, "Queued Transfer " + photoId + " upload to S3 starting...");
      Amplify.Storage.uploadFile(
          getS3PhotoKey(photoId, photoType),
          new File(selectedPhotoUri.getPath()),
          result -> {
            Log.d(DBG_TAG, "Photo upload successful: S3 Key = " + result.getKey());
            mS3TransferRepository.setTransferStatus(photoId, S3TransferState.Finished)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe();
            source.onComplete();
          },
          error -> {
            Log.d(DBG_TAG, "Photo upload failed: " + error.getCause().toString());
            Log.d(DBG_TAG, "Selected photo URI: " + selectedPhotoUri.getPath());
            mS3TransferRepository.setTransferStatus(photoId, S3TransferState.Queued)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
            source.onComplete();
          }
      );
        });
  }

  public Completable processPendingTransfer(S3Transfer s3Transfer) {
    return Completable.concatArray(
        mS3TransferRepository.setTransferStatus(s3Transfer.getPhotoId(),S3TransferState.Uploading),
        uploadPendingPhoto(s3Transfer.getPhotoId(), PhotoType.PRODUCT_PHOTO, getProductPhotoUri(s3Transfer.getPhotoId())));
  }

  /**
   * Saves the given photo locally
   *
   * @param photoId   the id of the photo to be saved
   * @param photoType the type of the photo to be saved
   * @return the Uri of the saved profile photo
   */
  private Single<Uri> savePhotoLocally(String photoId, PhotoType photoType, Uri latestPhotoUri) {
    return Single.create(source -> {
      Log.d(DBG_TAG, "saveProductPhotoLocally: New product photo URI: " + latestPhotoUri.toString());
      Log.d(DBG_TAG, "saveProductPhotoLocally: New product photo path: " + latestPhotoUri.getPath());

      File latestPhoto = new File(latestPhotoUri.getPath());
      File currentPhoto = getLocalPhotoFile(photoId, photoType);

      // Delete the old copy of the product picture if it exists
      if (currentPhoto.exists()) {
        boolean oldCopyDeleted = currentPhoto.delete();
        if (oldCopyDeleted) {
          Log.d(DBG_TAG, "Deleted stale copy of photo [" + photoId + ", " + photoType + "]");
        }
      }

      // Copy the latest photo into the current photo file
      com.curuza.utils.FileUtils.copy(latestPhoto, currentPhoto);
      Log.d(DBG_TAG, "Latest photo saved successfully to " + currentPhoto.getPath());
      source.onSuccess(Uri.fromFile(currentPhoto));
    });
  }
  

  // TODO Do photo existence check inside getProductPhotoUri
  /**
   * Retrieves the URI of the local file containing the product picture associated with productId
   */

  public Uri getProductPhotoUri(String productId) {
    return Uri.fromFile(getLocalPhotoFile(productId, PhotoType.PRODUCT_PHOTO));
  }

  public Uri getProductThumbnailUri(String productId) {
    return Uri.fromFile(getLocalPhotoFile(productId, PhotoType.PRODUCT_THUMBNAIL));
  }

  // UTILITY METHODS
  private File getLocalPhotoFile(String photoId, PhotoType photoType) {
    return new File(getLocalPhotoDirectory(mContext, photoType), photoId + ".jpg");
  }

  private File getTempPhotoFile(String photoId, PhotoType photoType) {
    return new File(getLocalPhotoDirectory(mContext, photoType), photoId + "_temp.jpg");
  }

  /**
   * Return the directory that contains product photo files (creating it if it didn't exist before)
   *
   * @return the local product photo directory
   */
  private static File getLocalPhotoDirectory(Context context, PhotoType photoType) {
    File localPhotoDir = new File(context.getFilesDir() + photoType.getLocalDirPath());
    if (!localPhotoDir.exists()) {
      localPhotoDir.mkdir();
    }

    return localPhotoDir;
  }

  private String getS3PhotoKey(String photoId, PhotoType photoType) {
    return photoType.getS3Prefix() + photoId + ".jpg";
  }

  public Completable fetchPhotoUpstream(String photoId, PhotoType photoType) {
    return Completable.create(source -> {
      File tempPhotoFile = getTempPhotoFile(photoId,photoType);
      File localPhotoFile = getLocalPhotoFile(photoId,photoType);

      Log.d(DBG_TAG,"photo" + photoId + "of type" + photoType + "downloading...");

      Amplify.Storage.downloadFile(
          getS3PhotoKey(photoId, photoType),
          tempPhotoFile,
          result -> {
            Log.d(DBG_TAG, "Photo" + photoId + "of type" + photoType + " successfully downloaded. Took" );
            FileUtils.move(tempPhotoFile, localPhotoFile);
            source.onComplete();
          },
          error -> {
            if (error.getCause() instanceof  AmazonS3Exception) {
              AmazonS3Exception cause = (AmazonS3Exception) (error.getCause());
              if (cause.getErrorCode().equals("No SuchKey")) {
                Log.d(DBG_TAG, "No photo" + photoId + "of type" + photoType + "found in S3. Took");
                org.apache.commons.io.FileUtils.deleteQuietly(localPhotoFile);
                source.onComplete();
              }
            }
          }
      );
    });
  }

}
