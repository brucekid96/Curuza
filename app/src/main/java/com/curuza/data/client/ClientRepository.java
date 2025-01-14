package com.curuza.data.client;

import android.content.Context;

import com.curuza.data.MainDatabase;
import com.curuza.data.remote.AmplifyAPI;
import com.curuza.data.remote.AppSyncApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class ClientRepository {

    private MainDatabase db;



    public ClientRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public Observable<List<Client>> getClients() {
        return db.clientDao().getClients();
    }
    public Maybe<Client> getClient(String clientId) {
        return db.clientDao().getClient(clientId);
    }


    public Completable insert(Client client) {
        return db.clientDao().insert(client)
            .andThen(AppSyncApi.addClient(client));
    }

    public Completable delete(Client client)  {
        return db.clientDao().delete(client)
            .andThen(AmplifyAPI.removeClient(client));
    }

    public Completable update(Client client)  {
        return db.clientDao().update(client)
            .andThen(AppSyncApi.updateClient(client));
    }

    public Completable syncClients() {
        return AmplifyAPI.getClients()
            .flatMapCompletable(db.clientDao()::bulkInsert);
    }

}
