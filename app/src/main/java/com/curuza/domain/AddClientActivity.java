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
import com.curuza.data.client.ClientRepository;
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

public class AddClientActivity extends AppCompatActivity {

    private Client mClient;
    private TextInputLayout mName;
    private TextInputLayout mPhoneNumber;
    private TextInputLayout mDescription;
    private TextView mNameErrorTextview;
    private TextInputLayout mAmount;
    private  String mDate;
    private Button mValidate;
    ClientRepository mClientRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);

        Intent intent = getIntent();
        mClient = intent.getParcelableExtra(Client.CLIENT_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mClientRepository = new ClientRepository(getApplicationContext());

        mNameErrorTextview =findViewById(R.id.name_textview_error);
        mName= findViewById(R.id.nom_client);
        mPhoneNumber = findViewById(R.id.tel_number);
        mDescription = findViewById(R.id.description_client);
        mValidate = findViewById(R.id.client_validate);

        mValidate.setOnClickListener(view -> {
            mDate = ZonedDateTime.now().toInstant().toString();

            if (mName.getEditText().length()==0) {
                mNameErrorTextview.setVisibility(View.VISIBLE);
            }
            else
                if(mName.getEditText().length()!=0) {

                    Client client = new Client(
                            UUID.randomUUID().toString(),
                            mName.getEditText().getText().toString(),
                            mDescription.getEditText().getText().toString(),
                            mDate,
                            mPhoneNumber.getEditText().getText().toString()
                    );
                    mClientRepository.insert(client);
                    Log.d(AddClientActivity.class.getSimpleName(), "Added client: " + client.toString());

                    Intent intent1 = new Intent(AddClientActivity.this, ClientActivity.class);
                    intent1.putExtra(Client.CLIENT_EXTRA, client);
                    startActivity(intent1);
                }

        });


    }

    public void setData(Client client) {
        mClient = client;
    }
}
