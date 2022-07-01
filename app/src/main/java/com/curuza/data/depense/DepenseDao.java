package com.curuza.data.depense;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface DepenseDao {
    @Insert
    Completable insert(Depense depense);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bulkInsert(List<Depense> depenses);

    @Delete
    Completable delete(Depense depense);
    @Update
    Completable update(Depense depense);

    @Query("SELECT * from depense order by date desc ")
    Observable<List<Depense>> getDepenses();

    @Query("SELECT * from depense where substr(date,1,10) = :date order by date desc")
    Observable<List<Depense>> getDepensesByDate(String date);

    @Query("SELECT * from  credit where id = :depenseId ")
    Maybe<Depense> getDepense(String depenseId);
}
