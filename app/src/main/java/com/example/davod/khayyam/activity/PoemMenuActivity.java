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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davod.khayyam.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class PoemMenuActivity extends AppCompatActivity {
    public static final String ID = "id";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_poem_menu);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupViews();
    }

    private void setupViews() {
        setupToolbar();
        setupNavigationView();
        setupButtons();

    }

    private void setupButtons() {
        Button btn1 = findViewById(R.id.btn1_poem_menu);
        Button btn2 = findViewById(R.id.btn2_poem_menu);
        Button btn3 = findViewById(R.id.btn3_poem_menu);
        Button btn4 = findViewById(R.id.btn4_poem_menu);
        Button btn5 = findViewById(R.id.btn5_poem_menu);
        Button btn6 = findViewById(R.id.btn6_poem_menu);
        ImageView imageView = findViewById(R.id.imageView_poem_menu);
        TextView textView = findViewById(R.id.txt_poem_menu);

            showTranslateAnimation(btn1, Animation.RELATIVE_TO_PARENT,1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
            showTranslateAnimation(btn2, Animation.RELATIVE_TO_PARENT,-1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
            showTranslateAnimation(btn3, Animation.RELATIVE_TO_PARENT,-1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
            showTranslateAnimation(btn4, Animation.RELATIVE_TO_PARENT,1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
        showTranslateAnimation(btn5, Animation.RELATIVE_TO_PARENT,1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);
        showTranslateAnimation(btn6, Animation.RELATIVE_TO_PARENT,-1,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0);

            showTranslateAnimation(imageView, Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.RELATIVE_TO_PARENT,-1,Animation.ABSOLUTE,0);
            showTranslateAnimation(textView, Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.RELATIVE_TO_PARENT,-1,Animation.ABSOLUTE,0);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                intent.putExtra(ID, 1);
                PoemMenuActivity.this.startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                intent.putExtra(ID, 2);
                PoemMenuActivity.this.startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                intent.putExtra(ID, 3);
                PoemMenuActivity.this.startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                intent.putExtra(ID, 4);
                PoemMenuActivity.this.startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                intent.putExtra(ID, 5);
                PoemMenuActivity.this.startActivity(intent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                intent.putExtra(ID, 6);
                PoemMenuActivity.this.startActivity(intent);
            }
        });
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view_poem_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu:
                        finish();
                        break;
                    case R.id.favorites:
                        Intent intent = new Intent(PoemMenuActivity.this, PoemsActivity.class);
                        intent.putExtra(PoemMenuActivity.ID, 0);
                        PoemMenuActivity.this.startActivity(intent);
                        break;
                    case R.id.setting:
                        Intent intent_setting = new Intent(PoemMenuActivity.this,SettingActivity.class);
                        startActivity(intent_setting);
                        break;
                    case R.id.about_us:
                        Intent intent_about_us = new Intent(PoemMenuActivity.this,AboutUsActivity.class);
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
        toolbar = findViewById(R.id.toolbar_poem_menu);
        drawerLayout = findViewById(R.id.drawer_layout_poem_menu);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
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
    public void showTranslateAnimation(View view,int fromXType, float fromXValue, int toXType, float toXValue,
                                       int fromYType, float fromYValue, int toYType, float toYValue){

        TranslateAnimation translateAnimation = new TranslateAnimation(
                                                    fromXType,fromXValue,toXType,toXValue,fromYType,fromYValue,toYType,toYValue);
        translateAnimation.setFillAfter(true);
        if (view instanceof Button || view instanceof ImageView){
            translateAnimation.setDuration(1000);
            translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        }else{
            translateAnimation.setDuration(1300);
            translateAnimation.setInterpolator(new BounceInterpolator());
        }
        view.startAnimation(translateAnimation);

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
                Intent intent = new Intent(PoemMenuActivity.this,SearchActivity.class);
                startActivity(intent);
                Bundle bundle = new Bundle();
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
