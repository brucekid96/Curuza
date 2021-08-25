package com.curuza.domain;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.curuza.R;

public class LoadingButton extends ConstraintLayout {
    private Button mButton;
    private ProgressBar mProgressBar;

    private String mButtonText;
    private OnClickListener mButtonListener;

    // The progress bar's translationZ needs to be set higher than the button's default translationZ
    // so that it can appear in front of the button
    private static final float PROGRESS_BAR_TRANSLATION_Z = 20;

    public LoadingButton(Context context) {
        super(context);
        init(context);
    }

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.loading_button, this);

        mButton = findViewById(R.id.button);
        mProgressBar = findViewById(R.id.progress_bar);
        ViewCompat.setTranslationZ(mProgressBar, PROGRESS_BAR_TRANSLATION_Z);
    }

    public void setLoadingStatus(boolean loadingStatus) {
        if (loadingStatus) {
            mButton.setText(null);
            mButton.setOnClickListener(null);
            mButton.setClickable(false);
            mProgressBar.setVisibility(VISIBLE);
        } else {
            mButton.setText(mButtonText);
            mButton.setOnClickListener(mButtonListener);
            mProgressBar.setVisibility(GONE);
        }
    }

    public void setText(int textResId) {
        setText(getResources().getString(textResId));
    }

    public void setText(String text) {
        mButtonText = text;
        mButton.setText(text);
        mProgressBar.setVisibility(GONE);
    }

    public void setOnClickListener(OnClickListener listener) {
        mButtonListener = listener;
        mButton.setOnClickListener(listener);
    }
}
