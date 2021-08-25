package com.curuza.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.AccountsManagement;
import com.curuza.data.stock.Product;

import java.util.List;

public class AccountManagementAdapter extends RecyclerView.Adapter<AccountManagementAdapter.AccountViewHolder>{

    private List<AccountsManagement> mListAccounts;
    private Context mContext;
    private AccountManagementAdapter.OnItemListener mOnitemListener;

    public interface OnItemListener{
        void onItemClick(int position);
    }

    public AccountManagementAdapter(List<AccountsManagement> mListAccounts, Context mContext, AccountManagementAdapter.OnItemListener OnitemListener) {
        this.mListAccounts = mListAccounts;
        this.mContext = mContext;
        this.mOnitemListener = OnitemListener;

    }


    public void setData(List<AccountsManagement>list) {
        this.mListAccounts=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountManagementAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_management_item,parent,false);
        return new AccountManagementAdapter.AccountViewHolder(view,mOnitemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountManagementAdapter.AccountViewHolder holder, int position) {
        AccountsManagement accounts = mListAccounts.get(position);
        if(accounts ==null) {
            return;
        }

        holder.tvName.setText(accounts.getName());
        holder.tvStatus.setText(String.valueOf(accounts.getmStatus()));

        if (accounts.getmProfileImageUri()!=null){

            Glide.with(mContext)
                    .load(accounts.getmProfileImageUri())
                    .circleCrop()
                    .into(holder.imgUser);
        }


    }


    @Override
    public int getItemCount() {
        if (mListAccounts != null) {
            return mListAccounts.size();
        }
        return 0;
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView tvName;
        private RadioButton tvStatus;
        public ConstraintLayout container;
        AccountManagementAdapter.OnItemListener mOnItemListener;


        public AccountViewHolder(@NonNull View itemView, AccountManagementAdapter.OnItemListener onitemListener) {
            super(itemView);

            imgUser = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.user_name);
            tvStatus = itemView.findViewById(R.id.user_status);
            container= itemView.findViewById(R.id.container);
            mOnItemListener =  onitemListener;
        }
    }
}
