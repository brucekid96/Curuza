package com.curuza.domain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class AllProductsFragment extends Fragment implements ProductMovementsAdapter.OnDeleteClickListener{




    private RecyclerView mRecyclerView;
    private ProductMovementsAdapter mProductAdapter;
    private MovementRepository mMovementRepository;
    private List<ProductMovement> productMovementList;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public AllProductsFragment() {

    }

    public static AllProductsFragment newInstance(String param1, String param2) {
        AllProductsFragment fragment = new AllProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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

        View view = inflater.inflate(R.layout.fragment_all_products, container, false);

        mRecyclerView =view.findViewById(R.id.all_products_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductAdapter = new ProductMovementsAdapter(getProductMovementList(),getContext(),this::OnDeleteClickListener);
        mProductAdapter.setData(getProductMovementList());
        mRecyclerView.setAdapter(mProductAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDisposable.add(
            mMovementRepository.getProductMovements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadAllProducts));
    }

    public void loadAllProducts(List<ProductMovement> productMovements) {
        Log.d(AllProductsFragment.class.getSimpleName(), "mModel.getProductMovements()");
        Log.d(AllProductsFragment.class.getSimpleName(), "Product movement list: " + productMovements.toString());
        productMovementList = productMovements;
        mProductAdapter.setData(productMovements);
    }
    public void updateSearchResults(String searchQuery) {
        if(productMovementList != null) {
            List<ProductMovement> searchResults = new ArrayList<>();

            for (ProductMovement productMovement : productMovementList) {

                if (productMovement.getProduct().getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        productMovement.getProduct().getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                    searchResults.add(productMovement);
                }
            }

            mProductAdapter.setData(searchResults);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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

    private List<ProductMovement> getProductMovementList() {
        List<ProductMovement> list = new ArrayList<>();
        Date date = new Date();

        String stringDate = DateFormat.getDateTimeInstance().format(date);



       return list;
    }

    @Override
    public void OnDeleteClickListener(Movement movement) {

    }
}
