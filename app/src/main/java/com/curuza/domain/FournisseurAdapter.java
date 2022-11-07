package com.curuza.domain;

import android.content.Context;
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
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FournisseurAdapter extends RecyclerView.Adapter<FournisseurAdapter.ViewHolder> {

    private List<Fournisseur> mFournisseurs;
    private Context mContext;
    private FournisseurRepository mFournisseurRepository;



    public FournisseurAdapter(List<Fournisseur> mFournisseurs,Context mContext) {
        this.mFournisseurs = mFournisseurs;
        this.mContext = mContext;
    }


    public void setData(List<Fournisseur>list) {
        this.mFournisseurs=list;
        notifyDataSetChanged();
    }

    private void showCardDialog(Fournisseur fournisseur) {
        mFournisseurRepository = new FournisseurRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Delete Fournisseur");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            mFournisseurRepository.delete(fournisseur)
                                .subscribeOn(Schedulers.io())
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                }, e -> {
                                });

                            break;
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
       Fournisseur  fournisseur = mFournisseurs.get(position);

        holder.tvPersonName.setText(fournisseur.getPersonName());

        holder.tvDate.setText(DateTimeUtils.getDateString(fournisseur.getDate()));

        holder.container.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, FournisseurDetailActivity.class);
            intent.putExtra(Fournisseur.FOURNISSEUR_EXTRA,fournisseur);
            mContext.startActivity(intent);
        });
        holder.container.setOnLongClickListener(v -> {
            showCardDialog(fournisseur);
            return true;
        });

    }


    @Override
    public int getItemCount() {
            return mFournisseurs.size();
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
            tvPersonPict = itemView.findViewById(R.id.img_user);
            tvPersonName = itemView.findViewById(R.id.user_name);
            tvTelephone  = itemView.findViewById(R.id.tel_number);
            tvDescription = itemView.findViewById(R.id.description_fournisseur);
            tvDate = itemView.findViewById(R.id.date);
            container= itemView.findViewById(R.id.container);

        }
    }
}
