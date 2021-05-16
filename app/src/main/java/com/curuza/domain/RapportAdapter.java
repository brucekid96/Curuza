package com.curuza.domain;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.stock.Product;
import com.curuza.data.view.Rapport;
import com.curuza.data.view.ProductMovement;
import com.curuza.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class RapportAdapter extends RecyclerView.Adapter<RapportAdapter.MyViewHolder>  {

    private List<Rapport> mRapportList;
    private Context mContext;


    public RapportAdapter(Context mContext) {
        this.mRapportList = new ArrayList<>();
        this.mContext = mContext;

    }

    public void setData(List<Rapport> rapportList) {
        mRapportList = rapportList;
        notifyDataSetChanged();
        Log.d(RapportAdapter.class.getSimpleName(),"rapportList" + rapportList);

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rapport_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rapport rapport = mRapportList.get(position);
        holder.Date.setText(rapport.getDate());
        holder.Amount.setText(FormatUtils.getLocalizedMonetaryAmountString(rapport.getTotalVente()));

        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RapportOverviewActivity.class);
            intent.putExtra(Rapport.RAPPORT_EXTRA,rapport);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mRapportList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Date;
        public TextView Amount;
        public ConstraintLayout container;
        private final Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            Date = itemView.findViewById(R.id.date);
            Amount = itemView.findViewById(R.id.amount);
            container= itemView.findViewById(R.id.container_rapport);

        }


    }


}
