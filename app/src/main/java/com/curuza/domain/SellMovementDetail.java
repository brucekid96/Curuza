package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.view.ProductMovement;
import com.google.android.material.textfield.TextInputLayout;

public class SellMovementDetail extends AppCompatActivity {


    private ProductMovement produit;
    private Button mUploadImageButton;
    private ImageView mCameraIconView;
    private ImageView mArticleImageView;
    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mQuantity;
    private TextInputLayout mPxAchat;
    private TextInputLayout mPxVente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_movement_detail);

        Intent intent = getIntent();
        produit = intent.getParcelableExtra(ProductMovement.ProductMovement_EXTRA);

        Toolbar toolbar = findViewById (R.id.toolbar_sell_movement_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mArticleImageView = findViewById(R.id.image_content);
        mCameraIconView = findViewById(R.id.iconview);
        mArticleImageView.setImageURI(produit.getProduct().getProductImageUri());
        mCameraIconView.setVisibility(View.INVISIBLE);
        mName = findViewById(R.id.nom_sell);
        mName.getEditText().setText(produit.getProduct().getName());
        mDescription = findViewById(R.id.description_sell);
        mDescription.getEditText().setText(produit.getProduct().getDescription());
        mQuantity = findViewById(R.id.add_quantity_sell);
        mQuantity.getEditText().setText(Integer.toString(produit.getMovement().getQuantity()));
        mPxVente = findViewById(R.id.p_vente_input_sell);
        mPxVente.getEditText().setText(Integer.toString(produit.getMovement().getPVente()*produit.getMovement().getQuantity()));




    }
}