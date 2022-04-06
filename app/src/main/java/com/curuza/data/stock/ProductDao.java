package com.curuza.data.stock;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.curuza.data.stock.Product;

import java.util.List;
import java.util.UUID;
@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Delete
    int delete(Product product);
    @Update
    int update(Product product);

    @Query("SELECT * from products_table order by p_date desc")
    LiveData<List<Product>> getProducts();

    @Query("SELECT * from products_table where name Like :searchQuery Or description Like :searchQuery ")
    LiveData<List<Product>> searchProducts(String searchQuery);

    @Query("SELECT * from  products_table where id = :ProductId ")
    LiveData<Product> getProduct(String ProductId);


}
