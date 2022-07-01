package com.curuza.data.photos;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amplifyframework.core.Amplify;
import com.curuza.data.MainDatabase;
import com.curuza.utils.FileUtils;

import java.io.File;

import io.reactivex.Completable;
import io.reactivex.Single;

public class PhotoRepository {
  private static final String DBG_TAG = PhotoRepository.class.getSimpleName();

  private Context mContext;
  private MainDatabase mDatabase;


  // UPLOAD OPERATIONS

  public PhotoRepository(Context context) {
    mContext = context;

  }

  public Completable saveProductPhoto(String productId, Uri photoUri) {
    return savePhotoLocally(productId, PhotoType.PRODUCT_PHOTO, photoUri).ignoreElement()
        .andThen(uploadPhotoUpstream(productId, PhotoType.PRODUCT_PHOTO, photoUri));
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
