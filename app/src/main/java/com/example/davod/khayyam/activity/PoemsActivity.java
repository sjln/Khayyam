package com.example.davod.khayyam.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.adapter.RecyclerViewAdapterPoems;
import com.example.davod.khayyam.dataFakeGenerator.DataFakeGeneratorPoems;
import com.example.davod.khayyam.dataModel.DataModelPoems;
import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class PoemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterPoems adapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int id;
    private DataModelPoems model;
    private String TAG = "TAG";
    private FloatingActionButton fab;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_poems);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent intent = getIntent();
        id = intent.getIntExtra(PoemMenuActivity.ID, 0);
        model = intent.getParcelableExtra(RecyclerViewAdapterPoems.MODEL);
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    private void setupViews() {
        setupRecyclerView();
        setupToolbar();
        setupNavigationView();
        setupFloatActionButton();
    }

    private void setupFloatActionButton() {
        fab = findViewById(R.id.fab_poems);
        final ScaleAnimation scaleAnimation = new ScaleAnimation
                (1.0f,0.5f,1.0f,0.5f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(250);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.startAnimation(scaleAnimation);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "float_search");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent intent = new Intent(PoemsActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view_poems);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu:
                        Intent intent_main = new Intent(PoemsActivity.this, MainActivity.class);
                        startActivity(intent_main);
                        break;
                    case R.id.favorites:
                        if (id == 0)
                            drawerLayout.closeDrawers();
                        else {
                            Intent intent = new Intent(PoemsActivity.this, PoemsActivity.class);
                            intent.putExtra(PoemMenuActivity.ID, 0);
                            startActivity(intent);
                        }
                        break;
                    case R.id.setting:
                        Intent intent_setting = new Intent(PoemsActivity.this,SettingActivity.class);
                        startActivity(intent_setting);
                        break;
                    case R.id.about_us:
                        Intent intent_about_us = new Intent(PoemsActivity.this,AboutUsActivity.class);
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

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_poems);
        drawerLayout = findViewById(R.id.drawer_layout_poems);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_poems);
        adapter = new RecyclerViewAdapterPoems(this, DataFakeGeneratorPoems.getData(id, this), id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0 && fab.isShown())
                    fab.hide();
                if (dy<0)
                    fab.show();
            }
        });

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
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            finish();
        }
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
                finish();
                break;
            case R.id.search:
                Intent intent = new Intent(PoemsActivity.this,SearchActivity.class);
                startActivity(intent);
                Bundle bundle = new Bundle();
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

