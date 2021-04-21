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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.client.ClientRepository;
import com.curuza.data.credit.Credit;
import com.curuza.data.depense.Depense;
import com.curuza.data.depense.DepenseRepository;

import java.util.List;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.ViewHolder> {

    private List<Depense> mDepenses;
    private Depense depense;
    private Context mContext;
    private OnDeleteClickListener onDeleteClickListener;
    private DepenseRepository mDepenseRepository;

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Depense depense);
    }

    public DepenseAdapter(List<Depense> listDepenses, Context mContext, OnDeleteClickListener listener) {
        this.mDepenses = listDepenses;
        this.mContext = mContext;
        this.onDeleteClickListener = listener;

    }


    public void setData(List<Depense>list) {
        this.mDepenses=list;
        notifyDataSetChanged();
    }

    private void showCardDialog() {
        mDepenseRepository = new DepenseRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Depense");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mDepenseRepository.delete(depense);

                                break;
                        }
                    }
                });
        cardDialog.show();
    }


    @NonNull
    @Override
    public DepenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.depense_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepenseAdapter.ViewHolder holder, int position) {
         depense = mDepenses.get(position);
        if(depense ==null) {
            return;
        }

        holder.tvDescription.setText(depense.getDescription());
        holder.tvAmount.setText(String.valueOf(depense.getAmount()));

        holder.tvDate.setText(DateTimeUtils.getDateString(depense.getDate()));

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, DepenseDetailActivity.class);
                intent.putExtra(Depense.DEPENSE_EXTRA,depense);
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
        if (mDepenses != null) {
            return mDepenses.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvAmount;
        public ConstraintLayout container;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.description);
            tvDate = itemView.findViewById(R.id.date);
            tvAmount = itemView.findViewById(R.id.amount);
            container= itemView.findViewById(R.id.container);

        }
    }
}
