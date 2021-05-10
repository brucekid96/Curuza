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
import com.curuza.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.ViewHolder> {

    private List<Depense> mDepenses;
    private Context mContext;
    private DepenseRepository mDepenseRepository;



    public DepenseAdapter(List<Depense> mListDepense, Context mContext) {
        this.mDepenses = mListDepense;
        this.mContext = mContext;


    }


    public void setData(List<Depense>list) {
        this.mDepenses=list;
        notifyDataSetChanged();
    }

    private void showCardDialog(Depense depense) {
        mDepenseRepository = new DepenseRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Depense");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            mDepenseRepository.delete(depense);

                            break;
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
       Depense  depense = mDepenses.get(position);

        holder.tvDescription.setText(depense.getDescription());
        holder.tvAmount.setText(FormatUtils.getLocalizedMonetaryAmountString(depense.getAmount()));

        holder.tvDate.setText(DateTimeUtils.getDateString(depense.getDate()));

        holder.container.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, DepenseDetailActivity.class);
            intent.putExtra(Depense.DEPENSE_EXTRA,depense);
            mContext.startActivity(intent);
        });
        holder.container.setOnLongClickListener(v -> {
            showCardDialog(depense);
            return true;
        });

    }


    @Override
    public int getItemCount() {
            return mDepenses.size();
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
