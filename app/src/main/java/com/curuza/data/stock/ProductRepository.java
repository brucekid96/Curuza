package com.curuza.data.stock;

import android.content.Context;

import com.curuza.data.MainDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public  class ProductRepository {

   private MainDatabase db;


    public  ProductRepository(Context context) {
        db = MainDatabase.getDatabase(context);
    }


    public Observable<List<Product>> getProducts() {
        return db.productDao().getProducts();
    }
    public Observable<List<Product>> searchProducts(String searchQuery) {
        return db.productDao().searchProducts(searchQuery);
    }
    public Maybe<Product> getProduct(String productId) {
        return db.productDao().getProduct(productId);
    }

    public Completable insert(Product product) {
        return db.productDao().insert(product);
    }

    public Completable delete(Product product)  {
        return db.productDao().delete(product);
    }

    public Completable update(Product product)  {
       return db.productDao().update(product);
    }


}
