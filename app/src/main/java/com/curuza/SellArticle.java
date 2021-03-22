package com.curuza;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class SellArticle extends AppCompatActivity {

    private RecyclerView rcvSellArticle;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_article);

        Toolbar toolbar = findViewById(R.id.toolbar_create_event);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        rcvSellArticle = findViewById(R.id.rcv_sell_article);
        productsAdapter = new ProductsAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSellArticle.setLayoutManager(linearLayoutManager);

        productsAdapter.setData(getListProduct(),getApplicationContext());
        rcvSellArticle.setAdapter(productsAdapter);


    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        list.add(new Product(R.drawable.bestii,"best friendoo","ikaranga ziryoshe cannee",500,1000,1500));
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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


