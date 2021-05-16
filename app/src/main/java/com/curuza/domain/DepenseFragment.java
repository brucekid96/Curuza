package com.curuza.domain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.depense.Depense;
import com.curuza.data.depense.DepenseViewModel;
import com.curuza.data.movements.MovementViewModel;
import com.curuza.data.view.ProductMovement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepenseFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private DepenseAdapter mAdapter;

    DepenseViewModel mModel;
    private DepenseFragment.OnFragmentInteractionListener mListener;

    public DepenseFragment() {

    }

    public static DepenseFragment newInstance(String param1, String param2) {
        DepenseFragment fragment = new DepenseFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_enter_products, container, false);
        mRecyclerView =view.findViewById(R.id.enter_products_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DepenseAdapter(getListDepenses(),getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mModel= ViewModelProviders.of(this).get(DepenseViewModel.class);
        mModel.getDepenses().observe(this, depenses ->  {

            mAdapter.setData(depenses);

        });



        return view;


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DepenseFragment.OnFragmentInteractionListener) {
            mListener = (DepenseFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private List<Depense> getListDepenses() {
        List<Depense> list = new ArrayList<>();
        Date date = new Date();

        String stringDate = DateFormat.getDateTimeInstance().format(date);


        return list;
    }
}
