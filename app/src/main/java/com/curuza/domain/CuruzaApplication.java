package com.curuza.domain;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class CuruzaApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
