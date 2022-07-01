package com.curuza.data.fournisseur;

import android.content.Context;

import com.curuza.data.MainDatabase;
import com.curuza.data.remote.AmplifyAPI;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class FournisseurRepository {
    private MainDatabase db;

    public FournisseurRepository(Context context) {
        db = MainDatabase.getDatabase(context);
    }

    public Observable<List<Fournisseur>> getFournisseurs() {
        return db.fournisseurDao().getFournisseurs();
    }
    public Maybe<Fournisseur> getFournisseur(String fournisseurId) {
        return db.fournisseurDao().getFournisseur(fournisseurId);
    }

    public Completable insert(Fournisseur fournisseur) {
       return db.fournisseurDao().insert(fournisseur)
           .andThen(AmplifyAPI.addFournisseur(fournisseur));
    }

    public Completable delete(Fournisseur fournisseur)  {
       return db.fournisseurDao().delete(fournisseur);
    }

    public Completable update(Fournisseur fournisseur)  {
        return db.fournisseurDao().update(fournisseur);
    }

    public Completable syncFournisseurs() {
        return AmplifyAPI.getFournisseurs()
            .flatMapCompletable(db.fournisseurDao()::bulkInsert);
    }
}
