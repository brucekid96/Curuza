package com.curuza.domain.common;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.curuza.R;

public class EditPhotoFragment extends BottomSheetFragment {
  private static final String DBG_TAG = EditPhotoFragment.class.getSimpleName();
  private LinearLayout mTakePhotoContainer;
  private LinearLayout mOpenGalleryContainer;
  private LinearLayout mRemovePhotoContainer;

  private EditPhotoListener mListener;
  private boolean mEnableRemovePhoto;
  private boolean mOnViewInflationComplete;

  public EditPhotoFragment(EditPhotoListener listener) {
    this(listener, true);
  }

  public EditPhotoFragment(EditPhotoListener listener, boolean enableRemovePhoto) {
    mListener = listener;
    mEnableRemovePhoto = enableRemovePhoto;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.edit_photo_fragment, container, false);

    mTakePhotoContainer = root.findViewById(R.id.take_photo_container);
    mTakePhotoContainer.setOnClickListener(v -> mListener.takePhoto());

    mOpenGalleryContainer = root.findViewById(R.id.open_gallery_container);
    mOpenGalleryContainer.setOnClickListener(v -> mListener.pickPhotoFromGallery());

    mRemovePhotoContainer = root.findViewById(R.id.remove_photo_container);

    mOnViewInflationComplete = true;
    toggleRemovePhotoOption(mEnableRemovePhoto);

    return root;
  }

  public void toggleRemovePhotoOption(boolean enableRemovePhoto) {
    Log.d(DBG_TAG, "toggleRemovePhotoOption: enableRemovePhoto = " + enableRemovePhoto + ", mOnViewInflationComplete = " + mOnViewInflationComplete);
    mEnableRemovePhoto = enableRemovePhoto;
    // Only do this if this fragment's view has been inflated
    if (mOnViewInflationComplete) {
      if (enableRemovePhoto) {
        mRemovePhotoContainer.setAlpha(1f);
        mRemovePhotoContainer.setOnClickListener(v -> mListener.removePhoto());
      } else {
        mRemovePhotoContainer.setAlpha(0.3f);
        mRemovePhotoContainer.setOnClickListener(null);
      }
    }

  }
}
