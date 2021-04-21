package com.curuza.data.credit;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CreditViewModel extends AndroidViewModel {

    private CreditRepository mCreditRepository;

    public CreditViewModel (Application application) {
        super(application);
        mCreditRepository = new CreditRepository(application);



    }

    public LiveData<List<Credit>> getCredits() { return mCreditRepository.getCredits(); }
    public LiveData<Credit> getCredit(String creditId) { return mCreditRepository.getCredit(creditId); }
    public void insert(Credit credit) { mCreditRepository.insert(credit); }
    public void delete(Credit credit) {mCreditRepository.delete(credit);}
}
