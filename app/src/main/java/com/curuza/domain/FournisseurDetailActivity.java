package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.credit.Credit;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;
import com.google.android.material.textfield.TextInputLayout;

public class FournisseurDetailActivity extends AppCompatActivity {

    private Fournisseur fournisseur;
    private Button mValidate;
    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mTelephone;
    private TextInputLayout mAmount;
    private FournisseurRepository mFournisseurRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fournisseur_detail);

        Intent intent = getIntent();
        fournisseur = intent.getParcelableExtra(Fournisseur.FOURNISSEUR_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d(FournisseurDetailActivity.class.getSimpleName(),"Fournisseur: " + fournisseur);


        mName = findViewById(R.id.nom_fournisseur);
        mName.getEditText().setText(fournisseur.getPersonName());
        mDescription = findViewById(R.id.description_fournisseur);
        mDescription.getEditText().setText(fournisseur.getDescription());
        mTelephone = findViewById(R.id.tel_number);
        mTelephone.getEditText().setText(fournisseur.getTelephone());

        mValidate = findViewById(R.id.fournisseur_validate);
        mValidate.setOnClickListener(v -> {

            Intent intent2 = new Intent(FournisseurDetailActivity.this, FournisseurActivity.class);
            startActivity(intent2);

        });



    }
}
