package com.curuza.domain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.movements.MovementStatus;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.photos.PhotoType;
import com.curuza.data.s3.S3Transfer;
import com.curuza.data.s3.S3TransferRepository;
import com.curuza.data.s3.S3TransferState;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.curuza.domain.common.BaseActivity;
import com.curuza.utils.DialogUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddArticle extends BaseActivity {
    private int GALLERY_REQUEST_CODE = 1;
    private int CAMERA_REQUEST_CODE = 2;
    private static final String TAG = AddArticle.class.getSimpleName();

    private Button mUploadImageButton;
    private ImageView mProductImageView;
    private ImageView mCameraIconView;
    private TextInputLayout mNameView;
    private TextInputLayout mDescriptionView;
    private TextInputLayout mQuantityView;
    private TextInputLayout mPrixAchatView;
    private TextInputLayout mPrixVenteView;
    private TextView mTitleErrorTextview;
    private TextView mDescriptionErrorTextview;
    private TextView mQuantityErrorTextView;
    private TextView mPAchatErrorTextview;
    private TextView mPVenteErrorTextview;
    private FloatingActionButton mAdd;
    private  String mDate;
    private String productId;

    private Uri mProductImageURI;
    Product mProduct;
    Movement mMovement;
    ProductRepository mProductRepository;
    MovementRepository mMovementRepository;
    S3TransferRepository mS3TransferRepository;
    PhotoRepository mPhotoRepository;
    private AlertDialog mPhotoUploadProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_article);


        Toolbar toolbar = findViewById (R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPhotoRepository = new PhotoRepository(this);
        mS3TransferRepository = new S3TransferRepository(this);

        mTitleErrorTextview = findViewById(R.id.title_error_textView);
        mDescriptionErrorTextview = findViewById(R.id.Description_error_textview);
        mQuantityErrorTextView = findViewById(R.id.quantity_textview_error);
        mPAchatErrorTextview = findViewById(R.id.p_achat_textview_error);
        mPVenteErrorTextview = findViewById(R.id.p_vente_textView_error);
        mUploadImageButton = findViewById(R.id.uploadbutton);
        mProductImageView = findViewById(R.id.image_content);
        mCameraIconView = findViewById(R.id.iconview);
        mNameView = findViewById(R.id.nom_stock);
        mDescriptionView = findViewById(R.id.description_stock);
        mQuantityView = findViewById(R.id.add_quantity_stock);
        mPrixAchatView = findViewById(R.id.p_vente_input_stock);
        mPrixVenteView = findViewById(R.id.p_vente_stock);

      //  mUploadImageButton.setOnClickListener(v -> choosePhotoFromGallery());


        mAdd = findViewById(R.id.floatingActionButton);
        mAdd.setOnClickListener(view -> {
            mDate = ZonedDateTime.now().toInstant().toString();

            if (mNameView.getEditText().length() == 0) {
                mTitleErrorTextview.setVisibility(View.VISIBLE);
            } else if (mQuantityView.getEditText().length() == 0) {
                mQuantityErrorTextView.setVisibility(View.VISIBLE);
            }
            if (mPrixAchatView.getEditText().length() == 0) {
                mPAchatErrorTextview.setVisibility(View.VISIBLE);
            }
            if (mPrixVenteView.getEditText().length() == 0) {
                mPVenteErrorTextview.setVisibility(View.VISIBLE);
            } else if (mNameView.getEditText().length() != 0 && mQuantityView.getEditText().length() != 0 && mPrixAchatView.getEditText().length() != 0 && mPrixVenteView.getEditText().length() != 0) {

                 productId = UUID.randomUUID().toString();

                mProductRepository = new ProductRepository(this);
                mMovementRepository = new MovementRepository(this);

                mPhotoRepository.uploadProductPhoto(productId, mProductImageURI)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onPhotoUploadSuccess, this::onPhotoUploadFailure);




                    mProductRepository.insert(generateProduct(productId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {

                        }, e -> {

                        });
                    mMovementRepository.insert(generateMovement(productId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {

                    }, e -> {

                    });
                Intent intent = new Intent(AddArticle.this, ProductsActivity.class);
                intent.putExtra(Product.PRODUCT_EXTRA, mProduct);
                startActivity(intent);


            }
        });
    }

    private Product generateProduct(String productId) {
        return new Product(
            productId,
            mProductImageURI,
            mNameView.getEditText().getText().toString(),
            mDescriptionView.getEditText().getText().toString(),
            Integer.parseInt(mQuantityView.getEditText().getText().toString()),
            Integer.parseInt(mPrixAchatView.getEditText().getText().toString()),
            Integer.parseInt(mPrixVenteView.getEditText().getText().toString()),
            mDate);
    }

    private Movement generateMovement(String productId) {
            return   new Movement(
                UUID.randomUUID().toString(),
                productId,
                Integer.parseInt(mQuantityView.getEditText().getText().toString()),
                Integer.parseInt(mPrixAchatView.getEditText().getText().toString()),
                Integer.parseInt(mPrixVenteView.getEditText().getText().toString()),
                mDate,
                MovementStatus.Enter);

    }

    public interface OnItemListener{

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
//        SearchView searchView =(SearchView) menuItem.getActionView();
//        searchView.requestFocus();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setQueryHint("search");
//
//
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.menu_item_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void choosePhotoFromGallery() {
        ImagePicker.with(this)
            .cropSquare()
            .maxResultSize(
                PhotoType.PRODUCT_PHOTO.getWidth(),
                PhotoType.PRODUCT_PHOTO.getHeight())
            .saveDir(getCacheDir())
            .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK) {
            onPhotoPicked(data.getData());
            Log.d(TAG, "ImagePicker data: " + data.getDataString());
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            DialogUtils.showErrorDialog(this);
        }
        super.onActivityResult(requestCode, resultCode, data);

   /*     super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == ImagePicker.REQUEST_CODE && data != null) {

            mProductImageURI = data.getData();

            Glide.with(this)
                    .load(mProductImageURI)
                    .into(mProductImageView);

            mCameraIconView.setVisibility(View.INVISIBLE);

        }*/
    }

    private void onPhotoPicked(Uri photoUri) {
        mPhotoUploadProgressDialog = DialogUtils.getProgressDialog(this, R.string.uploading_product_photo);
        //mPhotoUploadProgressDialog.show();

            mProductImageView.setImageURI(photoUri);
            mCameraIconView.setVisibility(View.GONE);

           mProductImageURI = photoUri;

    }

    private void onPhotoUploadSuccess() {

        mPhotoUploadProgressDialog.dismiss();
    }

    private void onPhotoUploadFailure(Throwable e) {
        mS3TransferRepository.addTransfer(new S3Transfer(productId, S3TransferState.Queued))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
        mPhotoUploadProgressDialog.dismiss();
        DialogUtils.showErrorDialog(this);
        Log.d(TAG, "Product  photo upload failed", e);
    }

}
