package com.curuza.domain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.view.ProductMovement;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ExitProductsFragment extends Fragment implements ProductMovementsAdapter.OnDeleteClickListener {

    private RecyclerView mRecyclerView;
    private ProductMovementsAdapter mAdapter;
    private MovementRepository mMovementRepository;
    private Context mContext;
    private CompositeDisposable mDisposable = new CompositeDisposable();


    private ExitProductsFragment.OnFragmentInteractionListener mListener;

    public ExitProductsFragment() {

    }

    public static AllProductsFragment newInstance(String param1, String param2) {
        AllProductsFragment fragment = new AllProductsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }

        mMovementRepository = new MovementRepository(mContext);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_exit_products, container, false);
        mRecyclerView =view.findViewById(R.id.exit_products_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ProductMovementsAdapter(getActivity(),this::OnDeleteClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mDisposable.add(
            mMovementRepository.getExitProductMovements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadExitProducts));


        return view;


    }

    public void loadExitProducts(List<ProductMovement> productMovements) {
        mAdapter.setData(productMovements);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AllProductsFragment.OnFragmentInteractionListener) {
            mListener = (ExitProductsFragment.OnFragmentInteractionListener) context;
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

    private List<ProductMovement> getListProduct() {
        List<ProductMovement> list = new ArrayList<>();
        Date date = new Date();

        String stringDate = DateFormat.getDateTimeInstance().format(date);


        return list;
    }

    @Override
    public void OnDeleteClickListener(Movement mouvement) {

    }
}
