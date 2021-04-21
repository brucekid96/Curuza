package com.curuza.data.fournisseur;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface FournisseurDao {
    @Insert
    void insert(Fournisseur fournisseur);

    @Delete
    int delete(Fournisseur fournisseur);
    @Update
    int update(Fournisseur fournisseur);

    @Query("SELECT * from fournisseur")
    LiveData<List<Fournisseur>> getFournisseurs();

    @Query("SELECT * from  fournisseur where id = :fournisseurId ")
    LiveData<Fournisseur> getFournisseur(String fournisseurId);
}
