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
import com.franmontiel.localechanger.LocaleChanger;
import com.jakewharton.threetenabp.AndroidThreeTen;

import static com.curuza.utils.LocaleUtils.APP_LOCALES;

public class CuruzaApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        Stetho.initializeWithDefaults(this);
        LocaleChanger.initialize(this, APP_LOCALES);


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
