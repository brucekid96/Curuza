package com.curuza.data.stock;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface ProductDao {

    @Insert
    Completable insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bulkInsert(List<Product> products);

    @Delete
    Completable delete(Product product);
    @Update
    Completable update(Product product);

    @Query("SELECT * from products_table order by p_date desc")
    Observable<List<Product>> getProducts();

    @Query("SELECT * from products_table where name Like :searchQuery Or description Like :searchQuery ")
    Observable<List<Product>> searchProducts(String searchQuery);

    @Query("SELECT * from  products_table where id = :ProductId ")
    Maybe<Product> getProduct(String ProductId);


}
