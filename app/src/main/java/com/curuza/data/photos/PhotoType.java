package com.curuza.data.photos;

public enum PhotoType {
  USER_PROFILE_PHOTO("/user_profile_photos", "user_profile_photos/", 200, 200),
  PRODUCT_PHOTO("/product_photos","product_photos/",500,500),
  PRODUCT_THUMBNAIL("/product_thumbnails","product_thumbnails/",200,200);

  private String mLocalDirPath;
  private String mS3Prefix;
  private int mWidth;
  private int mHeight;

  PhotoType(String LocalDirPath, String S3Prefix, int Width, int Height) {
    this.mLocalDirPath = LocalDirPath;
    this.mS3Prefix = S3Prefix;
    this.mWidth = Width;
    this.mHeight = Height;
  }

  public String getLocalDirPath() {
    return mLocalDirPath;
  }

  public String getS3Prefix() {
    return mS3Prefix;
  }

  public int getWidth() {
    return mWidth;
  }

  public int getHeight() {
    return mHeight;
  }
}
