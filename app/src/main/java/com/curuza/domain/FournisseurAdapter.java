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
import com.curuza.data.client.Client;
import com.curuza.data.client.ClientRepository;
import com.curuza.data.credit.Credit;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;

import java.util.List;

public class FournisseurAdapter extends RecyclerView.Adapter<FournisseurAdapter.ViewHolder> {

    private List<Fournisseur> mFournisseurs;
    private Fournisseur fournisseur;
    private Context mContext;
    private OnDeleteClickListener onDeleteClickListener;
    private FournisseurRepository mFournisseurRepository;

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Fournisseur fournisseur);
    }


    public FournisseurAdapter(List<Fournisseur> listFournisseur, Context mContext,  OnDeleteClickListener listener) {
        this.mFournisseurs = listFournisseur;
        this.mContext = mContext;
        this.onDeleteClickListener = listener;

    }


    public void setData(List<Fournisseur>list) {
        this.mFournisseurs=list;
        notifyDataSetChanged();
    }

    private void showCardDialog() {
        mFournisseurRepository = new FournisseurRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Fournisseur");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mFournisseurRepository.delete(fournisseur);

                                break;
                        }
                    }
                });
        cardDialog.show();
    }


    @NonNull
    @Override
    public FournisseurAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fournisseur_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FournisseurAdapter.ViewHolder holder, int position) {
         fournisseur = mFournisseurs.get(position);
        if(fournisseur ==null) {
            return;
        }

        holder.tvPersonName.setText(fournisseur.getPersonName());

        holder.tvDate.setText(DateTimeUtils.getDateString(fournisseur.getDate()));

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, FournisseurDetailActivity.class);
                intent.putExtra(Fournisseur.FOURNISSEUR_EXTRA,fournisseur);
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
        if (mFournisseurs != null) {
            return mFournisseurs.size();
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
            tvPersonName = itemView.findViewById(R.id.fournisseur_people_name);
            tvTelephone  = itemView.findViewById(R.id.tel_number);
            tvDescription = itemView.findViewById(R.id.description_fournisseur);
            tvDate = itemView.findViewById(R.id.date);
            container= itemView.findViewById(R.id.container);

        }
    }
}
