package com.curuza.data.fournisseur;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.curuza.data.MainDatabase;
import com.curuza.data.client.Client;
import com.curuza.data.client.ClientDao;
import com.curuza.data.client.ClientRepository;
import com.curuza.data.depense.DepenseDao;

import java.util.List;

public class FournisseurRepository {

    private MainDatabase db;



    public FournisseurRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public LiveData<List<Fournisseur>> getFournisseurs() {
        return db.fournisseurDao().getFournisseurs();
    }
    public LiveData<Fournisseur> getFournisseur(String fournisseurId) {
        return db.fournisseurDao().getFournisseur(fournisseurId);
    }


    public void insert(Fournisseur fournisseur) {
        new FournisseurRepository.insertAsyncTask(db.fournisseurDao()).execute(fournisseur);
    }
    private static class insertAsyncTask extends AsyncTask<Fournisseur, Void, Void> {

        private FournisseurDao mAsyncTaskDao;

        insertAsyncTask(FournisseurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Fournisseur... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Fournisseur fournisseur)  {
        new FournisseurRepository.deleteAsyncTask(db.fournisseurDao()).execute(fournisseur);
    }
    private static class deleteAsyncTask extends AsyncTask<Fournisseur, Void, Void> {
        private FournisseurDao mAsyncTaskDao;

        deleteAsyncTask(FournisseurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Fournisseur... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Fournisseur fournisseur)  {
        new FournisseurRepository.updateAsyncTask(db.fournisseurDao()).execute(fournisseur);
    }
    private static class updateAsyncTask extends AsyncTask<Fournisseur, Void, Void> {
        private FournisseurDao mAsyncTaskDao;

        updateAsyncTask(FournisseurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Fournisseur... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }




}
