package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;
import com.curuza.domain.common.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FournisseurDetailActivity extends BaseActivity {

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

        mFournisseurRepository = new FournisseurRepository(this);

        Log.d(FournisseurDetailActivity.class.getSimpleName(),"Fournisseur: " + fournisseur);


        mName = findViewById(R.id.nom_fournisseur);
        mName.getEditText().setText(fournisseur.getPersonName());
        mDescription = findViewById(R.id.description_fournisseur);
        mDescription.getEditText().setText(fournisseur.getDescription());
        mTelephone = findViewById(R.id.tel_number);
        mTelephone.getEditText().setText(fournisseur.getTelephone());

        mValidate = findViewById(R.id.fournisseur_validate);
        mValidate.setOnClickListener(v -> {
            Fournisseur updatedFournisseur = new Fournisseur(
                    fournisseur.getId(),mName.getEditText().getText().toString(),mDescription.getEditText().getText().toString(),mTelephone.getEditText().getText().toString(),fournisseur.getDate()

            );
            mFournisseurRepository.update(updatedFournisseur)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, e -> {
                });
            Intent intent2 = new Intent(FournisseurDetailActivity.this, FournisseurActivity.class);
            startActivity(intent2);

        });



    }
}
