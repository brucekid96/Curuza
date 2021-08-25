package com.curuza.domain;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.curuza.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class ProfilePhotoView extends ConstraintLayout {

    private static final String DBG_TAG = ProfilePhotoView.class.getSimpleName();
    private FloatingActionButton mPickButtonView;
    private FloatingActionButton mRemoveButtonView;
    private ImageView mProfilePhotoHolderView;

    public ProfilePhotoView(Context context) {
        super(context);
        init();
    }

    public ProfilePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.profile_photo_view, this);
        mPickButtonView = findViewById(R.id.pick_photo_button);
        // Change the ripple color from the default accent color in styles.xml. This cannot be done in XML at the moment.
        mPickButtonView.setRippleColor(getResources().getColor(R.color.lighter_gray));

        mRemoveButtonView = findViewById(R.id.remove_photo_button);

        mProfilePhotoHolderView = findViewById(R.id.profile_photo_holder);
    }

    public void setOnPickPhotoListener(OnClickListener listener) {
        mProfilePhotoHolderView.setOnClickListener(listener);
        mPickButtonView.setOnClickListener(listener);
    }

    public void setOnRemovePhotoListener(OnClickListener listener) {
        mRemoveButtonView.setOnClickListener(listener);
    }

    public void displayProfilePhoto(Uri photoUri) {
        boolean isPhotoPresent = new File(photoUri.getPath()).exists();
        mRemoveButtonView.setVisibility(isPhotoPresent ? VISIBLE : INVISIBLE);
        mPickButtonView.setVisibility(isPhotoPresent ? INVISIBLE : VISIBLE);

        Glide.with(this)
                .load(photoUri)
                .placeholder(R.drawable.ic_person_40dp)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop()
                .into(mProfilePhotoHolderView);
    }
}
