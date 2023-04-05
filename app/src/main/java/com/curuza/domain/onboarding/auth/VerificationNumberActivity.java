package com.curuza.domain.onboarding.auth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.curuza.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationNumberActivity extends AppCompatActivity {
    private static final String DBG_TAG = VerificationNumberActivity.class.getSimpleName();
    private static final boolean MOCK_VERIFICATION = true;
    private static final int VERIFICATION_CODE_LENGTH = 6;
    private static final int SMS_REQUEST_TIMEOUT_IN_SECONDS = 60;

    private Button mContinueButton;
    private SignupSession mSignupSession;
    private FirebaseAuth mAuth;
    private String mPhoneNumber;
    private TextView mInstructionsView;
    private TextInputEditText mVerificationCodeEditText;
    private TextView mResendSmsTextView;
    private Group mUiGroup;
    private AlertDialog mSMSDispatchPendingDialog;
    private CountDownTimer mResendTimer;
    private boolean mPhoneNumberVerified;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendingToken;
    private boolean mVerificationCodeSent;


    public static Intent newIntent(Context context, SignupSession signupSession) {
        Intent intent = new Intent(context, VerificationNumberActivity.class);
        intent.putExtra(SignupSession.SIGNUP_SESSION_EXTRA_KEY, signupSession);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_number2);

        mSignupSession = getIntent().getParcelableExtra(SignupSession.SIGNUP_SESSION_EXTRA_KEY);
        mPhoneNumber = mSignupSession.getPhoneNumber();

        mResendSmsTextView = findViewById(R.id.resend_sms);
        setResendSmsEnabled(true);

        // Set up UI
        mInstructionsView = findViewById(R.id.instructions);
        mInstructionsView.setText(getInstructionsText(mPhoneNumber));

        // Hide UI and show progress dialog
        mUiGroup = findViewById(R.id.ui_group);

        mContinueButton =findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(v -> onVerificationCodeManuallyEntered());

        mSMSDispatchPendingDialog = new AlertDialog.Builder(this)
                .setView(R.layout.verification_code_pending_dialog)
                .create();

        mSMSDispatchPendingDialog.show();

        if (AuthService.isValidPhoneNumber(mPhoneNumber)) { // Initiate SMS
            Log.d(DBG_TAG,"starting sms verification for: "+ mPhoneNumber);
            verifyPhoneNumber(mPhoneNumber,null);
        };

        mVerificationCodeEditText = findViewById(R.id.verification_code_edit_text);
        mVerificationCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == VERIFICATION_CODE_LENGTH) {
                    onVerificationCodeManuallyEntered(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void verifyPhoneNumber(String phoneNumber, PhoneAuthProvider.ForceResendingToken resendingToken){
        mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mVerificationCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void onVerificationCodeManuallyEntered() {
        Log.d(DBG_TAG, "onVerificationCodeManuallyEntered");
        String enteredCode = mVerificationCodeEditText.getText().toString();

        if(enteredCode != null && !enteredCode.isEmpty()) {
            Log.d(DBG_TAG, "Entered code: " + enteredCode);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, enteredCode);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            Log.d(DBG_TAG, "Sign-in successful",null);
                            onVerificationSuccessful();
                        } else {
                            Log.d(DBG_TAG, "Sign-in failed: " + task.getException().toString());
                        }
                    });
        } else {
            Log.d(DBG_TAG, "Entered code is empty or null");
        }
    }

    private CharSequence getInstructionsText(String phoneNumber) {
        return Html.fromHtml(
                getString(
                        R.string.phone_number_verification_instructions,
                        phoneNumber != null ? AuthService.prettifyPhoneNumber(phoneNumber) : "N/A"
                ));
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mVerificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d(DBG_TAG, "Verification completed: " + phoneAuthCredential.toString());
            mVerificationCodeEditText.setText(phoneAuthCredential.getSmsCode());
            mVerificationCodeEditText.setSelection(mVerificationCodeEditText.getText().length());
            onVerificationSuccessful();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            mSMSDispatchPendingDialog.dismiss();
            if (e instanceof FirebaseNetworkException) {
                showErrorDialog(
                        VerificationNumberActivity.this,
                        R.string.network_unavailable_error,
                        dialog -> VerificationNumberActivity.this.finish());
            } else if (e instanceof FirebaseTooManyRequestsException) {
                showErrorDialog(
                        VerificationNumberActivity.this,
                        R.string.too_many_verification_attempts_error,
                        dialog -> VerificationNumberActivity.this.finish());
            }
            Log.d(DBG_TAG, e.toString(), e);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(DBG_TAG, "Code sent");

            mVerificationCodeSent = true;
            mVerificationId = s;
            mResendingToken = forceResendingToken;
            mSMSDispatchPendingDialog.dismiss();
            mUiGroup.setVisibility(View.VISIBLE);
            mVerificationCodeEditText.requestFocus();
            setResendSmsEnabled(false);

            mResendTimer = new CountDownTimer(SMS_REQUEST_TIMEOUT_IN_SECONDS * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mResendSmsTextView.setText(getResendSmsTextWithTimer(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    setResendSmsEnabled(true);
                }
            }.start();
        }
    };

    private void onVerificationSuccessful() {
        mPhoneNumberVerified = true;
        mResendTimer.cancel();
        mResendSmsTextView.setText(Html.fromHtml(getString(R.string.resend_sms_without_timer)));
        setContinueButtonEnabled(true);
    }

    private void setResendSmsEnabled(boolean enabled) {
        if (enabled) {
            mResendSmsTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            mResendSmsTextView.setText(Html.fromHtml(getString(R.string.resend_sms_without_timer)));
            mResendSmsTextView.setOnClickListener(v -> verifyPhoneNumber(mPhoneNumber, mResendingToken));
        } else {
            mResendSmsTextView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            mResendSmsTextView.setOnClickListener(null);
        }
    }

    private void setContinueButtonEnabled(boolean enable) {
        if (enable) {
            mContinueButton.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
            mContinueButton.setEnabled(true);
            mContinueButton.setOnClickListener(v -> {
                mSignupSession.setPhoneNumberVerified(true);                                                                            
                startActivity(ChoosePasswordActivity.newIntent(VerificationNumberActivity.this, mSignupSession));
            });
        } else {
            mContinueButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            mContinueButton.setEnabled(false);
        }
    }

    private CharSequence getResendSmsTextWithTimer(long millisUntilTimeout) {
        int secondsUntilTimeout = (int) (millisUntilTimeout / 1000);
        String timeUntilTimeout = (secondsUntilTimeout / 60) + ":" + String.format("%02d", secondsUntilTimeout % 60);
        return Html.fromHtml(getString(R.string.resend_sms_with_timer, timeUntilTimeout));
    }

    private void showErrorDialog(Context context, int stringResId, DialogInterface.OnDismissListener onDismissListener) {
        View dialogView = View.inflate(this, R.layout.error_dialog, null);
        TextView bodyText = dialogView.findViewById(R.id.body_text);
        bodyText.setText(stringResId);

        Button okButton = dialogView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> onDismissListener.onDismiss(null));

        new AlertDialog.Builder(context)
                .setView(dialogView)
                .setOnDismissListener(onDismissListener)
                .create()
                .show();
    }

    private void onVerificationCodeManuallyEntered(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
    }


}