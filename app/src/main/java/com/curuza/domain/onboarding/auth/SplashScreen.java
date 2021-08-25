package com.curuza.domain.onboarding.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.curuza.R;
import com.curuza.domain.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashScreen extends AppCompatActivity {
    private static final String DBG_TAG = SplashScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        long startTime = System.currentTimeMillis();
        AuthService.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        authUser -> { // A currently logged in user was found
                            long elapsedTime = System.currentTimeMillis() - startTime;
                            MainActivity.launch(this, false);
                            Log.d(DBG_TAG, "currentUser = " + authUser.toString());
                            Log.d(DBG_TAG, "AuthService.getCurrentUser() took " + elapsedTime + "ms");
                        },
                        error -> Log.d(DBG_TAG, "AuthService.getCurrentUser() failed: " + error.toString()),
                        () -> { // No logged in user was found
                            WelcomeActivity.launch(this, false);
                            Log.d(DBG_TAG, "currentUser is null ");
                        });

    }
}
