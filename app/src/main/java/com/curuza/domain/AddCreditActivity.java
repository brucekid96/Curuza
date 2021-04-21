package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

public class AddCreditActivity extends AppCompatActivity {


    private Credit mCredit;
    private TextInputLayout mName;
    private TextInputLayout mPhoneNumber;
    private TextInputLayout mDescription;
    private TextInputLayout mAmount;
    private  String mDate;
    private Button mValidate;
    CreditRepository mCreditRepository;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit);

        Intent intent = getIntent();
        mCredit = intent.getParcelableExtra(Credit.CREDIT_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCreditRepository = new CreditRepository(getApplicationContext());


        mName= findViewById(R.id.nom_credit);
        mPhoneNumber = findViewById(R.id.tel_number);
        mDescription = findViewById(R.id.description_credit);
        mAmount = findViewById(R.id.amount_credit);
        mValidate = findViewById(R.id.credit_validate);

        mValidate.setOnClickListener(view -> {

            mDate = ZonedDateTime.now().toInstant().toString();
            Credit credit = new Credit(
                            UUID.randomUUID().toString(),
                            mName.getEditText().getText().toString(),
                            mDescription.getEditText().getText().toString(),
                    Integer.parseInt(mAmount.getEditText().getText().toString()),
                            mDate,
                            mPhoneNumber.getEditText().getText().toString()
                    );
            mCreditRepository.insert(credit);
            Log.d(AddCreditActivity.class.getSimpleName(), "Added credit: " + credit.toString());

            Intent intent1 = new Intent(AddCreditActivity.this, CreditActivity.class);
            intent1.putExtra(Credit.CREDIT_EXTRA, credit);
            startActivity(intent1);


            });







    }

    public void setData(Credit credit) {
        mCredit = credit;
    }


}
