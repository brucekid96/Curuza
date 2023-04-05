package com.curuza.domain;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.curuza.R;
import com.curuza.data.client.ClientRepository;
import com.curuza.data.credit.CreditRepository;
import com.curuza.data.depense.DepenseRepository;
import com.curuza.data.fournisseur.FournisseurRepository;
import com.curuza.data.movements.MovementRepository;
import com.curuza.data.photos.PhotoRepository;
import com.curuza.data.photos.PhotoType;
import com.curuza.data.remote.AmplifyAPI;
import com.curuza.data.remote.AppSyncApi;
import com.curuza.data.s3.S3Transfer;
import com.curuza.data.s3.S3TransferRepository;
import com.curuza.data.stock.ProductRepository;
import com.curuza.domain.common.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String DBG_TAG = MainActivity.class.getSimpleName();

    FloatingActionButton fab;
    private CardView mProducts;
    private CardView mDocuments;
    private ImageView productIcon;
    private ImageView documentIcon;
    private TextView ProductTitle;
    private TextView DocumentTitle;
    private TextView mName;
    private ImageView mPictIcon;
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
    private CardView mClient;
    private CardView mShare;
    private ImageView mClientIcon;
    private ImageView mShareIcon;
    private CardView mRapport;
    private ImageView mRapportIcon;
    private TextView mRapportTitle;
    private String mCurrentUser;
    AppSyncApi mAppSyncApi;

    private PhotoRepository mPhotoRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public static void launch(Context context, boolean clearHistory) {
        Intent intent = new Intent(context, MainActivity.class);
        if (clearHistory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        context.startActivity(intent);
    }

    public static void launch(Context context) {
        launch(context, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAppSyncApi = new AppSyncApi(getApplicationContext());



        mCurrentUser= Amplify.Auth.getCurrentUser().getUserId();



        mPhotoRepository = new PhotoRepository(getApplicationContext());

        File thumbnailFile =
            new File(mPhotoRepository.getUserProfilePhotoUri(mCurrentUser).getPath());

        if (thumbnailFile.exists()) {
            mPictIcon.setImageURI(mPhotoRepository.getUserProfilePhotoUri(mCurrentUser));
        } else {
            mPictIcon.setImageResource(R.drawable.ic_profile_user);
        }


        mPhotoRepository.fetchPhotoUpstream(mCurrentUser, PhotoType.USER_PROFILE_PHOTO)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() ->  mPictIcon.setImageURI(mPhotoRepository.getUserProfilePhotoUri(mCurrentUser)),e -> {});




       mAppSyncApi.getProducts()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(products -> Log.d(DBG_TAG,"appsync products"+products), e -> {});



       mAppSyncApi.getCredits()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(credits -> Log.d(DBG_TAG,"appsync credits"+credits), e -> {});

       mAppSyncApi.getMovements()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(movements -> Log.d(DBG_TAG,"appsync movements"+movements), e -> {});

        mAppSyncApi.getDepenses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(depenses -> Log.d(DBG_TAG,"appsync depenses"+depenses), e -> {});

        mAppSyncApi.getFournisseurs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fournisseurs -> Log.d(DBG_TAG,"appsync fournisseurs"+fournisseurs), e -> {});

        mAppSyncApi.getClients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(clients  -> Log.d(DBG_TAG,"appsync clients"+clients), e -> {});

    }

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

        mName = findViewById(R.id.name_textview);

        mPictIcon = findViewById(R.id.pict_icon);






        AmplifyAPI.getUserAttributes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setGreeting, e -> {});

        mProducts = findViewById(R.id.products_card);
        mProducts.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProductsActivity.class)));
        mDocuments = findViewById(R.id.document_card);
        mDocuments.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DocumentsActivity.class)));
        productIcon = findViewById(R.id.product_icon);
        documentIcon = findViewById(R.id.document_icon);
        ProductTitle= findViewById(R.id.product_title);
        DocumentTitle= findViewById(R.id.document_title);
        mAddProduct=findViewById(R.id.add_product_card);
        mAddProduct.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddArticle.class)));
        mSellProduct=findViewById(R.id.sell_product_card);
        mSellProduct.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SellArticleActivity.class)));
        sellProductIcon=findViewById(R.id.add_product_icon);
        addProductIcon=findViewById(R.id.sell_product_icon);
        mDepenses=findViewById(R.id.depense_card);
        mDepenses.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DepenseActivity.class)));
        mDepenseIcon=findViewById(R.id.depense_icon);
        mDepenseTitle=findViewById(R.id.depense_title);
        mAddFournisseur=findViewById(R.id.add_fournisseur_card);
        mAddFournisseur.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FournisseurActivity.class)));
        mFournisseurIcon=findViewById(R.id.add_fournisseur_icon);
        mAddCredit=findViewById(R.id.add_credit_card);
        mAddCredit.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreditActivity.class)));
        mCreditIcon=findViewById(R.id.add_credit_icon);
        mContactUs=findViewById(R.id.contact_us_card);
        mContactUs.setOnClickListener( v ->
        {String url = "https://api.whatsapp.com/send?phone=+25779841239";
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);});
        mContactIcon=findViewById(R.id.contact_us_icon);
        mSettings=findViewById(R.id.add_settings_card);
        mSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AccountActivity.class)));
        mSettingIcon=findViewById(R.id.add_settings_icon);
        mClient =findViewById(R.id.help_card);
        mClient.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ClientActivity.class)));
        mClientIcon =findViewById(R.id.client_icon);
        mShare=findViewById(R.id.share_app_card);
        mShare.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShareApp.class)));
        mShareIcon=findViewById(R.id.share_app_icon);
        mRapport=findViewById(R.id.rapport_card);
        mRapport.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RapportActivity.class)));
        mRapportIcon=findViewById(R.id.rapport_icon);
        mRapportTitle=findViewById(R.id.rapport_title);



        mPhotoRepository = new PhotoRepository(this);
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

        mDisposable.add(
            Completable.concatArray(
            syncProducts(),
            syncFournisseurs(),
            syncClients(),
            syncCredits(),
            syncDepenses(),
            syncMovements())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, e -> {
                    Log.d(DBG_TAG, "Synchronization failed: " + e.toString() + ", cause = " + e.getCause());
                }));



    }

    private Completable syncFailedPicts() {
        return Single.fromCallable(() -> new S3TransferRepository(this))
            .flatMap(S3TransferRepository::getQueuedTransfers)
            .flatMapCompletable(s3Transfers -> {
                Log.d(DBG_TAG, "S3 transfers: " + s3Transfers);
                List<Completable> transferUploads = new ArrayList<>();
                for (S3Transfer s3Transfer : s3Transfers) {
                    transferUploads.add(mPhotoRepository.processPendingTransfer(s3Transfer));
                }

                return Completable.merge(transferUploads);
            });
    }

    private Completable syncProducts() {
        return Single.fromCallable(() -> new ProductRepository(this))
            .flatMapCompletable(ProductRepository::syncProducts);
    }

    private Completable syncFournisseurs() {
        return Single.fromCallable(() -> new FournisseurRepository(this))
            .flatMapCompletable(FournisseurRepository::syncFournisseurs);
    }

    private Completable syncClients() {
        return Single.fromCallable(() -> new ClientRepository(this))
            .flatMapCompletable(ClientRepository::syncClients);
    }

    private Completable syncCredits() {
        return Single.fromCallable(() -> new CreditRepository(this))
            .flatMapCompletable(CreditRepository::syncCredits);
    }

    private Completable syncDepenses() {
        return Single.fromCallable(() -> new DepenseRepository(this))
            .flatMapCompletable(DepenseRepository::syncDepenses);
    }

    private Completable syncMovements() {
        return Single.fromCallable(() -> new MovementRepository(this))
            .flatMapCompletable(MovementRepository::syncMovements);
    }

    private void setGreeting(List<AuthUserAttribute> attributes) {
        String firstName = "";
        String lastName = "";
        for(AuthUserAttribute attribute : attributes) {
            if(attribute.getKey().getKeyString().equals("given_name")){
                firstName = attribute.getValue();
                break;
            }
        }



        mName.setText("Welcome "+ firstName+"!");

        Log.d(DBG_TAG,"success"+firstName);

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
    protected void onResume() {
        super.onResume();
        Log.d(DBG_TAG, "onResume");
       /* syncFailedPicts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> { }, e -> {
            });*/
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent i = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(i);
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
            Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_products) {
            startActivity(new Intent(MainActivity.this, ProductsActivity.class));

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
            startActivity(new Intent( MainActivity.this,AccountActivity.class));
        } else if (id == R.id.nav_question) {
            String url = "https://api.whatsapp.com/send?phone=+25779841239";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            startActivity(new Intent( MainActivity.this,ShareApp.class));
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}