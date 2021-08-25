package com.curuza.domain.onboarding.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.curuza.R;
import com.curuza.domain.ErrorClearingTextWatcher;
import com.curuza.domain.LoadingButton;
import com.curuza.domain.MainActivity;
import com.curuza.utils.DialogUtils;
import com.curuza.utils.ViewUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends AppCompatActivity {
    private static final String DBG_TAG = WelcomeActivity.class.getSimpleName();
    private LoadingButton mContinueButton;
    private TextInputLayout mPhoneNumberInputLayout;
    private EditText mPhoneNumberEditText;
    private TextInputLayout mPasswordInputLayout;
    private EditText mPasswordEditText;


    public static void launch(Context context, boolean clearHistory) {
        Intent intent = new Intent(context, WelcomeActivity.class);

        if (clearHistory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        context.startActivity(intent);
    }

    public static void launch(Context context) {
        launch(context, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        mPhoneNumberInputLayout = findViewById(R.id.phone_number_input_layout);
        mPhoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        mPhoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhoneNumberInputLayout.setErrorEnabled(false);
                onPhoneNumberBeingEntered();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordInputLayout = findViewById(R.id.password_input_layout);
        mPasswordEditText = mPasswordInputLayout.getEditText();
        mPasswordEditText.addTextChangedListener(new ErrorClearingTextWatcher(mPasswordInputLayout));

        mContinueButton = findViewById(R.id.continue_button);
        mContinueButton.setText("Ok");



    }

    private void onPhoneNumberBeingEntered() {
        mContinueButton.setText(R.string.continue_);
        mContinueButton.setOnClickListener(this::onPhoneNumberEntered);
    }

    private void onPhoneNumberEntered(View v) {
        String enteredPhoneNumber = getEnteredPhoneNumber();
        if (!AuthService.isValidPhoneNumber(enteredPhoneNumber)) {
            mPhoneNumberInputLayout.setError("Please enter a valid phone number");
        } else {
            mContinueButton.setLoadingStatus(true);
            AuthService.isUserRegistered(enteredPhoneNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onUserRegisteredResult, this::onUserRegisteredError);
        }
    }

    private void onUserRegisteredResult(Boolean isUserRegistered) {
        Log.d(DBG_TAG, "onUserRegisteredResult: " + isUserRegistered);
        if (!isUserRegistered) {
            ViewUtils.toggleSoftInput(mContinueButton, false);
            showPhoneNumberDoubleCheckDialog(getEnteredPhoneNumber());
            mContinueButton.setText(R.string.continue_);
            mContinueButton.setOnClickListener(WelcomeActivity.this::onPhoneNumberEntered);
        }
        else {
            mPasswordInputLayout.setVisibility(View.VISIBLE);
            mPasswordInputLayout.requestFocus();
            ViewUtils.toggleSoftInput(mContinueButton, true);
            mContinueButton.setText(R.string.continue_);
            mContinueButton.setOnClickListener(this::onSignInDetailsEntered);
        }
    }

    private void onUserRegisteredError(Throwable error) {
        Log.d(DBG_TAG, "onUserRegisteredError: " + error.toString());
        DialogUtils.showErrorDialog(this, R.string.request_unsuccessful);
        mContinueButton.setText(R.string.continue_);
        mContinueButton.setOnClickListener(this::onPhoneNumberEntered);
    }

    private void showPhoneNumberDoubleCheckDialog(String phoneNumber) {
        AlertDialog alertDialog = new MaterialAlertDialogBuilder(this).create();

        View dialogView = View.inflate(this, R.layout.verify_phone_number_dialog, null);
        TextView phoneNumberView = dialogView.findViewById(R.id.phone_number);
        phoneNumberView.setText(AuthService.prettifyPhoneNumber(phoneNumber));

        Button editButton = dialogView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.setOnDismissListener(dialog -> {
        });

        Button okButton = dialogView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            onPhoneNumberDoubleChecked(phoneNumber);
        });

        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    private String getEnteredPhoneNumber() {
        return "+257" + mPhoneNumberEditText.getText();
    }

    private void onPhoneNumberDoubleChecked(String phoneNumber) {
        SignupSession signupSession = new SignupSession();
        signupSession.setPhoneNumber(phoneNumber);
        startActivity(VerificationNumberActivity.newIntent(this, signupSession));
    }

    private boolean validatePasswordInput(){
        if (mPasswordInputLayout.getEditText().length() == 0) {
            mPasswordInputLayout.setError(getString(R.string.enter_a_password));
            return false;
        } else if (!AuthService.isValidPassword(mPasswordInputLayout.getEditText().getText().toString())) {
            mPasswordInputLayout.setError(getString(R.string.enter_valid_password));
            return false;
        }

        return true;
    }

    private void onSignInDetailsEntered(View v) {
        String enteredPhoneNumber = getEnteredPhoneNumber();
        String enteredPassword = mPasswordEditText.getText().toString();

        if (validatePasswordInput()) {
            mContinueButton.setLoadingStatus(true);
            AuthService.signIn(enteredPhoneNumber, enteredPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSignInResult, this::onSignInError);
        }
    }

    private void onSignInResult(AuthSignInResult signInResult) {
        Log.d(DBG_TAG, "Cognito signIn response: " + signInResult.toString());
        if (signInResult.isSignInComplete()) {
            MainActivity.launch(this);
        }
    }

    private void onSignInError(Throwable signInError) {
        if (signInError.getCause() instanceof NotAuthorizedException) {
            DialogUtils.showErrorDialog(this, R.string.incorrect_credentials);
        } else {
            DialogUtils.showErrorDialog(this, R.string.request_unsuccessful);
        }

        Log.d(DBG_TAG, "Cognito signIn error: " + signInError.toString());
        mContinueButton.setText(R.string.continue_);
        mContinueButton.setOnClickListener(this::onSignInDetailsEntered);
    }
}