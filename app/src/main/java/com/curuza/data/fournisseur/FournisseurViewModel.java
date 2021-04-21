package com.curuza.data.fournisseur;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;

public class FournisseurViewModel extends AndroidViewModel {

    private FournisseurRepository mFournisseurRepository;

    public FournisseurViewModel (Application application) {
        super(application);
        mFournisseurRepository = new FournisseurRepository(application);



    }

    public LiveData<List<Fournisseur>> getFournisseurs() { return mFournisseurRepository.getFournisseurs(); }
    public LiveData<Fournisseur> getFournisseur(String fournisseurId) { return mFournisseurRepository.getFournisseur(fournisseurId); }
    public void insert(Fournisseur fournisseur) { mFournisseurRepository.insert(fournisseur); }
    public void delete(Fournisseur fournisseur) {mFournisseurRepository.delete(fournisseur);}
}
