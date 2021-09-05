package com.curuza.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.curuza.R;
import com.curuza.data.accounts.AccountsManagement;
import com.curuza.utils.ResourceUtils;

import java.util.Arrays;
import java.util.List;

public class AccountManagementAdapter extends RecyclerView.Adapter<AccountManagementAdapter.AccountViewHolder> {

    private List<AccountsManagement> mAccounts;
    private Context mContext;
    private int mSelectedAccountPosition;



    public AccountManagementAdapter(Context context) {
        this.mAccounts = getSampleAccounts(context);
        this.mContext = context;
    }


    public void setData(List<AccountsManagement> list) {
        this.mAccounts = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountManagementAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_management_item, parent, false);
        return new AccountManagementAdapter.AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountManagementAdapter.AccountViewHolder holder, int position) {

        holder.bind(mAccounts.get(position));
    }


    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView tvName;
        private ImageView tvStatus;
        public ConstraintLayout container;


        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.user_name);
            tvStatus = itemView.findViewById(R.id.user_status);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(AccountsManagement account) {
            tvName.setText(account.getName());
            if(account.isSelected()) {
                tvStatus.setImageResource(R.drawable.ic_baseline_check_24);
                tvStatus.setVisibility(View.VISIBLE);
            }
            else {
                tvStatus.setVisibility(View.INVISIBLE);
            }

            if (account.getProfileImageUri() != null) {

                Glide.with(mContext)
                        .load(account.getProfileImageUri())
                        .circleCrop()
                        .into(imgUser);
            }
        }
    }

    public  List<AccountsManagement> getSampleAccounts(Context context) {

        AccountsManagement[] accountsArray = {
                new AccountsManagement("1", ResourceUtils.getResourceUri(context,R.drawable.bestii),"kaze brice",true),
                new AccountsManagement("2", ResourceUtils.getResourceUri(context,R.drawable.bestii),"Yohani MIkayeri",false)
        };
        return Arrays.asList(accountsArray);
    }
}


