package com.curuza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private List<Product> mListProduct;

    public void setData(List<Product>list) {
        this.mListProduct=list;
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
        holder.imgProducts.setImageResource(product.getResourceId());
        holder.tvName.setText(product.getName());
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


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_products);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
