 package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.curuza.R;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.curuza.data.stock.ProductViewModel;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 public  class  Products extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rcvProduct;
    private FloatingActionMenu fab;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private View mShadowView;
    private ProductsAdapter productsAdapter;
     private ProductViewModel mModel;
    private ProductsAdapter.OnItemListener OnitemListener;
     private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rcvProduct = findViewById(R.id.rcv_product);
        mShadowView = findViewById(R.id.shadow);

        fab = findViewById(R.id.float_menu);

        fab1 = findViewById(R.id.sell_article_fb);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Products.this, SellArticleActivity.class));
            }
        });

        fab2 = findViewById(R.id.add_article_fb);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Products.this, AddArticle.class));
            }
        });


        productsAdapter = new ProductsAdapter(getListProduct(),this,OnitemListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productsAdapter.setData(getListProduct());
        rcvProduct.setAdapter(productsAdapter);
        mModel= ViewModelProviders.of(this).get(ProductViewModel.class);
        mModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productsAdapter.setData(products);
            }
        });
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


     public void updateSearchResults(String searchQuery) {
         if(productList != null) {
             List<Product> searchResults = new ArrayList<>();

             for (Product product : productList) {

                 if (product.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                         product.getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                     searchResults.add(product);
                 }
             }

             productsAdapter.setData(searchResults);
         }
     }



    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        Date date = new Date();


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
        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.requestFocus();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("search");

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(Products.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(Products.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(Products.this, DocumentsActivity.class));


        } else if (id == R.id.nav_stock) {
            startActivity(new Intent(Products.this, StockActivity.class));
        } else if (id == R.id.nav_credit) {
            startActivity(new Intent(Products.this, CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( Products.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            Toast.makeText(getApplicationContext(),"rapport",Toast.LENGTH_LONG).show();
        }  else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( Products.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( Products.this,ClientActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
