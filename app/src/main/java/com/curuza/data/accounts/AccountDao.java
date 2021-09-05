package com.curuza.data.accounts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;
@Dao
public interface AccountDao {
    @Insert
    void insert(AccountsManagement account);

    @Delete
    int delete(AccountsManagement account);
    @Update
    int update(AccountsManagement account);

    @Query("SELECT * from accounts_table")
    LiveData<List<AccountsManagement>> getAccounts();

    @Query("SELECT * from accounts_table where name Like:searchQuery Or isSelected Like:searchQuery")
    LiveData<List<AccountsManagement>> searchAccounts(String searchQuery);

    @Query("SELECT * from  accounts_table where id = :AccountId ")
    LiveData<AccountsManagement> getAccount(String AccountId);
}
