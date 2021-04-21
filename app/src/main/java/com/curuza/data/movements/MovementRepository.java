package com.curuza.data.movements;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.curuza.data.MainDatabase;
import com.curuza.data.view.ProductMovement;


import java.util.List;

public class MovementRepository {

    private LiveData<List<ProductMovement>> mMovements;
    private LiveData<ProductMovement>mMovement;
    private MainDatabase db;




    public MovementRepository(Context context) {
         db = MainDatabase.getDatabase(context);

    }

    public LiveData<List<ProductMovement>> getProductMovements() {
        return db.movementDao().getProductMovements();
    }

    public LiveData<List<ProductMovement>> getEnterProductMovements() {
        return db.movementDao().getEnterProductMovements();
    }
    public LiveData<List<ProductMovement>> getExitProductMovements() {
        return db.movementDao().getExitProductMovements();
    }

    public ProductMovement getProductMovement(String movementId) {
        return db.movementDao().getProductMovement(movementId);
    }



    public void insert(Movement movement) {
        new insertAsyncTask(db.movementDao()).execute(movement);
    }
    private static class insertAsyncTask extends AsyncTask<Movement, Void, Void> {

        private MovementDao mAsyncTaskDao;

        insertAsyncTask(MovementDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movement... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Movement movement)  {
        new insertAsyncTask(db.movementDao()).execute(movement);
    }
    private static class deleteAsyncTask extends AsyncTask<Movement, Void, Void> {
        private MovementDao mAsyncTaskDao;

        deleteAsyncTask(MovementDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movement... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Movement movement)  {
        new insertAsyncTask(db.movementDao()).execute(movement);
    }
    private static class updateAsyncTask extends AsyncTask<Movement, Void, Void> {
        private MovementDao mAsyncTaskDao;

        updateAsyncTask(MovementDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movement... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}
