package com.curuza.domain;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.curuza.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class DocumentsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AllProductsFragment.OnFragmentInteractionListener,
        EnterProductsFragment.OnFragmentInteractionListener, ExitProductsFragment.OnFragmentInteractionListener {


    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Documents");

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tous"));
        tabLayout.addTab(tabLayout.newTab().setText("Entrée"));
        tabLayout.addTab(tabLayout.newTab().setText("Sortie"));
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
            startActivity(new Intent(DocumentsActivity.this, MainActivity.class));

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(DocumentsActivity.this, Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(DocumentsActivity.this, DocumentsActivity.class));

        }
        else if (id == R.id.nav_stock) {
            startActivity(new Intent(DocumentsActivity.this, StockActivity.class));
        } else if (id == R.id.nav_credit) {
            startActivity(new Intent(DocumentsActivity.this, CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( DocumentsActivity.this,DepenseActivity.class));
        } else if (id == R.id.nav_rapport) {
            Toast.makeText(getApplicationContext(),"rapport",Toast.LENGTH_LONG).show();
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
            startActivity(new Intent( DocumentsActivity.this,QuestionsActivity.class));
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
