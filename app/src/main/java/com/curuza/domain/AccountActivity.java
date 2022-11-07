package com.curuza.domain;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.curuza.R;
import com.curuza.domain.common.BaseActivity;
import com.curuza.domain.onboarding.auth.AuthService;
import com.curuza.domain.onboarding.auth.WelcomeActivity;
import com.curuza.utils.LocaleUtils;
import com.franmontiel.localechanger.LocaleChanger;
import com.franmontiel.localechanger.utils.ActivityRecreationHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountActivity extends BaseActivity {
 private TextView Username;
 private TextView mLogout;
    private AlertDialog mChangeLanguageDialog;
    private LinearLayout mChangeLanguageContainer;
    private TextView mCurrentLanguageView;

 private AccountManagementFragment mAccountManagementFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        mAccountManagementFragment = new AccountManagementFragment();
        mChangeLanguageDialog = setupChangeLanguageDialog();

        Username = findViewById(R.id.pict_label);

        Username.setOnClickListener(v -> mAccountManagementFragment.show(getSupportFragmentManager(),null));

        mLogout = findViewById(R.id.logout_label);

        mLogout.setOnClickListener(v -> onSignOutClicked() );

        mChangeLanguageContainer = findViewById(R.id.language_container);
        mChangeLanguageContainer.setOnClickListener(v -> mChangeLanguageDialog.show());

        mCurrentLanguageView = findViewById(R.id.current_language);
        mCurrentLanguageView.setText(LocaleUtils.getCurrentLocaleDisplayName(this));

    }

    private void onSignOutClicked() {
        AuthService.clearUserData(this)
                .andThen(AuthService.signOut())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> WelcomeActivity.launch(this, true));
    }

    private AlertDialog setupChangeLanguageDialog() {
        AlertDialog.Builder changeLanguageDialogBuilder = new MaterialAlertDialogBuilder(this);

        View changeLanguageView = View.inflate(this, R.layout.change_language_dialog, null);
        changeLanguageDialogBuilder.setView(changeLanguageView);

        AlertDialog changeLanguageDialog = changeLanguageDialogBuilder.create();

        TextView frenchView = changeLanguageView.findViewById(R.id.french);
        frenchView.setOnClickListener(v -> {
            mCurrentLanguageView.setText(R.string.French);
            LocaleChanger.setLocale(LocaleUtils.FRENCH);
            ActivityRecreationHelper.recreate(this, false);
            changeLanguageDialog.dismiss();
        });

        TextView kirundiView = changeLanguageView.findViewById(R.id.kirundi);
        kirundiView.setOnClickListener(v -> {
            mCurrentLanguageView.setText(R.string.kirundi);
            LocaleChanger.setLocale(LocaleUtils.KIRUNDI);
            ActivityRecreationHelper.recreate(this, false);
            changeLanguageDialog.dismiss();
        });

      TextView swahiliView = changeLanguageView.findViewById(R.id.swahili);
      swahiliView.setOnClickListener(v -> {
        mCurrentLanguageView.setText(R.string.swahili);
        LocaleChanger.setLocale(LocaleUtils.SWAHILI);
        ActivityRecreationHelper.recreate(this, false);
        changeLanguageDialog.dismiss();
      });

        TextView englishView = changeLanguageView.findViewById(R.id.english);
        englishView.setOnClickListener(v -> {
            mCurrentLanguageView.setText(R.string.English);
            LocaleChanger.setLocale(LocaleUtils.ENGLISH);
            ActivityRecreationHelper.recreate(this, false);
            changeLanguageDialog.dismiss();
        });

        return changeLanguageDialog;
    }


}