package com.example.davod.khayyam.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.davod.khayyam.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class AboutUsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_about_us);

        setupView();
    }

    private void setupView() {
        setupToolbar();
        setupNavigationView();
        setupTextView();
    }

    private void setupTextView() {
        Button khayyam = findViewById(R.id.btn_khayyam);
        Button yaghoot = findViewById(R.id.btn_yaghoot);

        khayyam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://khayiam.mihanblog.com/"));
                startActivity(intent);
            }
        });

        yaghoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://yaghoot.studio/"));
                startActivity(intent);
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

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view_about_us);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_menu:
                        Intent mainIntent = new Intent(AboutUsActivity.this,MainActivity.class);
                        startActivity(mainIntent);
                        break;
                    case R.id.favorites:
                        Intent intent = new Intent(AboutUsActivity.this,PoemsActivity.class);
                        intent.putExtra(PoemMenuActivity.ID,0);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.about_us:
                        drawerLayout.closeDrawers();
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
        toolbar = findViewById(R.id.toolbar_about_us);
        drawerLayout = findViewById(R.id.drawer_layout_about_us);
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
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
