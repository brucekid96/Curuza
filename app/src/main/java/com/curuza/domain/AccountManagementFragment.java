package com.curuza.domain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.domain.common.BottomSheetFragment;

public class AccountManagementFragment extends BottomSheetFragment {
    private  static  final String DBG_TAG = AccountManagementFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private AccountManagementAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.accounts_management_bottom_sheet, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AccountManagementAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);




        return root;

    }
}
