package com.example.davod.khayyam.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.dataModel.DataModelSetting;
import com.example.davod.khayyam.saveData.SettingSharedPrefManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class SettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SettingSharedPrefManager prefManager;
   private DataModelSetting setting = new DataModelSetting();
   private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_setting);

        setupView();
    }

    private void setupView() {
        setupToolbar();
        setupNavigationView();
        setupSeekBar();
        setupRadio();

    }

    private void setupSeekBar() {
        SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView size = findViewById(R.id.txt_size);
        test = findViewById(R.id.test);


        final int min = 16, max = 26, step = 2;
        seekBar.setMax((max-min)/step);

        prefManager = new SettingSharedPrefManager(this);
        setting = prefManager.getSetting();
        seekBar.setProgress((setting.getFontSize()-min)/2);
        size.setText(String.valueOf(setting.getFontSize()));
        test.setTextSize(setting.getFontSize());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = min + (progress * step);
                size.setText(String.valueOf(value));
                test.setTextSize((progress*2)+min);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setting.setFontSize((seekBar.getProgress()*2)+min);
            }
        });


    }

    private void setupRadio() {
        RadioGroup group = findViewById(R.id.radioGroup);
        RadioButton nastaligh = findViewById(R.id.radioButton1);
        RadioButton shekaste = findViewById(R.id.radioButton2);
        Button button = findViewById(R.id.btn_done_setting);

        prefManager = new SettingSharedPrefManager(this);
        setting = prefManager.getSetting();

        byte font = setting.getFont();
        switch (font){
            case DataModelSetting.FONT_NASTALIGH:
                nastaligh.setChecked(true);
                test.setTypeface(Typeface.createFromAsset(getAssets(),"font/nastaligh.ttf"));
                break;
            case DataModelSetting.FONT_SHEKASTE:
                shekaste.setChecked(true);
                test.setTypeface(Typeface.createFromAsset(getAssets(),"font/shekaste.ttf"));
                break;
        }

       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.radioButton1:
                        setting.setFont(DataModelSetting.FONT_NASTALIGH);
                        test.setTypeface(Typeface.createFromAsset(getAssets(),"font/nastaligh.ttf"));
                       break;
                   case R.id.radioButton2:
                       setting.setFont(DataModelSetting.FONT_SHEKASTE);
                       test.setTypeface(Typeface.createFromAsset(getAssets(),"font/shekaste.ttf"));
                       break;
               }
           }
       });

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                prefManager.saveSetting(setting);
                showScaleAnimation(test);
               Toast.makeText(SettingActivity.this, "تنظیمات ذخیره شد", Toast.LENGTH_SHORT).show();
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
        navigationView = findViewById(R.id.navigation_view_setting);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_menu:
                        finish();
                        break;
                    case R.id.favorites:
                        Intent intent = new Intent(SettingActivity.this,PoemsActivity.class);
                        intent.putExtra(PoemMenuActivity.ID,0);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.about_us:
                        Intent intent_about_us = new Intent(SettingActivity.this,AboutUsActivity.class);
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
        toolbar = findViewById(R.id.toolbar_setting);
        drawerLayout = findViewById(R.id.drawer_layout_setting);
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

    public void showScaleAnimation(View view){
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.5f,1.0f,1.5f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.startAnimation(scaleAnimation);
    }
}
