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
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;


import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {


    private List<Credit> mListCredit;
    private Context mContext;
    private OnDeleteClickListener onDeleteClickListener;
    private CreditRepository mCreditRepository;
    private Credit credit;


    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Credit credit);
    }
    public CreditAdapter(List<Credit> listCredit, Context mContext, OnDeleteClickListener listener) {
        this.mListCredit = listCredit;
        this.mContext = mContext;
        this.onDeleteClickListener = listener;

    }


    public void setData(List<Credit>list) {
        this.mListCredit=list;
        notifyDataSetChanged();
    }

    private void showCardDialog() {
        mCreditRepository = new CreditRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Credit");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mCreditRepository.delete(credit);

                                break;
                        }
                    }
                });
        cardDialog.show();
    }


    @NonNull
    @Override
    public CreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.ViewHolder holder, int position) {
         credit = mListCredit.get(position);
        if(credit ==null) {
            return;
        }

        holder.tvPersonName.setText(credit.getPersonName());
        holder.tvAmount.setText(String.valueOf(credit.getAmount()));

        holder.tvDate.setText(DateTimeUtils.getDateString(credit.getDate()));

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, CreditDetailActivity.class);
                intent.putExtra(Credit.CREDIT_EXTRA,credit);
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
        if (mListCredit != null) {
            return mListCredit.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvPersonPict;
        private TextView tvPersonName;
        private TextView tvTelephone;
        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvAmount;
        public ConstraintLayout container;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonPict = itemView.findViewById(R.id.img_person);
            tvPersonName = itemView.findViewById(R.id.credit_people_name);
            tvTelephone  = itemView.findViewById(R.id.tel_number);
            tvDescription = itemView.findViewById(R.id.description_credit);
            tvDate = itemView.findViewById(R.id.date);
            tvAmount = itemView.findViewById(R.id.amount);
            container= itemView.findViewById(R.id.container);

        }
    }
}


