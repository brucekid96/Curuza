package com.curuza.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.curuza.R;
import com.curuza.databinding.ActivityProfileBinding;
import com.curuza.domain.onboarding.auth.AuthService;
import com.curuza.domain.onboarding.auth.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountActivity extends AppCompatActivity {
 private TextView Username;
 private TextView mLogout;

 private AccountManagementFragment mAccountManagementFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        mAccountManagementFragment = new AccountManagementFragment();

        Username = findViewById(R.id.pict_label);

        Username.setOnClickListener(v -> mAccountManagementFragment.show(getSupportFragmentManager(),null));

        mLogout = findViewById(R.id.logout_label);

        mLogout.setOnClickListener(v -> onSignOutClicked() );

    }

    private void onSignOutClicked() {
        AuthService.clearUserData(this)
                .andThen(AuthService.signOut())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> WelcomeActivity.launch(this, true));
    }


}