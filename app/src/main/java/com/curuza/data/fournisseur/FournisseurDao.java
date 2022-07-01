package com.curuza.data.fournisseur;

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
public interface FournisseurDao {
    @Insert
    Completable insert(Fournisseur fournisseur);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bulkInsert(List<Fournisseur> fournisseurs);

    @Delete
    Completable delete(Fournisseur fournisseur);
    @Update
    Completable update(Fournisseur fournisseur);

    @Query("SELECT * from fournisseur order by date desc ")
    Observable<List<Fournisseur>> getFournisseurs();

    @Query("SELECT * from  fournisseur where id = :fournisseurId ")
    Maybe<Fournisseur> getFournisseur(String fournisseurId);
}
