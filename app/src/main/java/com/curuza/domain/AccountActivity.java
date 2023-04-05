package com.curuza.domain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.curuza.R;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.photos.PhotoType;
import com.curuza.domain.common.AboutAppFragment;
import com.curuza.domain.common.BaseActivity;
import com.curuza.domain.common.BottomSheetFragment;
import com.curuza.domain.common.EditPhotoFragment;
import com.curuza.domain.common.EditPhotoListener;
import com.curuza.domain.onboarding.auth.AuthService;
import com.curuza.domain.onboarding.auth.WelcomeActivity;
import com.curuza.utils.DialogUtils;
import com.curuza.utils.LocaleUtils;
import com.franmontiel.localechanger.LocaleChanger;
import com.franmontiel.localechanger.utils.ActivityRecreationHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountActivity extends BaseActivity implements EditPhotoListener {
  private static final String DBG_TAG = AccountActivity.class.getSimpleName();
  private static final String EDIT_PROFILE_PHOTO_FRAGMENT_TAG = "edit_profile_photo_fragment";

  private PhotoRepository mPhotoRepository;
 private TextView Username;
 private TextView mLogout;
  private ImageView mProfilePhotoView;
    private AlertDialog mChangeLanguageDialog;
    private LinearLayout mChangeLanguageContainer;
  private LinearLayout mAboutContainer;
    private TextView mCurrentLanguageView;

  private EditPhotoFragment mEditProfilePhotoFragment;
  private AlertDialog mUploadPhotoProgressDialog;
  private AlertDialog mRemovePhotoProgressDialog;
  private BottomSheetFragment mAboutAppFragment;

  private String mCurrentUserId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        mChangeLanguageDialog = setupChangeLanguageDialog();

        mPhotoRepository = new PhotoRepository(this);


      mAboutAppFragment = new AboutAppFragment();





      mUploadPhotoProgressDialog = DialogUtils.getProgressDialog(this, R.string.uploading_profile_photo);
      mRemovePhotoProgressDialog = DialogUtils.getProgressDialog(this, R.string.removing_profile_photo);



      mEditProfilePhotoFragment = new EditPhotoFragment(this);

      mAboutContainer = findViewById(R.id.about_us_container);
      mAboutContainer.setOnClickListener(v -> mAboutAppFragment.show(getSupportFragmentManager(), null));;


      mProfilePhotoView = findViewById(R.id.pict_icon);
      mProfilePhotoView.setOnClickListener(v -> showEditProfilePhotoFragment());

      File thumbnailFile =
          new File(mPhotoRepository.getUserProfilePhotoUri(mCurrentUserId).getPath());

      if (thumbnailFile.exists()) {
        mProfilePhotoView.setImageURI(mPhotoRepository.getUserProfilePhotoUri(mCurrentUserId));
      } else {
        mProfilePhotoView.setImageResource(R.drawable.ic_profile_user);
      }

        Username = findViewById(R.id.pict_label);



        mLogout = findViewById(R.id.logout_label);

        mLogout.setOnClickListener(v -> onSignOutClicked() );

        mChangeLanguageContainer = findViewById(R.id.language_container);
        mChangeLanguageContainer.setOnClickListener(v -> mChangeLanguageDialog.show());

        mCurrentLanguageView = findViewById(R.id.current_language);
        mCurrentLanguageView.setText(LocaleUtils.getCurrentLocaleDisplayName(this));

    }



  @Override
  protected void onStart() {
    super.onStart();
    mCurrentUserId = Amplify.Auth.getCurrentUser().getUserId();

    Username.setText(Amplify.Auth.getCurrentUser().getUsername());

    displayProfilePhoto(mPhotoRepository.getUserProfilePhotoUri(mCurrentUserId));
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
    mEditProfilePhotoFragment.dismiss();
    mRemovePhotoProgressDialog.show();
    mPhotoRepository.removeOwnProfilePhoto()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onPhotoRemoved);
  }

  private void onPhotoRemoved() {
    mRemovePhotoProgressDialog.dismiss();
    mProfilePhotoView.setImageResource(R.drawable.ic_person_55dp);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK) {
      onPhotoPicked(data.getData());
    } else if (resultCode == ImagePicker.RESULT_ERROR) {
      DialogUtils.showErrorDialog(this);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void onPhotoPicked(Uri photoUri) {
    mUploadPhotoProgressDialog.show();
    mPhotoRepository.uploadUserProfilePhoto(photoUri)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onPhotoUploadSuccess, this::onPhotoUploadFailure);
  }

  private void onPhotoUploadSuccess(Uri photoUri) {
    displayProfilePhoto(photoUri);
    mUploadPhotoProgressDialog.dismiss();
  }

  private void onPhotoUploadFailure(Throwable e) {
    mUploadPhotoProgressDialog.dismiss();
    DialogUtils.showErrorDialog(this);
    Log.d(DBG_TAG, "Profile photo upload failed", e);
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


}