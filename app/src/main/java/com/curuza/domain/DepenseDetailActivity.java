package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;

import com.curuza.data.depense.Depense;
import com.curuza.data.depense.DepenseRepository;
import com.google.android.material.textfield.TextInputLayout;

public class DepenseDetailActivity  extends AppCompatActivity {

    private Depense depense;
    private Button mValidate;
    private TextInputLayout mDescription;
    private TextInputLayout mAmount;
    private DepenseRepository mDepenseRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_detail);

        Intent intent = getIntent();
        depense = intent.getParcelableExtra(Depense.DEPENSE_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d(CreditDetailActivity.class.getSimpleName(),"Credit: " + depense);


        mDescription = findViewById(R.id.description_depense);
        mDescription.getEditText().setText(depense.getDescription());
        mAmount = findViewById(R.id.amount_depense);
        mAmount.getEditText().setText(Integer.toString(depense.getAmount()));

        mValidate = findViewById(R.id.validate);
        mValidate.setOnClickListener(v -> {

            Intent intent2 = new Intent(DepenseDetailActivity.this, DepenseActivity.class);
            startActivity(intent2);

        });



    }
}
