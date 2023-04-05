package com.curuza.domain.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.curuza.BuildConfig;
import com.curuza.R;

public class AboutAppFragment extends BottomSheetFragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.about_app_fragment, container, false);

    TextView mVersionNumberView = root.findViewById(R.id.version_number);
    mVersionNumberView.setText(BuildConfig.VERSION_NAME);

    return root;
  }
}
