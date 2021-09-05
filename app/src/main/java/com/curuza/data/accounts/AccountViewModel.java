package com.curuza.data.accounts;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository mAccountRepository;

    public AccountViewModel (Application application) {
        super(application);
        mAccountRepository = new AccountRepository(application.getApplicationContext());
    }

    public LiveData<List<AccountsManagement>> getAllAccounts() {
        return mAccountRepository.getAccounts();
    }
    public LiveData<List<AccountsManagement>> searchAccounts(String searchQuery) {
        return mAccountRepository.searchAccounts(searchQuery);
    }
    public LiveData<AccountsManagement> getAccount(String accountId) {
        return mAccountRepository.getAccount(accountId);
    }
    public void insert(AccountsManagement account) {
        mAccountRepository.insert(account);
    }
    //  public void deleteAll() {mAccountRepository.deleteAll();}
    public void delete(AccountsManagement account) {mAccountRepository.delete(account);}
}
