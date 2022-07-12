package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.curuza.R;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SellArticleDetails  extends AppCompatActivity {


    private Product produit;
    private ImageView mArticleImageView;
    private TextInputLayout mName;
    private TextInputLayout mQuantity;
    private TextInputLayout mAmount;
    private Button mValidate;
    ProductRepository mProductRepository;
    MovementRepository mMovementRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_article_details);

        Intent intent = getIntent();
        produit = intent.getParcelableExtra(Product.PRODUCT_EXTRA);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProductRepository = new ProductRepository(getApplicationContext());
        mMovementRepository = new MovementRepository(getApplicationContext());

        int P_Vente = (produit.getPVente());

        mArticleImageView = findViewById(R.id.image_content);
        mArticleImageView.setImageURI(produit.getProductImageUri());
        mName = findViewById(R.id.nom_stock);
        mName.getEditText().setText(produit.getName());
        mQuantity = findViewById(R.id.add_quantity_stock);
        mQuantity.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                       if (s.toString().isEmpty()){
                            return;
                        }

                      Integer PutQuantity= Integer.parseInt(s.toString());
                        Log.d("somme", String.valueOf(PutQuantity*P_Vente));
                        String total = String.valueOf(PutQuantity*P_Vente);
                        mAmount.getEditText().setText(total);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                }
        );
        mAmount = findViewById(R.id.name_textview_error);
        mValidate = findViewById(R.id.credit_validate);

        mValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(areInputsValid()&&produit.getQuantity()>= Integer.parseInt(mQuantity.getEditText().getText().toString())){

                int inputQuantity = Integer.parseInt(mQuantity.getEditText().getText().toString());
                produit.setQuantity(produit.getQuantity() - inputQuantity);

                Movement movement = new Movement(
                        UUID.randomUUID().toString(),
                        produit.getId(),
                        inputQuantity,
                        produit.getPAchat(),
                        produit.getPVente(),
                        ZonedDateTime.now().toInstant().toString(),
                        MovementStatus.Exit
                );

                mProductRepository.update(produit)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                    }, e -> {
                    });
                mMovementRepository.insert(movement)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                    }, e -> {
                    });

            }

                Intent intent = new Intent(SellArticleDetails.this, SellArticleActivity.class);
                startActivity(intent);

            }
        });


    }

    public void setData(Product product) {
        produit = product;
    }

    private Boolean areInputsValid() {
        String inputQuantity = mQuantity.getEditText().getText().toString();
        return !inputQuantity.isEmpty();
    }


}
