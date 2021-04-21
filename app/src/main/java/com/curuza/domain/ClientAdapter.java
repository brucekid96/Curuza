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
import com.curuza.data.stock.ProductRepository;


import java.util.List;

public class ClientAdapter  extends RecyclerView.Adapter<ClientAdapter.ViewHolder>{
    private List<Client> mClients;
    private Client client;
    private Context mContext;
    private OnDeleteClickListener onDeleteClickListener;
    private ClientRepository mClientRepository;

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Client client);
    }


    public ClientAdapter(List<Client> listClient, Context mContext, OnDeleteClickListener listener) {
        this.mClients = listClient;
        this.mContext = mContext;
        this.onDeleteClickListener = listener;

    }


    public void setData(List<Client>list) {
        this.mClients=list;
        notifyDataSetChanged();
    }

    private void showCardDialog() {
        mClientRepository = new ClientRepository(mContext.getApplicationContext());
        AlertDialog.Builder cardDialog = new AlertDialog.Builder(mContext);
        cardDialog.setTitle("Select Action");
        String[] cardDialogItems = {
                "delete",
        };

        cardDialog.setItems(cardDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mClientRepository.delete(client);

                                break;
                        }
                    }
                });
        cardDialog.show();
    }


    @NonNull
    @Override
    public ClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item,parent,false);
        return new ClientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ViewHolder holder, int position) {
         client = mClients.get(position);
        if(client ==null) {
            return;
        }

        holder.tvPersonName.setText(client.getPersonName());

        holder.tvDate.setText(DateTimeUtils.getDateString(client.getDate()));

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, ClientDetailActivity.class);
                intent.putExtra(Client.CLIENT_EXTRA,client);
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
        if (mClients != null) {
            return mClients.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvPersonPict;
        private TextView tvPersonName;
        private TextView tvTelephone;
        private TextView tvDescription;
        private TextView tvDate;
        public ConstraintLayout container;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonPict = itemView.findViewById(R.id.img_person);
            tvPersonName = itemView.findViewById(R.id.client_people_name);
            tvTelephone  = itemView.findViewById(R.id.tel_number);
            tvDescription = itemView.findViewById(R.id.description_client);
            tvDate = itemView.findViewById(R.id.date);
            container= itemView.findViewById(R.id.container);

        }
    }
}
