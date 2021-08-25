package com.curuza.domain.onboarding.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.curuza.R;
import com.curuza.domain.ErrorClearingTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChoosePasswordActivity extends AppCompatActivity {

    private Button mContinueButton;

    private TextInputLayout mEnterPasswordInputLayout;
    private TextInputEditText mEnterPasswordEditText;
    private TextInputLayout mConfirmPasswordInputLayout;
    private TextInputEditText mConfirmPasswordEditText;
    private TextInputLayout mEnterIdDocumentNumberInputLayout;
    private TextInputEditText mEnterIdDocumentEditText;

    private SignupSession mSignupSession;


    public static Intent newIntent(Context context, SignupSession signupSession) {
        Intent intent = new Intent(context, ChoosePasswordActivity.class);
        intent.putExtra(SignupSession.SIGNUP_SESSION_EXTRA_KEY, signupSession);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_password_activity);

        mSignupSession = getIntent().getParcelableExtra(SignupSession.SIGNUP_SESSION_EXTRA_KEY);

        mEnterPasswordInputLayout = findViewById(R.id.enter_password_input);
        mEnterPasswordEditText = findViewById(R.id.enter_password_edit_text);
        mEnterPasswordEditText.addTextChangedListener(new ErrorClearingTextWatcher(mEnterPasswordInputLayout));
        mEnterPasswordEditText.requestFocus();

        mConfirmPasswordInputLayout = findViewById(R.id.confirm_password_input);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        mConfirmPasswordEditText.addTextChangedListener(new ErrorClearingTextWatcher(mConfirmPasswordInputLayout));

        mContinueButton =findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(
                v -> {
                    if (validateInputs() && mSignupSession != null) {
                        mSignupSession
                                .setPassword(mEnterPasswordEditText.getText().toString());

                        startActivity(CompleteRegistrationActivity.newIntent(this, mSignupSession));
                    }
                });




    }

    private boolean validateInputs() {
        if (mEnterPasswordEditText.length() == 0) {
            mEnterPasswordInputLayout.setError(getString(R.string.no_password_entered));
            return false;
        } else if (!AuthService.isValidPassword(mEnterPasswordEditText.getText().toString())) {
            mEnterPasswordInputLayout.setError(getString(R.string.invalid_password_entered));
            return false;
        } else if (mConfirmPasswordEditText.length() == 0) {
            mConfirmPasswordInputLayout.setError(getString(R.string.no_confirmation_password_entered));
            return false;
        } else if (!mConfirmPasswordEditText.getText().toString()
                .equals(mEnterPasswordEditText.getText().toString())) {
            mConfirmPasswordInputLayout.setError(getString(R.string.confirmation_password_mismatch));
            return false;
        }

        return true;
    }
}
