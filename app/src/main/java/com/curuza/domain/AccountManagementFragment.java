package com.curuza.domain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AccountManagementFragment extends BottomSheetDialogFragment {
    private  static  final String DBG_TAG = AccountManagementFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private AccountManagementAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.accounts_management_bottom_sheet, container, false);

        return root;

    }

}
