package com.curuza.data.client;

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
public interface ClientDao {

    @Insert
    Completable insert(Client client);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bulkInsert(List<Client> clients);

    @Delete
    Completable delete(Client client);
    @Update
    Completable update(Client client);

    @Query("SELECT * from client order by date desc ")
    Observable<List<Client>> getClients();

    @Query("SELECT * from  client where id = :clientId ")
    Maybe<Client> getClient(String clientId);
}
