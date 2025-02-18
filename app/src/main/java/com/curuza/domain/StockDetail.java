package com.curuza.domain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.photos.PhotoType;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.curuza.domain.common.BaseActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StockDetail extends BaseActivity {
    private int GALLERY_REQUEST_CODE = 1;
    private int CAMERA_REQUEST_CODE = 2;


    private Product produit;
    private Button mUploadImageButton;
    private ImageView mCameraIconView;
    private ImageView mArticleImageView;
    private TextInputLayout mName;
    private TextInputLayout mDescription;
    private TextInputLayout mQuantity;
    private TextInputLayout mPxAchat;
    private TextInputLayout mPxVente;
    private String mDate;
    private FloatingActionButton mFab;

    private Uri mProductImageURI;
    ProductRepository mProductRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_detail);

        Intent intent = getIntent();
        produit = intent.getParcelableExtra(Product.PRODUCT_EXTRA);
        mProductImageURI = null/*produit.getProductImageUri()*/;
        Log.d(StockDetail.class.getSimpleName(), "Retrieved produit: " + produit);

        Toolbar toolbar = findViewById (R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProductRepository = new ProductRepository(this);

        //mArticleImageView = findViewById(R.id.image_content);
       // mCameraIconView = findViewById(R.id.iconview);
       // mArticleImageView.setImageURI(produit.getProductImageUri());
      //  mCameraIconView.setVisibility(View.INVISIBLE);
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
        /*mUploadImageButton = findViewById(R.id.uploadbutton);
        mUploadImageButton.setOnClickListener(v -> choosePhotoFromGallary());*/
        mFab = findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(v -> {
            mDate = ZonedDateTime.now().toInstant().toString();

           Product productUpdate= new Product( produit.getId(),
                   mProductImageURI,
                   mName.getEditText().getText().toString(),
                   mDescription.getEditText().getText().toString(),
                   Integer.parseInt(mQuantity.getEditText().getText().toString()),
                   Integer.parseInt(mPxAchat.getEditText().getText().toString()),
                   Integer.parseInt(mPxVente.getEditText().getText().toString()),
                   mDate);
            mProductRepository.update(productUpdate)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, e -> {
                });
            Intent intent2 = new Intent(StockDetail.this, StockActivity.class);
            startActivity(intent2);

        });

    }


    public void choosePhotoFromGallary() {
        ImagePicker.with(this)
            .cropSquare()
            .maxResultSize(
                PhotoType.PRODUCT_PHOTO.getWidth(),
                PhotoType.PRODUCT_PHOTO.getHeight())
            .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == ImagePicker.REQUEST_CODE && data != null) {

            mProductImageURI = data.getData();

            Glide.with(this)
                    .load(mProductImageURI)
                   .into(mArticleImageView);

            Log.d(StockDetail.class.getSimpleName(),"verifie Uri" + mProductImageURI);

            mCameraIconView.setVisibility(View.INVISIBLE);

        }
    }
}
