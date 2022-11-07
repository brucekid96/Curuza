package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.depense.Depense;
import com.curuza.data.depense.DepenseRepository;
import com.curuza.domain.common.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DepenseDetailActivity  extends BaseActivity {

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

        mDepenseRepository = new DepenseRepository(this);


        Log.d(CreditDetailActivity.class.getSimpleName(),"Credit: " + depense);


        mDescription = findViewById(R.id.description_depense);
        mDescription.getEditText().setText(depense.getDescription());
        mAmount = findViewById(R.id.amount_depense);
        mAmount.getEditText().setText(Integer.toString(depense.getAmount()));

        mValidate = findViewById(R.id.validate);
        mValidate.setOnClickListener(v -> {
            Depense updatedDepense = new Depense(
                    depense.getId(),mDescription.getEditText().getText().toString(),Integer.parseInt(mAmount.getEditText().getText().toString()),depense.getDate()

            );
            mDepenseRepository.update(updatedDepense)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, e -> {
                });
            Intent intent2 = new Intent(DepenseDetailActivity.this, DepenseActivity.class);
            startActivity(intent2);

        });



    }
}
