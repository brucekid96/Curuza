package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;

import com.curuza.data.client.Client;
import com.curuza.data.depense.Depense;
import com.curuza.data.depense.DepenseRepository;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

public class AddDepenseActivity extends AppCompatActivity {

    private Depense mDepense;
    private TextView mDescriptionErrorTextview;
    private TextView mAmountErrorTextview;
    private TextInputLayout mDescription;
    private TextInputLayout mAmount;
    private  String mDate;
    private Button mValidate;
    DepenseRepository mDepenseRepository;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_depense);

        Intent intent = getIntent();
        mDepense = intent.getParcelableExtra(Depense.DEPENSE_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDepenseRepository = new DepenseRepository(getApplicationContext());

        mDescriptionErrorTextview =findViewById(R.id.description_textview_error);
        mAmountErrorTextview = findViewById(R.id.amount_txview_error);
        mDescription = findViewById(R.id.description);
        mAmount = findViewById(R.id.amount);
        mValidate = findViewById(R.id.validate);

        mValidate.setOnClickListener(view -> {
            mDate = ZonedDateTime.now().toInstant().toString();
            if (mDescription.getEditText().length()==0) {
                mDescriptionErrorTextview.setVisibility(View.VISIBLE);
            } else
            if(mAmount.getEditText().length()==0) {
                mAmountErrorTextview.setVisibility(View.VISIBLE);
            }
            else
            if(mDescription.getEditText().length()!=0 && mAmount.getEditText().length()!=0) {

                Depense depense = new Depense(
                        UUID.randomUUID().toString(),
                        mDescription.getEditText().getText().toString(),
                        Integer.parseInt(mAmount.getEditText().getText().toString()),
                        mDate
                );
                mDepenseRepository.insert(depense);
                Log.d(AddDepenseActivity.class.getSimpleName(), "Added depense: " + depense.toString());

                Intent intent1 = new Intent(AddDepenseActivity.this, DepenseActivity.class);
                intent1.putExtra(Depense.DEPENSE_EXTRA, depense);
                startActivity(intent1);
            }



        });







    }

    public void setData(Depense depense) {
        mDepense = depense;
    }


}
