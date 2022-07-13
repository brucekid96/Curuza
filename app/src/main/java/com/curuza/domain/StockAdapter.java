package com.curuza.domain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.photos.PhotoType;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private List<Product> mListProduct;
    private Context mContext;
    private ProductRepository mStockRepository;
    private PhotoRepository mPhotoRepository;

    private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Product product);
    }

    public StockAdapter(List<Product> mListProduct,Context mContext) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
        mPhotoRepository = new PhotoRepository(mContext);
    }


    public void setData(List<Product>list) {
        this.mListProduct=list;
        notifyDataSetChanged();
    }


    private void showCardDialog(Product product) {
        mStockRepository = new ProductRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Product");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                (dialog, which) -> {
                    if (which == 0) {
                        mStockRepository.delete(product);
                    }
                });;
        cardDialog.show();
    }


    @NonNull
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, int position) {
       Product  product = mListProduct.get(position);

        holder.bind(product);

        holder.container.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, StockDetail.class);
            intent.putExtra(Product.PRODUCT_EXTRA,product);
            mContext.startActivity(intent);
        });
        holder.container.setOnLongClickListener(v -> {
            showCardDialog(product);
            return true;
        });

    }


    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvQuantity;
        public ConstraintLayout container;

        public void bind(Product product) {
            tvName.setText(product.getName());
            tvQuantity.setText(String.valueOf(product.getQuantity()));
            displayCoverThumbnail(product.getId());
            mPhotoRepository.fetchPhotoUpstream(product.getId(), PhotoType.PRODUCT_THUMBNAIL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> displayCoverThumbnail(product.getId()));
        }

        private void displayCoverThumbnail(String productId) {
            File thumbnailFile =
                new File(mPhotoRepository.getProductPhotoUri(productId).getPath());

            if(thumbnailFile.exists()) {
                Glide.with(mContext)
                    .load(thumbnailFile)
                    .into(imgProduct);
            } else {
                imgProduct.setImageResource(R.drawable.ic_baseline_shopping_basket_green);
            }
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.name);
            tvQuantity = itemView.findViewById(R.id.quantity);
            container= itemView.findViewById(R.id.container_stock);

        }
    }
}

