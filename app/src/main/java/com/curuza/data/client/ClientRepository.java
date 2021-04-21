package com.curuza.data.client;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.curuza.data.MainDatabase;


import java.util.List;

public class ClientRepository {

    private MainDatabase db;



    public ClientRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public LiveData<List<Client>> getClients() {
        return db.clientDao().getClients();
    }
    public LiveData<Client> getClient(String clientId) {
        return db.clientDao().getClient(clientId);
    }


    public void insert(Client client) {
        new ClientRepository.insertAsyncTask(db.clientDao()).execute(client);
    }
    private static class insertAsyncTask extends AsyncTask<Client, Void, Void> {

        private ClientDao mAsyncTaskDao;

        insertAsyncTask(ClientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Client... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Client client)  {
        new ClientRepository.deleteAsyncTask(db.clientDao()).execute(client);
    }
    private static class deleteAsyncTask extends AsyncTask<Client, Void, Void> {
        private ClientDao mAsyncTaskDao;

        deleteAsyncTask(ClientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Client... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Client client)  {
        new ClientRepository.updateAsyncTask(db.clientDao()).execute(client);
    }
    private static class updateAsyncTask extends AsyncTask<Client, Void, Void> {
        private ClientDao mAsyncTaskDao;

        updateAsyncTask(ClientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Client... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }




    ;
}
