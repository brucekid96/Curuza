package com.curuza.data.client;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    void insert(Client client);

    @Delete
    int delete(Client client);
    @Update
    int update(Client client);

    @Query("SELECT * from client order by date desc ")
    LiveData<List<Client>> getClients();

    @Query("SELECT * from  client where id = :clientId ")
    LiveData<Client> getClient(String clientId);
}
