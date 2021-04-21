package com.curuza.data.stock;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.curuza.data.MainDatabase;

import java.util.List;

public  class ProductRepository {

   private MainDatabase db;


    public  ProductRepository(Context context) {
        db = MainDatabase.getDatabase(context);

    }


    public LiveData<List<Product>> getProducts() {
        return db.productDao().getProducts();
    }
    public LiveData<List<Product>> searchProducts(String searchQuery) {
        return db.productDao().searchProducts(searchQuery);
    }
    public LiveData<Product> getProduct(String productId) {
        return db.productDao().getProduct(productId);
    }


    public void insert(Product product) {
        new insertAsyncTask(db.productDao()).execute(product);
    }
    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Product product)  {
        new deleteAsyncTask(db.productDao()).execute(product);
    }
    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao mAsyncTaskDao;

        deleteAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Product product)  {
        new updateAsyncTask(db.productDao()).execute(product);
    }
    private static class updateAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao mAsyncTaskDao;

        updateAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}
