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
import com.curuza.data.client.Client;
import com.curuza.data.client.ClientRepository;
import com.curuza.data.client.ClientViewModel;

import com.curuza.data.credit.Credit;
import com.curuza.data.credit.CreditRepository;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ClientAdapter.OnDeleteClickListener {

    private RecyclerView rcvClient;
    private ClientAdapter clientAdapter;
    private View mShadowView;
    private ClientViewModel mModel;
    private FloatingActionMenu clientFab;
    private ClientRepository mClientRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);

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

        rcvClient = findViewById(R.id.rcv_client);
        mShadowView = findViewById(R.id.shadow);
        clientFab = findViewById(R.id.add_client);
        clientFab.setOnMenuButtonClickListener(v ->  startActivity(new Intent(ClientActivity.this, AddClientActivity.class)));


        clientAdapter = new ClientAdapter(getListClient(),this,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvClient.setLayoutManager(linearLayoutManager);

        clientAdapter.setData(getListClient());
        rcvClient.setAdapter(clientAdapter);
        mModel= ViewModelProviders.of(this).get(ClientViewModel.class);
        mModel.getClients().observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients) {
                clientAdapter.setData(clients);
            }
        });

    }

    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private List<Client> getListClient() {
        List<Client> list = new ArrayList<>();
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
            startActivity(new Intent(ClientActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(ClientActivity.this,Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(ClientActivity.this, DocumentsActivity.class));

        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent( ClientActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent( ClientActivity.this,CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( ClientActivity.this,DepenseActivity.class));
        }  else if (id == R.id.nav_rapport) {
            Toast.makeText(getApplicationContext(),"rapport",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( ClientActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( ClientActivity.this,ClientActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnDeleteClickListener(Client client) {
        mClientRepository = new ClientRepository(getApplicationContext());
        mClientRepository.delete(client);
    }

}
