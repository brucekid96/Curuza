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
import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.curuza.data.credit.CreditViewModel;
import com.curuza.data.stock.Product;
import com.curuza.data.stock.ProductRepository;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CreditAdapter.OnDeleteClickListener {

    private RecyclerView rcvCredit;
    private CreditAdapter creditAdapter;
    private View mShadowView;
    private CreditViewModel mModel;
    private FloatingActionMenu creditFab;
    private CreditRepository mCreditRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit);

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

        rcvCredit = findViewById(R.id.rcv_credit);
        mShadowView = findViewById(R.id.shadow);
        creditFab = findViewById(R.id.add_credit);
        creditFab.setOnMenuButtonClickListener(v ->  startActivity(new Intent(CreditActivity.this, AddCreditActivity.class)));


        creditAdapter = new CreditAdapter(getListCredit(),this,this::OnDeleteClickListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCredit.setLayoutManager(linearLayoutManager);

        creditAdapter.setData(getListCredit());
        rcvCredit.setAdapter(creditAdapter);
        mModel= ViewModelProviders.of(this).get(CreditViewModel.class);
        mModel.getCredits().observe(this, new Observer<List<Credit>>() {
            @Override
            public void onChanged(List<Credit> credits) {
                creditAdapter.setData(credits);
            }
        });

    }

    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private List<Credit> getListCredit() {
        List<Credit> list = new ArrayList<>();
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
            startActivity(new Intent(CreditActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(CreditActivity.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(CreditActivity.this, DocumentsActivity.class));

        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent( CreditActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent( CreditActivity.this,CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( CreditActivity.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            Toast.makeText(getApplicationContext(),"rapport",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( CreditActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( CreditActivity.this,ClientActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnDeleteClickListener(Credit credit) {
        mCreditRepository = new CreditRepository(getApplicationContext());
        mCreditRepository.delete(credit);
    }

}

