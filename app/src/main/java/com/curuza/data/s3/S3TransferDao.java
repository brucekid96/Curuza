package com.curuza.data.s3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class S3TransferDao {

  @Query("select * from s3_transfer  ")
  public abstract Single<List<S3Transfer>> getS3Transfers();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract Completable addTransfer(S3Transfer s3Transfer);

  @Query("delete from s3_transfer where photo_id = :photoId")
  public abstract Completable removeTransfer(String photoId);

  @Query("SELECT * from  s3_transfer where status= 'Queued'")
   public abstract  Single <List<S3Transfer>> getQueuedTransfers();

  @Query("UPDATE s3_transfer set status= :transferStatus where photo_id = :photoId ")
  public abstract  Completable setTransferStatus(String photoId,S3TransferState transferStatus);

  @Query("UPDATE s3_transfer set status= :transferStatus where photo_id = :photoId ")
  public abstract void setTransferStatusSync(String photoId,S3TransferState transferStatus);
}
