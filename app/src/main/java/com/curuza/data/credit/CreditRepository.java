package com.curuza.data.credit;

import android.content.Context;

import com.curuza.data.MainDatabase;
import com.curuza.data.remote.AmplifyAPI;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class CreditRepository {

    private MainDatabase db;

    public CreditRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public Observable<List<Credit>> getCredits() {
        return db.creditDao().getCredits();
    }
    public Observable<List<Credit>> getCreditsByDate(String date) {
        return db.creditDao().getCreditsByDate(date);
    }
    public Maybe<Credit> getCredit(String creditId) {
        return db.creditDao().getCredit(creditId);
    }


    public Completable insert(Credit credit) {
       return db.creditDao().insert(credit)
           .andThen(AmplifyAPI.addCredit(credit));
    }

    public Completable delete(Credit credit)  {
        return db.creditDao().delete(credit);
    }

    public Completable update(Credit credit)  {
        return db.creditDao().update(credit);
    }

    public Completable syncCredits() {
        return AmplifyAPI.getCredits()
            .flatMapCompletable(db.creditDao()::bulkInsert);
    }



    ;
}
