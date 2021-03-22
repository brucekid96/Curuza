 package com.curuza;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rcvProduct;
    private FloatingActionsMenu fab;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rcvProduct = findViewById(R.id.rcv_product);
        fab1 = findViewById(R.id.sell_article_fb);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Products.this,SellArticle.class));
            }
        });

        fab2 = findViewById(R.id.add_article_fb);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Products.this,AddArticle.class));
            }
        });



        productsAdapter = new ProductsAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productsAdapter.setData(getListProduct(),getApplicationContext());
        rcvProduct.setAdapter(productsAdapter);
        fab= findViewById(R.id.float_menu);
        rcvProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                   // fab.();
                } else {
                  //  fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(Products.this,MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(Products.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(Products.this,Documents.class));


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
