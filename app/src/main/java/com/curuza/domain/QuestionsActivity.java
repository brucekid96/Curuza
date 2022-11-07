package com.curuza.domain;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.domain.common.BaseActivity;

public class QuestionsActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
