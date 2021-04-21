package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.stock.Product;
import com.google.android.material.textfield.TextInputLayout;

public class ArticleDetails extends AppCompatActivity {


    private Product produit;
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
        setContentView(R.layout.article_detail);

        Intent intent = getIntent();
        produit = intent.getParcelableExtra(Product.PRODUCT_EXTRA);

        Toolbar toolbar = findViewById (R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mArticleImageView = findViewById(R.id.image_content);
        mCameraIconView = findViewById(R.id.iconview);
        mArticleImageView.setImageURI(produit.getProductImageUri());
        mCameraIconView.setVisibility(View.INVISIBLE);
        mName = findViewById(R.id.nom_stock);
        mName.getEditText().setText(produit.getName());
        mDescription = findViewById(R.id.description_stock);
        mDescription.getEditText().setText(produit.getDescription());
        mQuantity = findViewById(R.id.add_quantity_stock);
        mQuantity.getEditText().setText(Integer.toString(produit.getQuantity()));
        mPxAchat = findViewById(R.id.p_vente_input_stock);
        mPxAchat.getEditText().setText(Integer.toString(produit.getPAchat()));
        mPxVente = findViewById(R.id.p_vente_stock);
        mPxVente.getEditText().setText(Integer.toString(produit.getPVente()));




    }
}
