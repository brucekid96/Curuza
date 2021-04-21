package com.curuza.domain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.stock.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private List<Product> mListProduct;
    private Context mContext;
    private OnItemListener mOnitemListener;
    ConstraintLayout mConstraint;
    private AddArticleFragemt mArticleFragment;


    public interface OnItemListener{
        void onItemClick(int position);
    }
    public ProductsAdapter(List<Product> mListProduct, Context mContext,OnItemListener OnitemListener) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
        this.mOnitemListener = OnitemListener;
        this.mArticleFragment = new AddArticleFragemt();
    }


    public void setData(List<Product>list) {
        this.mListProduct=list;
        notifyDataSetChanged();
    }


    // method to open a popup menu(list) upon long click in the recyclerView item



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

        Glide.with(mContext)
                .load(product.getProductImageUri())
                .circleCrop()
                .into(holder.imgProducts);

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mArticleFragment.setData(product);
                mArticleFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(),null);
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProducts;
        private TextView tvName;
        private TextView tvQuantity;
        public ConstraintLayout container;
        OnItemListener mOnItemListener;


        public ProductViewHolder(@NonNull View itemView,OnItemListener onitemListener) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.name);
            tvQuantity = itemView.findViewById(R.id.quantity);
            container= itemView.findViewById(R.id.container);
            mOnItemListener =  onitemListener;
        }
    }
}
