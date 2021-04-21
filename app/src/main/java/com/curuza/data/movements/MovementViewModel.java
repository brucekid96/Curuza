package com.curuza.data.movements;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.curuza.data.view.ProductMovement;

import java.util.List;

public class MovementViewModel extends AndroidViewModel {

    private MovementRepository mTransactionRepository;

    public MovementViewModel(Application application) {
        super(application);
        mTransactionRepository = new MovementRepository(application.getApplicationContext());



    }

    public LiveData<List<ProductMovement>> getAllProductMovements() {
        return mTransactionRepository.getProductMovements();
    }

    public LiveData<List<ProductMovement>> getEnterProductMovements() {
        return mTransactionRepository.getEnterProductMovements();
    }

    public LiveData<List<ProductMovement>> getExitProductMovements() {
        return mTransactionRepository.getExitProductMovements();
    }

    public ProductMovement getProductMovement(String productId) {
        return mTransactionRepository.getProductMovement(productId);
    }

    public void insert(Movement movement) {
        mTransactionRepository.insert(movement);
    }
    //  public void deleteAll() {mEventRepository.deleteAll();}
    public void delete(Movement movement) {mTransactionRepository.delete(movement);}
}
