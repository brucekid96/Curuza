package com.curuza.domain.onboarding.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.result.AuthSignUpResult;
import com.curuza.R;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.domain.ErrorClearingTextWatcher;
import com.curuza.domain.MainActivity;
import com.curuza.domain.common.ProgressDialog;
import com.curuza.utils.DialogUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CompleteRegistrationActivity  extends AppCompatActivity {

    private static final String DBG_TAG = CompleteRegistrationActivity.class.getSimpleName();

    private TextInputLayout mFirstNameInputLayout;
    private TextInputEditText mFirstNameEditText;
    private TextInputLayout mLastNameInputLayout;
    private TextInputEditText mLastNameEditText;
    private Button mContinueButton;
    private com.curuza.domain.common.ProgressDialog mProgressDialog;
    private PhotoRepository mUserRepository;

    private SignupSession mSignupSession;

    public static Intent newIntent(Context context, SignupSession signupSession) {
        Intent intent = new Intent(context, CompleteRegistrationActivity.class);
        intent.putExtra(SignupSession.SIGNUP_SESSION_EXTRA_KEY, signupSession);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_registration_activity);
        mSignupSession = getIntent().getParcelableExtra(SignupSession.SIGNUP_SESSION_EXTRA_KEY);


        mFirstNameInputLayout = findViewById(R.id.first_name_input);
        mFirstNameEditText = findViewById(R.id.first_name_edit_text);
        mFirstNameEditText.addTextChangedListener(new ErrorClearingTextWatcher(mFirstNameInputLayout));
        mFirstNameEditText.requestFocus();

        mLastNameInputLayout = findViewById(R.id.last_name_input);
        mLastNameEditText = findViewById(R.id.last_name_edit_text);
        mLastNameEditText.addTextChangedListener(new ErrorClearingTextWatcher(mLastNameInputLayout));

        mContinueButton = findViewById(R.id.continue_button);
        mContinueButton.setText(R.string.continue_);
        mContinueButton.setOnClickListener(v -> {
            if (validateInputs()) {
                mSignupSession
                        .setFirstName(mFirstNameEditText.getText().toString())
                        .setLastName(mLastNameEditText.getText().toString());

                completeSignup();


            }
        });


    }

    private boolean validateInputs() {
        if (mFirstNameEditText.length() == 0) {
            mFirstNameInputLayout.setError("Please enter your first name");
            return false;
        } else if (!AuthService.isValidName(mFirstNameEditText.getText().toString())) {
            mFirstNameInputLayout.setError("Please enter a valid name");
            return false;
        } else if (mLastNameEditText.length() == 0) {
            mLastNameInputLayout.setError("Please enter your last name");
            return false;
        } else if (!AuthService.isValidName(mLastNameEditText.getText().toString())) {
            mLastNameInputLayout.setError("Please enter a valid name");
            return false;
        }

        return true;
    }

    private void completeSignup() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setText(R.string.creating_new_account);
        mProgressDialog.show();

        Log.d(DBG_TAG, "mSignupSession = " + mSignupSession);
        AuthService.signUp(mSignupSession)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSignupSuccess, this::onSignupFailure);
    }

    private void onSignupSuccess(AuthSignUpResult signUpResult) {
        Log.d(DBG_TAG, "onSignupSuccess: " + signUpResult.toString());
        AuthService.signIn(mSignupSession.getPhoneNumber(), mSignupSession.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        signInResult -> {
                            if (signInResult.isSignInComplete()) {
                                onSignInSuccess();
                            } else {
                                Log.d(DBG_TAG, "signInComplete: false");
                                mContinueButton.setText(R.string.continue_);
                                mContinueButton.setOnClickListener(v -> completeSignup());
                                DialogUtils.showErrorDialog(this, R.string.request_unsuccessful);
                            }
                        },
                        this::onSignInFailure);
    }

    private void onSignupFailure(Throwable error) {
        Log.d(DBG_TAG, "Sign-up failed\n" + error.toString());
        mProgressDialog.dismiss();
        DialogUtils.showErrorDialog(this, R.string.request_unsuccessful);
    }

    private void onSignInSuccess() {
        Log.d(DBG_TAG, "Sign-in successful");
        goToMainActivity();
    }

    private void onSignInFailure(Throwable error) {
        Log.d(DBG_TAG, "Sign-in failed" + error.toString());
        mProgressDialog.dismiss();
        DialogUtils.showErrorDialog(this);
    }

    private void goToMainActivity() {
        mProgressDialog.dismiss();
        MainActivity.launch(this);
    }
}
