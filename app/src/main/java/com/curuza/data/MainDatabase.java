package com.curuza.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.curuza.data.accounts.AccountDao;
import com.curuza.data.accounts.AccountsManagement;
import com.curuza.data.client.Client;
import com.curuza.data.client.ClientDao;
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditDao;
import com.curuza.data.depense.Depense;
import com.curuza.data.depense.DepenseDao;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurDao;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementDao;
import com.curuza.data.photos.PhotoDao;
import com.curuza.data.photos.PhotoFetchInfo;
import com.curuza.data.s3.S3Transfer;
import com.curuza.data.s3.S3TransferDao;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductDao;
import com.curuza.data.view.ProductMovement;
import com.curuza.data.view.Rapport;
import com.curuza.domain.Converters;

@TypeConverters({Converters.class})
@Database(entities = {Product.class, PhotoFetchInfo.class, S3Transfer.class, Movement.class, Credit.class, Depense.class, Client.class, Fournisseur.class, AccountsManagement.class},
        views = {
                ProductMovement.class, Rapport.class

        }, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    private static final String TAG = MainDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "curuza.db";




    private static volatile MainDatabase INSTANCE;

   public static MainDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MainDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MainDatabase.class, MainDatabase.DATABASE_NAME)

                            .build();
                }
            }
        }


        return INSTANCE;

    }

    public abstract ProductDao productDao();
    public abstract PhotoDao photoDao();
    public abstract S3TransferDao s3TransferDao();
    public abstract MovementDao movementDao();
    public abstract CreditDao creditDao();
    public abstract DepenseDao depenseDao();
    public abstract ClientDao clientDao();
    public abstract FournisseurDao fournisseurDao();
    public abstract AccountDao accountDao();
}
