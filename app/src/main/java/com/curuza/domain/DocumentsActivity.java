package com.curuza.domain;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.curuza.R;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.view.ProductMovement;
import com.curuza.utils.ExcelExporter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DocumentsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AllProductsFragment.OnFragmentInteractionListener,
        EnterProductsFragment.OnFragmentInteractionListener, ExitProductsFragment.OnFragmentInteractionListener {
    private static final String DBG_TAG = DocumentsActivity.class.getSimpleName();

    private List<ProductMovement> allProductMovements;
    private List<ProductMovement> enterProductMovements;
    private List<ProductMovement> exitProductMovements;
    private MovementRepository mMovementRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.documents_activity);

        mMovementRepository = new MovementRepository(this);

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Tous_activity));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.entree_activity));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sortie_activity));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#a0d8d2"),
                Color.parseColor("#FFFFFF"));


        final ViewPager viewPager = findViewById(R.id.pager);
        final DocumentPagerAdapter adapter = new DocumentPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener
                (new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDisposable.add(
            mMovementRepository.getProductMovements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadAllProducts));

        mDisposable.add(
            mMovementRepository.getEnterProductMovements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadEnterProducts));

        mDisposable.add(
            mMovementRepository.getExitProductMovements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadExitProducts));

//        mModel= ViewModelProviders.of(this).get(MovementViewModel.class);
//        mModel.getAllProductMovements().observe(this, productMovement -> {
//            allProductMovements = productMovement;
//        });
//        mModel.getEnterProductMovements().observe(this, productEnterMovements ->  {
//            enterProductMovements = productEnterMovements;
//        });
//        mModel.getExitProductMovements().observe(this, productExitMovements ->  {
//            exitProductMovements = productExitMovements;
//        });




    }
    public void loadAllProducts(List<ProductMovement> productMovements) {
        allProductMovements = productMovements;
        Log.d(DBG_TAG,"all product data is " + productMovements);
    }
    public void loadEnterProducts(List<ProductMovement> productEnterMovements) {
        enterProductMovements = productEnterMovements;
        Log.d(DBG_TAG,"enter product data is " + productEnterMovements);
    }
    public void loadExitProducts(List<ProductMovement> productExitMovements) {
        exitProductMovements = productExitMovements;
        Log.d(DBG_TAG,"exit product data is " + productExitMovements);
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
        getMenuInflater().inflate(R.menu.documents, menu);
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
                AllProductsFragment all = new AllProductsFragment();
                all.updateSearchResults(query);
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
        if(id == R.id.all) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 1);
                } else {
                    ExcelExporter.exportAllDocuments(getApplicationContext(), allProductMovements);
                }
            }
        }
        if(id == R.id.enter) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 1);
                } else {
                    ExcelExporter.exportEnterDocuments(getApplicationContext(), enterProductMovements);
                }
            }
        }
        if(id == R.id.exit) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 1);
                } else {
                    ExcelExporter.exportExitDocuments(getApplicationContext(), exitProductMovements);
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
            startActivity(new Intent(DocumentsActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(DocumentsActivity.this, ProductsActivity.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(DocumentsActivity.this, DocumentsActivity.class));

        }
        else if (id == R.id.nav_stock) {
            startActivity(new Intent(DocumentsActivity.this, StockActivity.class));
        } else if (id == R.id.nav_credit) {
            startActivity(new Intent(DocumentsActivity.this, CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( DocumentsActivity.this,DepenseActivity.class));
        }  else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( DocumentsActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( DocumentsActivity.this,ClientActivity.class));
        }

        else if (id == R.id.nav_rapport) {
            startActivity(new Intent( DocumentsActivity.this,RapportActivity.class));
        }  else if (id == R.id.nav_settings) {
            startActivity(new Intent( DocumentsActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_question) {
            String url = "https://api.whatsapp.com/send?phone=+25779841239";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (id == R.id.nav_subscription) {
            startActivity(new Intent( DocumentsActivity.this,SubscriptionsActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent( DocumentsActivity.this,HelpActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
