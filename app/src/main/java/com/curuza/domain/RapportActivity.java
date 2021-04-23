package com.curuza.domain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.movements.MovementViewModel;
import com.curuza.data.view.Rapport;
import com.curuza.data.view.ProductMovement;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RapportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RapportAdapter rapportAdapter;
    private List<Rapport> rapportList;
    private Context mContext;
    private RecyclerView rcvRapport;
    private MovementViewModel mModel;
    private RapportAdapter.OnItemListener mOnItemListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapport);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rcvRapport = findViewById(R.id.rcv_rapport);
        rapportAdapter = new RapportAdapter(getListRapport(),mContext,mOnItemListener);
        rapportAdapter.setData(getListRapport());
        rcvRapport.setAdapter(rapportAdapter);



        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private List<Rapport> getListRapport() {
        List<Rapport> list = new ArrayList<>();
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
            startActivity(new Intent(RapportActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(RapportActivity.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(RapportActivity.this, DocumentsActivity.class));

        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent( RapportActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent( RapportActivity.this,CreditActivity.class));
        }
        else if (id == R.id.nav_depense) {
            startActivity(new Intent( RapportActivity.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            startActivity(new Intent( RapportActivity.this,RapportActivity.class));
        }
        else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( RapportActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( RapportActivity.this,ClientActivity.class));
        }   else if (id == R.id.nav_settings) {
            startActivity(new Intent( RapportActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_question) {
            startActivity(new Intent( RapportActivity.this,QuestionsActivity.class));
        } else if (id == R.id.nav_subscription) {
            startActivity(new Intent( RapportActivity.this,SubscriptionsActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent( RapportActivity.this,HelpActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
