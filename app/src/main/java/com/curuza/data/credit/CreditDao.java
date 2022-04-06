package com.curuza.data.credit;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface CreditDao {

    @Insert
    void insert(Credit credit);

    @Delete
    int delete(Credit credit);
    @Update
    int update(Credit credit);

    @Query("SELECT * from credit order by date desc ")
    LiveData<List<Credit>> getCredits();

    @Query("SELECT * from  credit where id = :creditId ")
    LiveData<Credit> getCredit(String creditId);

}
