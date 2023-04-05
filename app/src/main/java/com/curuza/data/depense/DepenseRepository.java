package com.curuza.data.depense;

import android.content.Context;

import com.curuza.data.MainDatabase;
import com.curuza.data.remote.AmplifyAPI;
import com.curuza.data.remote.AppSyncApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class DepenseRepository {

    private MainDatabase db;



    public DepenseRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public Observable<List<Depense>> getDepenses() {
        return db.depenseDao().getDepenses();
    }
    public Observable<List<Depense>> getDepensesByDate(String date) {
        return db.depenseDao().getDepensesByDate(date);
    }
    public Maybe<Depense> getDepense(String depenseId) {
        return db.depenseDao().getDepense(depenseId);
    }


    public Completable insert(Depense depense) {
        return db.depenseDao().insert(depense)
            .andThen(AppSyncApi.addDepense(depense));
    }

    public Completable delete(Depense depense)  {
        return db.depenseDao().delete(depense)
            .andThen(AmplifyAPI.removeDepense(depense));
    }

    public Completable update(Depense depense)  {
       return db.depenseDao().update(depense)
           .andThen(AppSyncApi.updateDepense(depense));
    }

    public Completable syncDepenses() {
        return AmplifyAPI.getDepenses()
            .flatMapCompletable(db.depenseDao()::bulkInsert);
    }

}
