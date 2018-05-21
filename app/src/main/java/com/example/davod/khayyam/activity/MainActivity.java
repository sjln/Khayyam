package com.example.davod.khayyam.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.adapter.RecyclerViewAdapterMain;
import com.example.davod.khayyam.dataFakeGenerator.DataFakeGeneratorMain;
import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapterMain adapter;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupViews();

        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
    }

    private void setupViews() {
        setupRecyclerView();
        setupToolbar();
        setupNavigationView();
    }

    private void setupFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                        .setDefaultFontPath("font/iranSans.ttf")
                                         .setFontAttrId(R.attr.fontPath)
                                          .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view_main);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_menu:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.favorites:
                        Intent intent = new Intent(MainActivity.this,PoemsActivity.class);
                        intent.putExtra(PoemMenuActivity.ID,0);
                        MainActivity.this.startActivity(intent);
                        break;
                    case R.id.setting:
                        Intent intent_setting = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent_setting);
                        break;
                    case R.id.about_us:
                        Intent intent_about_us = new Intent(MainActivity.this,AboutUsActivity.class);
                        startActivity(intent_about_us);
                        break;
                    case R.id.ratting:
                        Intent intent_bazar = new Intent(Intent.ACTION_EDIT);
                        intent_bazar.setData(Uri.parse("bazaar://details?id=" + PACKAGE_NAME));
                        intent_bazar.setPackage("com.farsitel.bazaar");
                        startActivity(intent_bazar);
                        break;

                }
                return false;
            }
        });
    }


    public void setupToolbar() {
        toolbar = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.drawer_layout_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_main);
        adapter = new RecyclerViewAdapterMain(this, DataFakeGeneratorMain.getData(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        showAlphaAnimation(recyclerView);
    }

    @Override
    public void onBackPressed() {
        setupFont();
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            setupDialog();
        }
    }

    private void setupDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        dialog.setTitle("خروج").setMessage("آیا می خواهید خارج شوید ؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("امتیاز دادن", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setData(Uri.parse("bazaar://details?id=" + PACKAGE_NAME));
                        intent.setPackage("com.farsitel.bazaar");
                        startActivity(intent);
                    }
                }).setCancelable(true);
        dialog.create().show();
    }

    private void showAlphaAnimation(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poem_menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                setupDialog();
                break;
            case R.id.search:
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                Bundle bundle = new Bundle();
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
