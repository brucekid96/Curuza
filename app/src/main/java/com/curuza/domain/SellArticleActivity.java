package com.curuza.domain;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.curuza.domain.common.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SellArticleActivity extends BaseActivity {

    private RecyclerView rcvSellArticle;
    private SellArticleAdapter sellArticleAdapter;
    private List<Product> productList;
    ProductRepository mProductRepository;
    MovementRepository mMovementRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private SellArticleAdapter.OnItemListener OnitemListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_article);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProductRepository = new ProductRepository(this);

        rcvSellArticle = findViewById(R.id.rcv_sell_article);
        sellArticleAdapter = new SellArticleAdapter(getListProduct(),this,OnitemListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSellArticle.setLayoutManager(linearLayoutManager);

        sellArticleAdapter.setData(getListProduct());
        rcvSellArticle.setAdapter(sellArticleAdapter);
        mDisposable.add(
            mProductRepository.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadProducts));

    }
    public void loadProducts(List<Product> products) {
        productList = products;
        sellArticleAdapter.setData(products);
    }

    public void updateSearchResults(String searchQuery) {
        if(productList != null) {
            List<Product> searchResults = new ArrayList<>();

            for (Product product : productList) {

                if (product.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        product.getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                    searchResults.add(product);
                }
            }

            sellArticleAdapter.setData(searchResults);
        }
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        Date date = new Date();


        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.requestFocus();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                updateSearchResults(query);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_item_search) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}


