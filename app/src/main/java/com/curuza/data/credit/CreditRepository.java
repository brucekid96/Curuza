package com.curuza.data.credit;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.curuza.data.MainDatabase;

import java.util.List;

public class CreditRepository {

    private MainDatabase db;



    public CreditRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public LiveData<List<Credit>> getCredits() {
        return db.creditDao().getCredits();
    }
    public LiveData<Credit> getCredit(String creditId) {
        return db.creditDao().getCredit(creditId);
    }


    public void insert(Credit credit) {
        new insertAsyncTask(db.creditDao()).execute(credit);
    }
    private static class insertAsyncTask extends AsyncTask<Credit, Void, Void> {

        private CreditDao mAsyncTaskDao;

        insertAsyncTask(CreditDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Credit... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Credit credit)  {
        new deleteAsyncTask(db.creditDao()).execute(credit);
    }
    private static class deleteAsyncTask extends AsyncTask<Credit, Void, Void> {
        private CreditDao mAsyncTaskDao;

        deleteAsyncTask(CreditDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Credit... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Credit credit)  {
        new updateAsyncTask(db.creditDao()).execute(credit);
    }
    private static class updateAsyncTask extends AsyncTask<Credit, Void, Void> {
        private CreditDao mAsyncTaskDao;

        updateAsyncTask(CreditDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Credit... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }




    ;
}
