package com.curuza.data.accounts;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.curuza.data.MainDatabase;

import java.util.List;

public class AccountRepository {

    private MainDatabase db;

    public  AccountRepository(Context context) {
        db = MainDatabase.getDatabase(context);
    }

    public LiveData<List<AccountsManagement>> getAccounts() {
        return db.accountDao().getAccounts();
    }
    public LiveData<List<AccountsManagement>> searchAccounts(String searchQuery) {
        return db.accountDao().searchAccounts(searchQuery);
    }
    public LiveData<AccountsManagement> getAccount(String accountId) {
        return db.accountDao().getAccount(accountId);
    }

    public void insert(AccountsManagement account) {
        new AccountRepository.insertAsyncTask(db.accountDao()).execute(account);
    }
    private static class insertAsyncTask extends AsyncTask<AccountsManagement, Void, Void> {

        private AccountDao mAsyncTaskDao;

        insertAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AccountsManagement... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(AccountsManagement account)  {
        new AccountRepository.deleteAsyncTask(db.accountDao()).execute(account);
    }
    private static class deleteAsyncTask extends AsyncTask<AccountsManagement, Void, Void> {
        private AccountDao mAsyncTaskDao;

        deleteAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AccountsManagement... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(AccountsManagement account)  {
        new AccountRepository.updateAsyncTask(db.accountDao()).execute(account);
    }
    private static class updateAsyncTask extends AsyncTask<AccountsManagement, Void, Void> {
        private AccountDao mAsyncTaskDao;

        updateAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AccountsManagement... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
