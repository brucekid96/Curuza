package com.curuza.domain.onboarding.auth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.amplifyframework.auth.result.AuthSignUpResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.curuza.R;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.photos.PhotoType;
import com.curuza.domain.ErrorClearingTextWatcher;
import com.curuza.domain.MainActivity;
import com.curuza.domain.common.BaseActivity;
import com.curuza.domain.common.EditPhotoFragment;
import com.curuza.domain.common.EditPhotoListener;
import com.curuza.domain.common.ProgressDialog;
import com.curuza.utils.DialogUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CompleteRegistrationActivity  extends BaseActivity implements EditPhotoListener {

    private static final String DBG_TAG = CompleteRegistrationActivity.class.getSimpleName();
    private static final String EDIT_PROFILE_PHOTO_FRAGMENT_TAG = "edit_profile_photo_fragment";

    private ImageView mProfilePhotoView;
    private EditPhotoFragment mEditProfilePhotoFragment;
    private Uri mProfilePhotoUri;
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


        mEditProfilePhotoFragment = new EditPhotoFragment(this, false);

        mProfilePhotoView = findViewById(R.id.profile_photo_view);
        mProfilePhotoView.setOnClickListener(v -> showEditProfilePhotoFragment());


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

    private void showEditProfilePhotoFragment() {
        mEditProfilePhotoFragment.show(getSupportFragmentManager(), EDIT_PROFILE_PHOTO_FRAGMENT_TAG);
    }

    @Override
    public void takePhoto() {
        mEditProfilePhotoFragment.dismiss();
        ImagePicker.with(this)
            .cropSquare()
            .cameraOnly()
            .maxResultSize(
                PhotoType.USER_PROFILE_PHOTO.getWidth(),
                PhotoType.USER_PROFILE_PHOTO.getHeight())
            .saveDir(getCacheDir())
            .start();
    }

    @Override
    public void pickPhotoFromGallery() {
        mEditProfilePhotoFragment.dismiss();
        ImagePicker.with(this)
            .cropSquare()
            .galleryOnly()
            .maxResultSize(
                PhotoType.USER_PROFILE_PHOTO.getWidth(),
                PhotoType.USER_PROFILE_PHOTO.getHeight())
            .saveDir(getCacheDir())
            .start();
    }

    @Override
    public void removePhoto() {
        mProfilePhotoUri = null;
        mProfilePhotoView.setImageResource(R.drawable.ic_person_55dp);
        mEditProfilePhotoFragment.toggleRemovePhotoOption(false);
        mEditProfilePhotoFragment.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK) {
            mProfilePhotoUri = data.getData();
            mSignupSession.setProfilePhotoUri(mProfilePhotoUri);
            // Allow the user to remove the selected profile photo
            mEditProfilePhotoFragment.toggleRemovePhotoOption(true);
            displayProfilePhoto(mProfilePhotoUri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayProfilePhoto(Uri profilePhotoUri) {
        File localProfilePhotoFile = new File(profilePhotoUri.getPath());
        if (localProfilePhotoFile.exists()) {
            Glide.with(this)
                .load(profilePhotoUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .circleCrop()
                .into(mProfilePhotoView);
        }
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

    private void uploadProfilePhoto() {
        if (mSignupSession.getProfilePhotoUri() != null) {
            Log.d(DBG_TAG, "Uploading profile photo...");
            mProgressDialog.setText(R.string.uploading_profile_photo);
            mUserRepository = new PhotoRepository(this);
            mUserRepository.uploadUserProfilePhoto(mSignupSession.getProfilePhotoUri())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    uri -> {
                        Log.d(DBG_TAG, "Profile photo upload successful");

                    },
                    error -> {
                        Log.d(DBG_TAG, "Profile photo upload failed: " + error.toString());
                        mProgressDialog.dismiss();
                        DialogUtils.showErrorDialog(this);
                    });
        } else {

        }
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
                            } if (mProfilePhotoUri != null) {
                                uploadProfilePhoto();
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
