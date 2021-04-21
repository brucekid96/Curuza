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
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.fournisseur.FournisseurRepository;
import com.curuza.data.fournisseur.FournisseurViewModel;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FournisseurActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FournisseurAdapter.OnDeleteClickListener {

    private RecyclerView rcvFournisseur;
    private FournisseurAdapter fournisseurAdapter;
    private View mShadowView;
    private FournisseurViewModel mModel;
    private FournisseurRepository mFournisseurRepository;
    private FloatingActionMenu fournisseurFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fournisseur);

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

        rcvFournisseur = findViewById(R.id.rcv_fournisseur);
        mShadowView = findViewById(R.id.shadow);
        fournisseurFab = findViewById(R.id.add_fournisseur);
        fournisseurFab.setOnMenuButtonClickListener(v ->  startActivity(new Intent(FournisseurActivity.this, AddFournisseurActivity.class)));


        fournisseurAdapter = new FournisseurAdapter(getListFournisseur(),this,this::OnDeleteClickListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvFournisseur.setLayoutManager(linearLayoutManager);

        fournisseurAdapter.setData(getListFournisseur());
        rcvFournisseur.setAdapter(fournisseurAdapter);
        mModel= ViewModelProviders.of(this).get(FournisseurViewModel.class);
        mModel.getFournisseurs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
                fournisseurAdapter.setData(fournisseurs);
            }
        });

    }

    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private List<Fournisseur> getListFournisseur() {
        List<Fournisseur> list = new ArrayList<>();
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
            startActivity(new Intent(FournisseurActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(FournisseurActivity.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(FournisseurActivity.this, DocumentsActivity.class));

        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent( FournisseurActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent( FournisseurActivity.this,CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( FournisseurActivity.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            Toast.makeText(getApplicationContext(),"rapport",Toast.LENGTH_LONG).show();
        }  else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( FournisseurActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( FournisseurActivity.this,ClientActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void OnDeleteClickListener(Fournisseur fournisseur) {
        mFournisseurRepository = new FournisseurRepository(getApplicationContext());
        mFournisseurRepository.delete(fournisseur);
    }
}
