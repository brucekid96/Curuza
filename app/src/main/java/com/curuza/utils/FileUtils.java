package com.curuza.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileUtils {

  private static final String DBG_TAG = FileUtils.class.getSimpleName();
  private static final int MAX_CACHE_DIR_SIZE_IN_BYTES = 3 * 1024 * 1024;

  /**
   * A helper method that copies content between two files
   *
   * @param srcFile  the source file to read from
   * @param destFile the destination file to write to
   */
  public static void copy(File srcFile, File destFile) {
    try (FileInputStream srcStream = new FileInputStream(srcFile)) {
      try (FileOutputStream destStream = new FileOutputStream(destFile)) {
        FileChannel srcChannel = srcStream.getChannel();
        FileChannel destChannel = destStream.getChannel();

        long bytesTransferred = srcChannel.transferTo(0, srcChannel.size(), destChannel);
        Log.d(DBG_TAG, "File copying complete: " + bytesTransferred + " bytes transferred");
      }
    } catch (Exception e) {
      Log.d(DBG_TAG, e.toString());
    }
  }

  /**
   * A helper method that moves content between two files, copying the content to the
   * destination file and then deleting the source file
   *
   * @param srcFile  the source file to read from
   * @param destFile the destination file to write to.
   */
  public static void move(File srcFile, File destFile) {
    copy(srcFile, destFile);
    srcFile.delete();
  }

  public static void deleteDirectory(File fileOrDirectory) {
    if (fileOrDirectory.isDirectory()) {
      for (File child : fileOrDirectory.listFiles()) {
        deleteDirectory(child);
      }
    }

    fileOrDirectory.delete();
  }

  public static void clearCacheDirIfNecessary(Context context) {
    File cacheDir = context.getCacheDir();
    if (cacheDir.length() > MAX_CACHE_DIR_SIZE_IN_BYTES) {
      org.apache.commons.io.FileUtils.deleteQuietly(cacheDir);
    }
  }
}
