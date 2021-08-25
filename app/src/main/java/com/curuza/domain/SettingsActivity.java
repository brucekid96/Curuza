package com.curuza.domain;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.curuza.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private CardView language_card;
    private TextView language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadLocale();
        setContentView(R.layout.settings);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        language_card = findViewById(R.id.language_card);
//        language = findViewById(R.id.language);
//
//        language_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showChangeLangageDialog();
//            }
//        });

    }

//    private void showChangeLangageDialog() {
//        final String[] listItems = {"French","Kirundi","English"};
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
//        mBuilder.setTitle("Choose Language");
//        mBuilder.setSingleChoiceItems(listItems, -1, (dialog, i) -> {
//            if(i==0) {
//               setLocale("fr");
//               recreate();
//
//            }
//
//            if(i==1) {
//                setLocale("rn");
//                recreate();
//                language.setText("Kirundi");
//            }
//
//            if(i==2) {
//                setLocale("en");
//                recreate();
//                language.setText("English");
//            }
//
//            dialog.dismiss();
//        });
//
//
//        AlertDialog mDialog = mBuilder.create();
//        mDialog.show();
//    }
//
//    private void setLocale(String lang) {
//
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics() );
//        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
//        editor.putString("my lang",lang);
//        editor.apply();
//    }
//    public void loadLocale(){
//        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
//        String language = prefs.getString("my lang","");
//        setLocale(language);
//    }
}
