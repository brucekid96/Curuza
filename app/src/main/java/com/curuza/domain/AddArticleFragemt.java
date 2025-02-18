package com.curuza.domain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.curuza.R;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.movements.MovementStatus;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.ZonedDateTime;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddArticleFragemt extends BottomSheetDialogFragment  {
    private  static  final String DBG_TAG = AddArticleFragemt.class.getSimpleName();

    private TextInputLayout mQuantityInputLayout;
    private EditText mName;
    private EditText mQuantity;
    private Button mSave;
    private Button mCancel;
    private ImageView product_image;
    private TextView product_name;
    private ProductsAdapter mProductAdapter;
    ProductsAdapter.OnItemListener mOnitemListener;
    ProductRepository mProductRepository;
    MovementRepository mMovementRepository;
    PhotoRepository mPhotoRepository;
    Product mProduct;
    Movement mMovement;
    public static void showDialog(FragmentManager fragmentManager) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bottom_sheet_dialg, container, false);
        product_image = root.findViewById(R.id.pict_people);
        mName = root.findViewById(R.id.name);
        mQuantity = root.findViewById(R.id.quantiti);
        mSave = root.findViewById(R.id.submit);
        mSave.setOnClickListener(v -> onSaveButtonClicked());

        return root;

    }

  public void setData(Product product) {
        mProduct = product;
  }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProductRepository = new ProductRepository(getContext());
        mMovementRepository = new MovementRepository(getContext());
        mPhotoRepository = new PhotoRepository(getContext());
        product_image.setImageURI(mPhotoRepository.getProductPhotoUri(mProduct.getId()));
        mName.setText(mProduct.getName());
    }

    private void onSaveButtonClicked() {

              Log.d(DBG_TAG,"onSaveButtonCLicked");
        if(areInputsValid()) {
            int inputQuantity = Integer.parseInt(mQuantity.getText().toString());
            mProduct.setQuantity(mProduct.getQuantity() + inputQuantity);

            Movement movement = new Movement(
                    UUID.randomUUID().toString(),
                    mProduct.getId(),
                    inputQuantity,
                    mProduct.getPAchat(),
                    mProduct.getPVente(),
                    ZonedDateTime.now().toInstant().toString(),
                    MovementStatus.Enter
            );

            mProductRepository.update(mProduct)
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

            dismiss();
        }

    }

    private Boolean areInputsValid() {
        String inputQuantity = mQuantity.getText().toString();
        Log.d(DBG_TAG, "Entered quantity: " + inputQuantity);
        return !inputQuantity.isEmpty();
    }


}
