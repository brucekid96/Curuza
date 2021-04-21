package com.curuza.data.depense;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.curuza.data.MainDatabase;
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditDao;
import com.curuza.data.credit.CreditRepository;

import java.util.List;

public class DepenseRepository {

    private MainDatabase db;



    public DepenseRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public LiveData<List<Depense>> getDepenses() {
        return db.depenseDao().getDepenses();
    }
    public LiveData<Depense> getDepense(String depenseId) {
        return db.depenseDao().getDepense(depenseId);
    }


    public void insert(Depense depense) {
        new DepenseRepository.insertAsyncTask(db.depenseDao()).execute(depense);
    }
    private static class insertAsyncTask extends AsyncTask<Depense, Void, Void> {

        private DepenseDao mAsyncTaskDao;

        insertAsyncTask(DepenseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Depense... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Depense depense)  {
        new DepenseRepository.deleteAsyncTask(db.depenseDao()).execute(depense);
    }
    private static class deleteAsyncTask extends AsyncTask<Depense, Void, Void> {
        private DepenseDao mAsyncTaskDao;

        deleteAsyncTask(DepenseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Depense... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Depense depense)  {
        new DepenseRepository.updateAsyncTask(db.depenseDao()).execute(depense);
    }
    private static class updateAsyncTask extends AsyncTask<Depense, Void, Void> {
        private DepenseDao mAsyncTaskDao;

        updateAsyncTask(DepenseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Depense... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }




}
