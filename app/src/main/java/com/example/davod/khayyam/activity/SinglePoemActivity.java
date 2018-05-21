package com.example.davod.khayyam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.adapter.RecyclerViewAdapterPoems;
import com.example.davod.khayyam.adapter.ViewPagerAdapterSinglePoem;
import com.example.davod.khayyam.dataModel.DataModelPoems;
import com.example.davod.khayyam.dataModel.DataModelSetting;
import com.example.davod.khayyam.saveData.SettingSharedPrefManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SinglePoemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int id;
    private int recycler_position;
    private DataModelPoems model;
    private ViewPager viewPager;
    private String fontName;
    private SettingSharedPrefManager prefManager;
    private DataModelSetting setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_single_poem);

        Intent intent = getIntent();
        model = intent.getParcelableExtra(RecyclerViewAdapterPoems.MODEL);
        recycler_position = intent.getIntExtra(RecyclerViewAdapterPoems.RECYCLER_POSITION, 0);
        id = intent.getIntExtra(PoemMenuActivity.ID, 0);
        fontName = intent.getStringExtra("fontName");
        setupViews();
    }

    private void setupViews() {
        setupViewPager();
        setupToolbar();
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.view_pager_single_poem);
        ViewPagerAdapterSinglePoem adapter = new ViewPagerAdapterSinglePoem(getSupportFragmentManager(), id, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(recycler_position);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar_single_poem);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setupFont() {
        prefManager = new SettingSharedPrefManager(this);

        setting = prefManager.getSetting();
        byte font = setting.getFont();

        switch (font) {
            case DataModelSetting.FONT_NASTALIGH:
                fontName = "nastaligh";
                break;
            case DataModelSetting.FONT_SHEKASTE:
                fontName = "shekaste";
                break;
            default:
                fontName = "nastaligh";
                break;
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/" + fontName + ".ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
