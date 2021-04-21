package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.google.android.material.textfield.TextInputLayout;

public class CreditDetailActivity extends AppCompatActivity {


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

        Log.d(CreditDetailActivity.class.getSimpleName(),"Credit: " + credit);


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

                Intent intent2 = new Intent(CreditDetailActivity.this, CreditActivity.class);
                startActivity(intent2);

        });



    }
}
