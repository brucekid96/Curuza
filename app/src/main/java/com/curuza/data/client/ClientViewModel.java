package com.curuza.data.client;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class ClientViewModel extends AndroidViewModel {

    private ClientRepository mClientRepository;

    public ClientViewModel (Application application) {
        super(application);
        mClientRepository = new ClientRepository(application);

    }

    public LiveData<List<Client>> getClients() { return mClientRepository.getClients(); }
    public LiveData<Client> getClient(String clientId) { return mClientRepository.getClient(clientId); }
    public void insert(Client client) { mClientRepository.insert(client); }
    public void delete(Client client) {mClientRepository.delete(client);}
}
