package com.curuza.data.depense;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;

public class DepenseViewModel extends AndroidViewModel {

    private DepenseRepository mDepenseRepository;


    public DepenseViewModel (Application application) {
        super(application);
        mDepenseRepository = new DepenseRepository(application);



    }

    public LiveData<List<Depense>> getDepenses() { return mDepenseRepository.getDepenses(); }
    public LiveData<Depense> getCredit(String depenseId) { return mDepenseRepository.getDepense(depenseId); }
    public void insert(Depense depense) { mDepenseRepository.insert(depense); }
    //  public void deleteAll() {mEventRepository.deleteAll();}
    public void delete(Depense depense) {mDepenseRepository.delete(depense);}
}
