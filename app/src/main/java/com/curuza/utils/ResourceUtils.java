package com.curuza.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

public class ResourceUtils {

    public static Uri getResourceUri(Context context, int resourceId) {
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(context.getResources().getResourcePackageName(resourceId))
                .appendPath(context.getResources().getResourceTypeName(resourceId))
                .appendPath(context.getResources().getResourceEntryName(resourceId))
                .build();
    }
}
