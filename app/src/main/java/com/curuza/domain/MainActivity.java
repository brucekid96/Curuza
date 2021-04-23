package com.curuza.domain;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curuza.R;
import com.curuza.data.view.Rapport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fab;
    private CardView mProducts;
    private CardView mDocuments;
    private ImageView productIcon;
    private ImageView documentIcon;
    private TextView ProductTitle;
    private TextView DocumentTitle;
    private CardView mAddProduct;
    private CardView mSellProduct;
    private ImageView sellProductIcon;
    private ImageView addProductIcon;
    private CardView mDepenses;
    private ImageView mDepenseIcon;
    private TextView mDepenseTitle;
    private CardView mAddFournisseur;
    private CardView mAddCredit;
    private ImageView mFournisseurIcon;
    private ImageView mCreditIcon;
    private CardView mContactUs;
    private CardView mSettings;
    private ImageView mContactIcon;
    private ImageView mSettingIcon;
    private CardView mHelp;
    private CardView mShare;
    private ImageView mHelpIcon;
    private ImageView mShareIcon;
    private CardView mRapport;
    private ImageView mRapportIcon;
    private TextView mRapportTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        mProducts = findViewById(R.id.products_card);
        mProducts.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Products.class));
            }
        });
        mDocuments = findViewById(R.id.document_card);
        mDocuments.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DocumentsActivity.class));
            }
        });
        productIcon = findViewById(R.id.product_icon);
        documentIcon = findViewById(R.id.document_icon);
        ProductTitle= findViewById(R.id.product_title);
        DocumentTitle= findViewById(R.id.document_title);
        mAddProduct=findViewById(R.id.add_product_card);
        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddArticle.class));
            }
        });
        mSellProduct=findViewById(R.id.sell_product_card);
        mSellProduct.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SellArticleActivity.class));
            }
        });
        sellProductIcon=findViewById(R.id.add_product_icon);
        addProductIcon=findViewById(R.id.sell_product_icon);
        mDepenses=findViewById(R.id.depense_card);
        mDepenses.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DepenseActivity.class));
            }
        });
        mDepenseIcon=findViewById(R.id.depense_icon);
        mDepenseTitle=findViewById(R.id.depense_title);
        mAddFournisseur=findViewById(R.id.add_fournisseur_card);
        mAddFournisseur.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FournisseurActivity.class));
            }
        });
        mFournisseurIcon=findViewById(R.id.add_fournisseur_icon);
        mAddCredit=findViewById(R.id.add_credit_card);
        mAddCredit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreditActivity.class));
            }
        });
        mCreditIcon=findViewById(R.id.add_credit_icon);
        mContactUs=findViewById(R.id.contact_us_card);
        mContactUs.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuestionsActivity.class));
            }
        });
        mContactIcon=findViewById(R.id.contact_us_icon);
        mSettings=findViewById(R.id.add_settings_card);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        mSettingIcon=findViewById(R.id.add_settings_icon);
        mHelp=findViewById(R.id.help_card);
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
            }
        });
        mHelpIcon=findViewById(R.id.help_icon);
        mShare=findViewById(R.id.share_app_card);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShareApp.class));
            }
        });
        mShareIcon=findViewById(R.id.share_app_icon);
        mRapport=findViewById(R.id.rapport_card);
        mRapport.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RapportActivity.class));
            }
        });
        mRapportIcon=findViewById(R.id.rapport_icon);
        mRapportTitle=findViewById(R.id.rapport_title);






        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String timestamp = "2020-04-18T05:39:24Z";
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(timestamp), ZoneId.systemDefault());
        String dateTimeStr = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(dateTime);
        Log.d("MainActivity", "Date and time: " + dateTimeStr);

        String instant = ZonedDateTime.now().toInstant().toString();
        Log.d("AddArticle", "Article added at: " + instant);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        int id = item.getItemId();
//
//        if (id == R.id.menu_item_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(MainActivity.this, Products.class));

        } else if (id == R.id.nav_documents) {
            startActivity(new Intent(MainActivity.this, DocumentsActivity.class));


        }  else if (id == R.id.nav_stock) {
            startActivity(new Intent(MainActivity.this, StockActivity.class));
        }  else if (id == R.id.nav_credit) {
            startActivity(new Intent(MainActivity.this, CreditActivity.class));
        }  else if (id == R.id.nav_depense) {
            startActivity(new Intent( MainActivity.this,DepenseActivity.class));
        }
        else if (id == R.id.nav_fournisseur) {
            startActivity(new Intent( MainActivity.this,FournisseurActivity.class));
        }  else if (id == R.id.nav_client) {
            startActivity(new Intent( MainActivity.this,ClientActivity.class));
        } else if (id == R.id.nav_rapport) {
            startActivity(new Intent( MainActivity.this,RapportActivity.class));
        }  else if (id == R.id.nav_settings) {
            startActivity(new Intent( MainActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_question) {
            startActivity(new Intent( MainActivity.this,QuestionsActivity.class));
        } else if (id == R.id.nav_subscription) {
            startActivity(new Intent( MainActivity.this,SubscriptionsActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent( MainActivity.this,HelpActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent( MainActivity.this,ShareApp.class));
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}