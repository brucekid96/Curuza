package com.curuza.data.movements;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.curuza.data.MainDatabase;
import com.curuza.data.remote.AmplifyAPI;
import com.curuza.data.view.ProductMovement;
import com.curuza.data.view.Rapport;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class MovementRepository {

    private LiveData<List<ProductMovement>> mMovements;
    private LiveData<ProductMovement>mMovement;
    private MainDatabase db;




    public MovementRepository(Context context) {
         db = MainDatabase.getDatabase(context);

    }

    public Observable<List<ProductMovement>> getProductMovements() {
        return db.movementDao().getProductMovements();
    }
   public Observable<List<Rapport>>getRapportList() {
        return db.movementDao().getRapportList();
   }
    public Observable<List<ProductMovement>> getEnterProductMovements() {
        return db.movementDao().getEnterProductMovements();
    }
    public Observable<List<ProductMovement>> getEnterProductMovementsByDate(String date) {
        return db.movementDao().getEnterProductMovementsByDate(date);
    }
    public Observable<List<ProductMovement>> getExitProductMovements() {
        return db.movementDao().getExitProductMovements();
    }

    public Observable<List<ProductMovement>> getExitProductMovementsByDate(String date) {
        return db.movementDao().getExitProductMovementsByDate(date);
    }

    public ProductMovement getProductMovement(String movementId) {
        return db.movementDao().getProductMovement(movementId);
    }



    public Completable insert(Movement movement) {
        return db.movementDao().insert(movement)
            .andThen(AmplifyAPI.addMovement(movement));
    }

    public Completable delete(Movement movement)  {
      return db.movementDao().delete(movement);
    }

  public Completable delete(String movementId)  {
    return db.movementDao().delete(movementId);
  }




    public Completable update(Movement movement)  {
        return db.movementDao().update(movement);
    }

  public Completable syncMovements() {
    return AmplifyAPI.getMovements()
        .flatMapCompletable(db.movementDao()::bulkInsert);
  }

}
