package com.curuza.data.s3;

import android.content.Context;
import android.util.Log;

import com.curuza.data.MainDatabase;
import com.curuza.data.photos.PhotoRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class S3TransferRepository {
  private static final String DBG_TAG = S3TransferRepository.class.getSimpleName();
  private MainDatabase db;
  private PhotoRepository mPhotoRepository;

  public S3TransferRepository(Context context) {
    db = MainDatabase.getDatabase(context);

  }

  public Single<List<S3Transfer>> getS3Transfers() {
      return db.s3TransferDao().getS3Transfers();
    }

  public Completable addTransfer(S3Transfer s3Transfer)  {
    Log.d(DBG_TAG, "S3Transfer: " + s3Transfer + " queued...");
    return db.s3TransferDao().addTransfer(s3Transfer);
  }

  public Completable removeTransfer(String photoId) {
    return db.s3TransferDao().removeTransfer(photoId);
  }

  public Single<List<S3Transfer>> getQueuedTransfers() {
    return db.s3TransferDao().getQueuedTransfers();
  }

  public Completable setTransferStatus(String photoId,S3TransferState transferStatus)  {
    Log.d(DBG_TAG, "Setting transfer " + photoId + " status to: " + transferStatus);
    return db.s3TransferDao().setTransferStatus(photoId,transferStatus);
  }

  public void setTransferStatusSync(String photoId, S3TransferState transferStatus) {
    db.s3TransferDao().setTransferStatusSync(photoId, transferStatus);
  }



  public Single<List<S3Transfer>> getPendingTransfers() {
    Log.d(DBG_TAG, "syncFailedPicts starting");
   return getQueuedTransfers();
  }






}
