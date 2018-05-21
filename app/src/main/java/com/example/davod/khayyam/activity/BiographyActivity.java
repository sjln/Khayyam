package com.example.davod.khayyam.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davod.khayyam.R;

import java.io.IOException;
import java.io.InputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class BiographyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_biography);

        setupViews();

    }

    private void setupViews() {
        setupToolbar();
        setupNavigationView();
        setupLayout();
    }

    private void setupLayout() {
        TextView txt = findViewById(R.id.txt_bio);
        ImageView image = findViewById(R.id.image_biography);

        String fileName ;

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        if (position == 3) {
            fileName = "result";
            image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.result));
        } else {
            fileName = "bio";
            image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.biography));
        }

        try {
            InputStream stream = getAssets().open("file/"+fileName+".txt");
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            txt.setText(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupToolbar() {
        toolbar = findViewById(R.id.toolbar_bio);
        drawerLayout = findViewById(R.id.drawer_layout_bio);
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

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view_bio);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu:
                        finish();
                        break;
                    case R.id.favorites:
                        Intent intent = new Intent(BiographyActivity.this, PoemsActivity.class);
                        intent.putExtra(PoemMenuActivity.ID, 0);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        Intent intent_setting = new Intent(BiographyActivity.this,SettingActivity.class);
                        startActivity(intent_setting);
                        break;
                    case R.id.about_us:
                        Intent intent_about_us = new Intent(BiographyActivity.this,AboutUsActivity.class);
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
