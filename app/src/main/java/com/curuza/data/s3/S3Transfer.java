package com.curuza.data.s3;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.threeten.bp.ZonedDateTime;


@Entity(
    tableName = "s3_transfer",
    primaryKeys = {"photo_id"})
public class S3Transfer {

  @NonNull
  @ColumnInfo(name = "photo_id")
  private String photoId;

  @ColumnInfo(name = "added_at")
  private String addedAt;

  @NonNull
  @ColumnInfo(name = "status")
  private S3TransferState status;



  public S3Transfer(String photoId,S3TransferState status) {
    this.photoId = photoId;
    this.addedAt = ZonedDateTime.now().toInstant().toString();
    this.status = status;
  }

  public String getPhotoId() {
    return photoId;
  }

  public void setPhotoId(String photoId) {
    this.photoId = photoId;
  }

  public String getAddedAt() {
    return addedAt;
  }

  public void setAddedAt(String addedAt) {
    this.addedAt = addedAt;
  }

  public S3TransferState getStatus() {
    return status;
  }

  public void setStatus(S3TransferState status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "S3Transfer{" +
        "photoId='" + photoId + '\'' +
        ", addedAt='" + addedAt + '\'' +
        ", status=" + status +
        '}';
  }
}
