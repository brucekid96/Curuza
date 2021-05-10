package com.curuza.data.help;

import android.net.Uri;


public class Help {

    public static final String HELP_EXTRA = "help_code";

    private String mId;
    private Uri mProductImageUri;
    private String name;

    public Help(String mId, Uri mProductImageUri, String name) {
        this.mId = mId;
        this.mProductImageUri = mProductImageUri;
        this.name = name;
    }


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Uri getmProductImageUri() {
        return mProductImageUri;
    }

    public void setmProductImageUri(Uri mProductImageUri) {
        this.mProductImageUri = mProductImageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
