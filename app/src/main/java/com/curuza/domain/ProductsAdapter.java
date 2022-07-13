package com.curuza.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.photos.PhotoType;
import com.curuza.data.stock.Product;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private List<Product> mListProduct;
    private Context mContext;
    private OnItemListener mOnitemListener;
    private PhotoRepository mPhotoRepository;
    private AddArticleFragemt mArticleFragment;


    public interface OnItemListener{
        void onItemClick(int position);
    }
    public ProductsAdapter(List<Product> mListProduct, Context mContext,OnItemListener OnitemListener) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
        this.mOnitemListener = OnitemListener;
        mPhotoRepository = new PhotoRepository(mContext);

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
        holder.bind(product);
        holder.container.setOnClickListener(v -> {
            mArticleFragment = new AddArticleFragemt();
            mArticleFragment.setData(product);
            mArticleFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(),null);
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

        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvQuantity;
        public ConstraintLayout container;
        OnItemListener mOnItemListener;

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

        public ProductViewHolder(@NonNull View itemView,OnItemListener onitemListener) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.name);
            tvQuantity = itemView.findViewById(R.id.quantity);
            container= itemView.findViewById(R.id.container);
            mOnItemListener =  onitemListener;
        }
    }


}
