package com.curuza.domain;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curuza.R;
import com.curuza.data.client.Client;
import com.curuza.data.client.ClientRepository;
import com.curuza.domain.common.BaseActivity;
import com.curuza.utils.ExcelExporter;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ClientActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

  private RecyclerView rcvClient;
  private ClientAdapter clientAdapter;
  private View mShadowView;
  private FloatingActionMenu clientFab;
  private ClientRepository mClientRepository;
  private List<Client> clientList;
  private CompositeDisposable mDisposable = new CompositeDisposable();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.client);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

     mClientRepository = new ClientRepository(this);

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


    clientAdapter = new ClientAdapter(getListClient(),this);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    rcvClient.setLayoutManager(linearLayoutManager);
    clientAdapter.setData(getListClient());

    rcvClient.setAdapter(clientAdapter);
    mDisposable.add(
        mClientRepository.getClients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadClients));
  }

  public void loadClients(List<Client> clients) {
    clientList = clients;
    clientAdapter.setData(clients);
  }



  public void updateSearchResults(String searchQuery) {
    if(clientList != null) {
      List<Client> searchResults = new ArrayList<>();

      for (Client client : clientList) {

        if (client.getPersonName().toLowerCase().contains(searchQuery.toLowerCase()) ||
            client.getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
          searchResults.add(client);
        }
      }

      clientAdapter.setData(searchResults);
    }
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
    getMenuInflater().inflate(R.menu.client, menu);
    MenuItem menuItem = menu.findItem(R.id.menu_item_search);
    SearchView searchView =(SearchView) menuItem.getActionView();
    searchView.requestFocus();
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


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
    if (id == R.id.export_client) {
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
          String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
          requestPermissions(permissions, 1);
        } else {
          ExcelExporter.exportClients(getApplicationContext(),clientList);
        }
      }
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
      startActivity(new Intent(ClientActivity.this, ProductsActivity.class));

    } else if (id == R.id.nav_documents) {
      startActivity(new Intent(ClientActivity.this, DocumentsActivity.class));

    }  else if (id == R.id.nav_stock) {
      startActivity(new Intent( ClientActivity.this, StockActivity.class));
    }  else if (id == R.id.nav_credit) {
      startActivity(new Intent( ClientActivity.this,CreditActivity.class));
    }  else if (id == R.id.nav_depense) {
      startActivity(new Intent( ClientActivity.this,DepenseActivity.class));
    }
    else if (id == R.id.nav_fournisseur) {
      startActivity(new Intent( ClientActivity.this,FournisseurActivity.class));
    }  else if (id == R.id.nav_client) {
      startActivity(new Intent( ClientActivity.this,ClientActivity.class));
    } else if (id == R.id.nav_rapport) {
      startActivity(new Intent( ClientActivity.this,RapportActivity.class));
    }  else if (id == R.id.nav_settings) {
      startActivity(new Intent( ClientActivity.this,AccountActivity.class));
    } else if (id == R.id.nav_question) {
      String url = "https://api.whatsapp.com/send?phone=+25779841239";
      Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(i);
    } else if (id == R.id.nav_subscription) {
      startActivity(new Intent( ClientActivity.this,SubscriptionsActivity.class));
    } else if (id == R.id.nav_help) {
      startActivity(new Intent( ClientActivity.this,HelpActivity.class));
    } else if (id == R.id.nav_share) {
      startActivity(new Intent( ClientActivity.this,ShareApp.class));
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }


}
