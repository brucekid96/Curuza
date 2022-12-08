package com.curuza.domain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.help.Help;
import com.curuza.domain.common.BaseActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView mRecyclerView;
    private HelpAdapter helpAdapter;
    private List<Help> helpList;
    private HelpAdapter.OnItemListener OnitemListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView =findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        helpList = new ArrayList<>(SampleData.getSampleHelp());

        helpAdapter = new HelpAdapter(SampleData.getSampleHelp(),this,OnitemListener);
        mRecyclerView.setAdapter(helpAdapter);





        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_item_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(HelpActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(HelpActivity.this, ProductsActivity.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(HelpActivity.this, DocumentsActivity.class));

        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent( HelpActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent( HelpActivity.this,CreditActivity.class));
        }
        else if (id == R.id.nav_depense) {
            startActivity(new Intent( HelpActivity.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            startActivity(new Intent( HelpActivity.this,RapportActivity.class));
        }
        else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( HelpActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( HelpActivity.this,ClientActivity.class));
        }   else if (id == R.id.nav_settings) {
            startActivity(new Intent( HelpActivity.this,AccountActivity.class));
        } else if (id == R.id.nav_question) {
            String url = "https://api.whatsapp.com/send?phone=+25779841239";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (id == R.id.nav_subscription) {
            startActivity(new Intent( HelpActivity.this,SubscriptionsActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent( HelpActivity.this,HelpActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent( HelpActivity.this,ShareApp.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
