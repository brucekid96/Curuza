package com.curuza.domain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        boolean lang_selected = true;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.container)!=null)
        {
            if(savedInstanceState!=null)
                return;

            getFragmentManager().beginTransaction().add(R.id.container,new Preference()).commit();
        }
    }
}
