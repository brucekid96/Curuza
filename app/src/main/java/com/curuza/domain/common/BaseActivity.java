package com.curuza.domain.common;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.LocaleChangerAppCompatDelegate;

import com.franmontiel.localechanger.utils.ActivityRecreationHelper;

public class BaseActivity extends AppCompatActivity {
  private LocaleChangerAppCompatDelegate mLocaleChangerAppCompatDelegate;

  @Override
  protected void onStart() {
    super.onStart();
  }

  @NonNull
  @Override
  public AppCompatDelegate getDelegate() {
    if (mLocaleChangerAppCompatDelegate == null) {
      mLocaleChangerAppCompatDelegate = new LocaleChangerAppCompatDelegate(super.getDelegate());
    }

    return mLocaleChangerAppCompatDelegate;
  }

  @Override
  protected void onResume() {
    super.onResume();
    ActivityRecreationHelper.onResume(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityRecreationHelper.onDestroy(this);
  }
}

