package com.curuza.data.photos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public abstract class PhotoDao {

  @Query("select * from photo_fetch_infos where photo_id = :photoId and photo_type = :photoType")
  public abstract Maybe<PhotoFetchInfo> getPhotoFetchInfo(String photoId, PhotoType photoType);


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract Completable add(PhotoFetchInfo photoFetchInfo);

  @Query("delete from photo_fetch_infos where photo_id = :photoId")
  public abstract Completable deletePhotoFetchInfo(String photoId);

}
