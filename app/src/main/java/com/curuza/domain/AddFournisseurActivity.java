package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;

import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

public class AddFournisseurActivity extends AppCompatActivity {
    private Fournisseur mFournisseur;
    private TextInputLayout mName;
    private TextInputLayout mPhoneNumber;
    private TextInputLayout mDescription;
    private TextInputLayout mAmount;
    private  String mDate;
    private Button mValidate;
    FournisseurRepository mFournisseurRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fournisseur);

        Intent intent = getIntent();
        mFournisseur = intent.getParcelableExtra(Fournisseur.FOURNISSEUR_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFournisseurRepository = new FournisseurRepository(getApplicationContext());


        mName= findViewById(R.id.nom_fournisseur);
        mPhoneNumber = findViewById(R.id.tel_number);
        mDescription = findViewById(R.id.description_fournisseur);
        mValidate = findViewById(R.id.fournisseur_validate);

        mValidate.setOnClickListener(view -> {

            mDate = ZonedDateTime.now().toInstant().toString();
            Fournisseur fournisseur = new Fournisseur(
                    UUID.randomUUID().toString(),
                    mName.getEditText().getText().toString(),
                    mDescription.getEditText().getText().toString(),
                    mDate,
                    mPhoneNumber.getEditText().getText().toString()
            );
            mFournisseurRepository.insert(fournisseur);
            Log.d(AddFournisseurActivity.class.getSimpleName(), "Added fournisseur: " + fournisseur.toString());

            Intent intent1 = new Intent(AddFournisseurActivity.this, FournisseurActivity.class);
            intent1.putExtra(Fournisseur.FOURNISSEUR_EXTRA, fournisseur);
            startActivity(intent1);


        });







    }

    public void setData(Fournisseur fournisseur) {
        mFournisseur = fournisseur;
    }
}
