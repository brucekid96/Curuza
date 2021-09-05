package com.curuza.data.stock;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public  class ProductViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;

    public ProductViewModel (Application application) {
        super(application);
        mProductRepository = new ProductRepository(application.getApplicationContext());
    }

    public LiveData<List<Product>> getAllProducts() {
        return mProductRepository.getProducts();
    }
    public LiveData<List<Product>> searchProducts(String searchQuery) {
        return mProductRepository.searchProducts(searchQuery);
    }
    public LiveData<Product> getProduct(String productId) {
        return mProductRepository.getProduct(productId);
    }
    public void insert(Product product) {
        mProductRepository.insert(product);
    }
    //  public void deleteAll() {mEventRepository.deleteAll();}
    public void delete(Product product) {mProductRepository.delete(product);}
}
