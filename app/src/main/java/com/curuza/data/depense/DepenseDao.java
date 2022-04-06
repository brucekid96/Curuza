package com.curuza.data.depense;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface DepenseDao {
    @Insert
    void insert(Depense depense);

    @Delete
    int delete(Depense depense);
    @Update
    int update(Depense depense);

    @Query("SELECT * from depense order by date desc ")
    LiveData<List<Depense>> getDepenses();

    @Query("SELECT * from  credit where id = :depenseId ")
    LiveData<Depense> getDepense(String depenseId);
}
