package com.curuza.data.photos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(
    tableName = "photo_fetch_infos",
    primaryKeys = {"photo_id", "photo_type"})
public class PhotoFetchInfo {
  @NonNull
  @ColumnInfo(name = "photo_id")
  private String photoId;

  @NonNull
  @ColumnInfo(name = "photo_type")
  private PhotoType photoType;

  @ColumnInfo(name = "latest_fetch_timestamp")
  private String latestFetchTimeStamp;

  public PhotoFetchInfo(String photoId,PhotoType photoType,String latestFetchTimeStamp) {

    this.photoId = photoId;
    this.photoType = photoType;
    this.latestFetchTimeStamp = latestFetchTimeStamp;
  }

  public String getPhotoId() {
    return photoId;
  }

  public PhotoType getPhotoType() {
    return photoType;
  }

  public String getLatestFetchTimeStamp() {
    return latestFetchTimeStamp;
  }


}
