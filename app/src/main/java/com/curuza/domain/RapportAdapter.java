package com.curuza.domain;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.view.Rapport;
import com.curuza.data.view.ProductMovement;

import java.util.List;

public class RapportAdapter extends RecyclerView.Adapter<RapportAdapter.MyViewHolder>  {

    private List<Rapport> mRapportList;
    private Rapport rapport;
    private Context mContext;
    private OnItemListener mOnitemListener;
    private MovementRepository movementRepository;

    public interface OnItemListener{
        void onItemClick(int position);
    }
    public RapportAdapter(List<Rapport> listRapport, Context mContext, OnItemListener OnitemListener) {
        this.mRapportList = listRapport;
        this.mContext = mContext;
        this.mOnitemListener = OnitemListener;
    }

    public void setData(List<Rapport> rapportList) {
        mRapportList = rapportList;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rapport_item,parent,false);
        return new MyViewHolder(v,mOnitemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Rapport rapport = mRapportList.get(position);
        holder.Date.setText(rapport.getMovement().getDate());
        holder.Amount.setText(rapport.getMovement().getPVente());

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
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
        OnItemListener mOnItemListener;
        private final Context context;

        public MyViewHolder(@NonNull View itemView,OnItemListener onitemListener) {
            super(itemView);
            context = itemView.getContext();
            Date = itemView.findViewById(R.id.date);
            Amount = itemView.findViewById(R.id.amount);
            container= itemView.findViewById(R.id.container_rapport);
            mOnItemListener =  onitemListener;

        }


    }


}
