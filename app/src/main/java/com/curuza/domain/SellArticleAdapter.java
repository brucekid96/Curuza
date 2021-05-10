package com.curuza.domain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.stock.Product;

import java.util.List;

public class SellArticleAdapter extends RecyclerView.Adapter<SellArticleAdapter.ProductViewHolder>{

    private List<Product> mListProduct;
    private Context mContext;
    private SellArticleAdapter.OnItemListener mOnitemListener;
    private SellArticleFragment mSellFragment;

    public interface OnItemListener{
        void onItemClick(int position);
    }
    public SellArticleAdapter(List<Product> mListProduct, Context mContext, OnItemListener OnitemListener) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
        this.mOnitemListener = OnitemListener;
    }


    public void setData(List<Product>list) {
        this.mListProduct=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products,parent,false);
        return new ProductViewHolder(view,mOnitemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product ==null) {
            return;
        }

        holder.tvName.setText(product.getName());
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

       if(product.getProductImageUri()!=null) {
           Glide.with(mContext)
                   .load(product.getProductImageUri())
                   .circleCrop()
                   .into(holder.imgProducts);
       }



        holder.container.setOnClickListener(v -> {
            mSellFragment = new SellArticleFragment();
            mSellFragment.setData(product);
            mSellFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(),null);
        });

    }


    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProducts;
        private TextView tvName;
        private TextView tvQuantity;
        public ConstraintLayout container;
        OnItemListener mOnItemListener;


        public ProductViewHolder(@NonNull View itemView, OnItemListener onitemListener) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.name);
            tvQuantity = itemView.findViewById(R.id.quantity);
            container= itemView.findViewById(R.id.container);
            mOnItemListener =  onitemListener;
        }
    }
}
