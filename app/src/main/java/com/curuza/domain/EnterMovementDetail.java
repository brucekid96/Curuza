package com.curuza.domain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.view.ProductMovement;
import com.curuza.domain.common.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

public class EnterMovementDetail extends BaseActivity {


    private ProductMovement produit;
    private Button mUploadImageButton;
    private ImageView mCameraIconView;
    private ImageView mArticleImageView;
    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mQuantity;
    private TextInputLayout mPxAchat;
    private TextInputLayout mAmount;

    private PhotoRepository photoRepository;
    private ProductMovement productMovement;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_movement_detail);



        Intent intent = getIntent();
        produit = intent.getParcelableExtra(ProductMovement.ProductMovement_EXTRA);

        setupToolbar();

        photoRepository = new PhotoRepository(getApplicationContext());



        mArticleImageView = findViewById(R.id.image_content);
       // mCameraIconView = findViewById(R.id.iconview);
       // mArticleImageView.setImageURI(produit.getProduct().getProductImageUri());
       // mCameraIconView.setVisibility(View.INVISIBLE);
        mName = findViewById(R.id.nom_enter);
        mName.getEditText().setText(produit.getProduct().getName());
        mDescription = findViewById(R.id.description_enter);
        mDescription.getEditText().setText(produit.getProduct().getDescription());
        mQuantity = findViewById(R.id.add_quantity_enter);
        mQuantity.getEditText().setText(Integer.toString(produit.getMovement().getQuantity()));
        mAmount= findViewById(R.id.p_achat_input_enter);
        mAmount.getEditText().setText(Integer.toString(produit.getMovement().getPAchat()*produit.getMovement().getQuantity()));

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_enter_movement_detail);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void displayCoverThumbnail(String productId) {
        mArticleImageView.setImageResource(R.drawable.ic_baseline_shopping_basket_green);
        /*File thumbnailFile =
            new File(photoRepository.getProductThumbnailUri(productId).getPath());

        if(thumbnailFile.exists()) {
            Glide.with(mContext)
                .load(thumbnailFile)
                .into(mArticleImageView);
        } else {
            mArticleImageView.setImageResource(R.drawable.ic_baseline_shopping_basket_green);
        }*/
    }
}
