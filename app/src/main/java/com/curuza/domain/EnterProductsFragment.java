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

public class EnterProductsFragment extends Fragment implements ProductMovementsAdapter.OnDeleteClickListener {

  private static final String DBG_TAG = EnterProductsFragment.class.getSimpleName();
  private RecyclerView mRecyclerView;
  private ProductMovementsAdapter mAdapter;
  private MovementRepository mMovementRepository;
  private Context mContext;
  private CompositeDisposable mDisposable = new CompositeDisposable();

    private EnterProductsFragment.OnFragmentInteractionListener mListener;

    public EnterProductsFragment() {

    }

    public static EnterProductsFragment newInstance(String param1, String param2) {
        EnterProductsFragment fragment = new EnterProductsFragment();
        Bundle args = new Bundle();

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

      View view = inflater.inflate(R.layout.fragment_enter_products, container, false);
      mRecyclerView =view.findViewById(R.id.enter_products_recyclerview);
      mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      mAdapter = new ProductMovementsAdapter(getContext(),this::OnDeleteClickListener);
      mRecyclerView.setAdapter(mAdapter);
      return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    mDisposable.add(
        mMovementRepository.getEnterProductMovements()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadEnterProducts));
  }

  public void loadEnterProducts(List<ProductMovement> productMovements) {
      Log.d(DBG_TAG, "logged data is : "+productMovements);
    mAdapter.setData(productMovements);
  }

  @Override
  public void onAttach(Context context) {
      super.onAttach(context);
      if (context instanceof EnterProductsFragment.OnFragmentInteractionListener) {
          mListener = (EnterProductsFragment.OnFragmentInteractionListener) context;
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
