package com.curuza.domain;

import android.app.Application;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.AmplifyConfiguration;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.curuza.R;
import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class CuruzaApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        Stetho.initializeWithDefaults(this);

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(
                    AmplifyConfiguration.builder(this, R.raw.amplifyconfiguration)
                            .devMenuEnabled(false)
                            .build(),
                    this);
        } catch (AmplifyException e) {
            Log.e(CuruzaApplication.class.getSimpleName(), "Could not initialize Amplify", e);
        }
    }
}
