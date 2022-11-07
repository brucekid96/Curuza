package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.curuza.domain.common.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreditDetailActivity extends BaseActivity {


    private Credit credit;
    private Button mValidate;
    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mTelephone;
    private TextInputLayout mAmount;
    private CreditRepository mCreditRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_detail);

        Intent intent = getIntent();
        credit = intent.getParcelableExtra(Credit.CREDIT_EXTRA);


        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCreditRepository = new CreditRepository(this);


        mName = findViewById(R.id.nom_credit);
        mName.getEditText().setText(credit.getPersonName());
        mDescription = findViewById(R.id.description_credit);
        mDescription.getEditText().setText(credit.getDescription());
        mAmount = findViewById(R.id.amount_credit);
        mAmount.getEditText().setText(Integer.toString(credit.getAmount()));
        mTelephone = findViewById(R.id.tel_number);
        mTelephone.getEditText().setText(credit.getTelephone());

        mValidate = findViewById(R.id.credit_validate);
        mValidate.setOnClickListener(v -> {
           Credit updatedCredit = new Credit(
               credit.getId(),mName.getEditText().getText().toString(),mDescription.getEditText().getText().toString(),Integer.parseInt(mAmount.getEditText().getText().toString()),credit.getDate(),mTelephone.getEditText().getText().toString());
            mCreditRepository.update(updatedCredit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, e -> {
                });
                Intent intent2 = new Intent(CreditDetailActivity.this, CreditActivity.class);
                startActivity(intent2);

        });



    }
}
