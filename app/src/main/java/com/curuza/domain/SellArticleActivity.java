package com.curuza.domain;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.curuza.R;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellArticleActivity extends AppCompatActivity {

    private RecyclerView rcvSellArticle;
    private SellArticleAdapter sellArticleAdapter;
    private ProductViewModel mModel;
    private SellArticleAdapter.OnItemListener OnitemListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_article);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        rcvSellArticle = findViewById(R.id.rcv_sell_article);
        sellArticleAdapter = new SellArticleAdapter(getListProduct(),this,OnitemListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSellArticle.setLayoutManager(linearLayoutManager);

        sellArticleAdapter.setData(getListProduct());
        rcvSellArticle.setAdapter(sellArticleAdapter);
        mModel= ViewModelProviders.of(this).get(ProductViewModel.class);
        mModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                sellArticleAdapter.setData(products);
            }
        });


    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        Date date = new Date();


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


