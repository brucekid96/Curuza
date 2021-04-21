package com.curuza.domain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {


    private List<Product> mListProduct;
    private Context mContext;
    private ProductRepository mStockRepository;
    private Product product;

    private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Product product);
    }

    public StockAdapter(List<Product> mListProduct, Context mContext, OnDeleteClickListener listener) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
        this.onDeleteClickListener = listener;
    }


    public void setData(List<Product>list) {
        this.mListProduct=list;
        notifyDataSetChanged();
    }


    private void showCardDialog() {
        mStockRepository = new ProductRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Product");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mStockRepository.delete(product);

                                break;
                        }
                    }
                });
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
         product = mListProduct.get(position);
        if(product ==null) {
            return;
        }

        holder.tvName.setText(product.getName());
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

        Glide.with(mContext)
                .load(product.getProductImageUri())
                .circleCrop()
                .into(holder.imgProducts);

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(mContext, ArticleDetails.class);
                intent.putExtra(Product.PRODUCT_EXTRA,product);
                mContext.startActivity(intent);
            }
        });
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showCardDialog();
                return true;
            }
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

        private ImageView imgProducts;
        private TextView tvName;
        private TextView tvQuantity;
        public ConstraintLayout container;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.name);
            tvQuantity = itemView.findViewById(R.id.quantity);
            container= itemView.findViewById(R.id.container_stock);

        }
    }
}

