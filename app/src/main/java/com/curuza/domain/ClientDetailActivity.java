package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.client.Client;
import com.curuza.data.client.ClientRepository;


import com.google.android.material.textfield.TextInputLayout;

public class ClientDetailActivity extends AppCompatActivity {
    private Client client;
    private Button mValidate;
    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mTelephone;
    private TextInputLayout mAmount;
    private ClientRepository mClientRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_detail);

        Intent intent = getIntent();
        client = intent.getParcelableExtra(Client.CLIENT_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d(ClientDetailActivity.class.getSimpleName(),"Client: " + client);


        mName = findViewById(R.id.nom_client);
        mName.getEditText().setText(client.getPersonName());
        mDescription = findViewById(R.id.description_client);
        mDescription.getEditText().setText(client.getDescription());
        mTelephone = findViewById(R.id.tel_number);
        mTelephone.getEditText().setText(client.getTelephone());

        mValidate = findViewById(R.id.client_validate);
        mValidate.setOnClickListener(v -> {

            Intent intent2 = new Intent(ClientDetailActivity.this, ClientActivity.class);
            startActivity(intent2);

        });



    }
}
