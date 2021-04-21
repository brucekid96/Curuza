package com.curuza.domain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.curuza.data.movements.Movement;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.curuza.data.view.ProductMovement;

import java.util.ArrayList;
import java.util.List;

public class ProductMovementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private static final int VIEW_TYPE_Enter = 1;
    private static final int VIEW_TYPE_Exit = 2;
    private Context mContext;
    private List<ProductMovement> mProductMovements;
    private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Movement movement);
    }



    public ProductMovementsAdapter(Context mContext, OnDeleteClickListener listener) {
        this.mContext = mContext;
        mProductMovements = new ArrayList<>();
        this.onDeleteClickListener = listener;

    }


    public void setData(List<ProductMovement> productMovement) {
        this.mProductMovements =productMovement;
        notifyDataSetChanged();
    }

    private void showCardDialog() {
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Movement");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                break;
                        }
                    }
                });
        cardDialog.show();
    }

    private class EnterViewHolder extends RecyclerView.ViewHolder {


        private ImageView imgProducts;
        private TextView tvName;
        private TextView tvQuantity;
        private TextView tvDate;
        private TextView tvAmount;
        private TextView tvTotalAmount;
        public ConstraintLayout container;
        EnterViewHolder(final View itemView) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.product_name_update);
            tvDate = itemView.findViewById(R.id.date);
            tvAmount = itemView.findViewById(R.id.amount_piece);
            tvTotalAmount = itemView.findViewById(R.id.total_amount);
            tvQuantity = itemView.findViewById(R.id.quantit);
            container = itemView.findViewById(R.id.container);
            // Initialize your All views prensent in list items
        }
        void bind(ProductMovement productMovement) {
            Log.d(ProductMovementsAdapter.class.getSimpleName(),"WHAT HAPPEN:"+productMovement.toString());

            tvName.setText(productMovement.getProduct().getName());
            tvQuantity.setText(String.valueOf(productMovement.getMovement().getQuantity()));
            tvDate.setText(DateTimeUtils.getDateString(productMovement.getMovement().getDate()));
            tvTotalAmount.setText(String.valueOf(productMovement.getMovement().getPAchat()*productMovement.getMovement().getQuantity()));
            tvAmount.setText(String.valueOf(productMovement.getProduct().getPAchat()));


            Glide.with(mContext)
                    .load(productMovement.getProduct().getProductImageUri())
                    .circleCrop()
                    .into(imgProducts);

            container.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, EnterMovementDetail.class);
                    intent.putExtra(ProductMovement.ProductMovement_EXTRA,productMovement);
                    mContext.startActivity(intent);
                }
            });
            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showCardDialog();
                    return true;
                }
            });

        }
    }
    private class ExitViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProducts;
        private TextView tvName;
        private TextView tvQuantity;
        private TextView tvDate;
        private TextView tvAmount;
        private TextView tvTotalAmount;
        public ConstraintLayout container;
        ExitViewHolder(final View itemView) {
            super(itemView);

            imgProducts = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.product_name_update);
            tvDate = itemView.findViewById(R.id.date);
            tvQuantity = itemView.findViewById(R.id.quantit);
            tvAmount= itemView.findViewById(R.id.amount_piece);
           tvTotalAmount= itemView.findViewById(R.id.total_amount);
            container= itemView.findViewById(R.id.container);
        }
        void bind(ProductMovement productMovement) {


            tvName.setText(productMovement.getProduct().getName());
            tvQuantity.setText(String.valueOf(productMovement.getMovement().getQuantity()));
            tvDate.setText(DateTimeUtils.getDateString(productMovement.getMovement().getDate()));
            tvTotalAmount.setText(String.valueOf(productMovement.getMovement().getPVente()*productMovement.getMovement().getQuantity()));
            tvAmount.setText(String.valueOf(productMovement.getProduct().getPVente()));


            Glide.with(mContext)
                    .load(productMovement.getProduct().getProductImageUri())
                    .circleCrop()
                    .into(imgProducts);
            container.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, SellMovementDetail.class);
                    intent.putExtra(ProductMovement.ProductMovement_EXTRA,productMovement);
                    mContext.startActivity(intent);
                }
            });
            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showCardDialog();
                    return true;
                }
            });
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_Enter:
            return new EnterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.entree_item, parent, false));
            case VIEW_TYPE_Exit:
        return new ExitViewHolder(LayoutInflater.from(mContext).inflate(R.layout.sortie_item, parent, false));
            default:
                throw new IllegalArgumentException("The provided viewType is not valid");
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductMovement productMovement = mProductMovements.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_Enter: {
                EnterViewHolder viewHolder = (EnterViewHolder) holder;
                viewHolder.bind(productMovement);
                break;
            }
            case VIEW_TYPE_Exit: {
                ExitViewHolder exitViewHolder = (ExitViewHolder) holder;
                exitViewHolder.bind(productMovement);
                break;
            }

            default:
                throw new IllegalArgumentException("The viewType associated with position is not valid");

        }
    }


    @Override
    public int getItemCount() {
        if (mProductMovements != null) {
            return mProductMovements.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
     ProductMovement productMovement = mProductMovements.get(position);
        return productMovement.getMovement().getStatus()== RequestStatus.Enter?VIEW_TYPE_Enter:VIEW_TYPE_Exit;
    }
    }
