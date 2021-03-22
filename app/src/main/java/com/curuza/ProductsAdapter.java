package com.curuza;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private List<Product> mListProduct;
    private Context mContext;

    public void setData(List<Product>list,Context mContext) {
        this.mListProduct=list;
        this.mContext = mContext;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product ==null) {
            return;
        }

        holder.tvName.setText(product.getName());
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

        Glide.with(mContext)
                .load(product.getResourceId())
                .circleCrop()
                .into(holder.imgProducts);

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


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.name);
            tvQuantity = itemView.findViewById(R.id.quantity);
        }
    }
}
