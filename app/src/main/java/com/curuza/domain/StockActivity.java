package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, StockAdapter.OnDeleteClickListener {

    private RecyclerView rcvProduct;
    private StockAdapter stockAdapter;
    private View mShadowView;
    private ProductViewModel mModel;
    private ProductRepository mProductRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock);

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


        stockAdapter = new StockAdapter(getListProduct(),this,this::OnDeleteClickListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        stockAdapter.setData(getListProduct());
        rcvProduct.setAdapter(stockAdapter);
        mModel= ViewModelProviders.of(this).get(ProductViewModel.class);
        mModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                stockAdapter.setData(products);
            }
        });

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
            startActivity(new Intent(StockActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(StockActivity.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(StockActivity.this, DocumentsActivity.class));

        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent( StockActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent( StockActivity.this,CreditActivity.class));
        }
        else if (id == R.id.nav_depense) {
            startActivity(new Intent( StockActivity.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            Toast.makeText(getApplicationContext(),"rapport",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( StockActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( StockActivity.this,ClientActivity.class));
        }
        else if (id == R.id.nav_rapport) {
            startActivity(new Intent( StockActivity.this,RapportActivity.class));
        }  else if (id == R.id.nav_settings) {
            startActivity(new Intent( StockActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_question) {
            startActivity(new Intent( StockActivity.this,QuestionsActivity.class));
        } else if (id == R.id.nav_subscription) {
            startActivity(new Intent( StockActivity.this,SubscriptionsActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent( StockActivity.this,HelpActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnDeleteClickListener(Product product) {
        mProductRepository = new ProductRepository(getApplicationContext());
        mProductRepository.delete(product);
    }



}

