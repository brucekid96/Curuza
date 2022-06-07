package com.curuza.data.credit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface CreditDao {

    @Insert
    Completable insert(Credit credit);

    @Delete
    Completable delete(Credit credit);
    @Update
    Completable update(Credit credit);

    @Query("SELECT * from credit order by date desc ")
    Observable<List<Credit>> getCredits();

    @Query("SELECT * from credit where substr(date,1,10) = :date order by date desc")
    Observable<List<Credit>> getCreditsByDate(String date);

    @Query("SELECT * from  credit where id = :creditId ")
    Maybe<Credit> getCredit(String creditId);

}
