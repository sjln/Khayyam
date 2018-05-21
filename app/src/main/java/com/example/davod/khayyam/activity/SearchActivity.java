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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.davod.khayyam.PoemHolder;
import com.example.davod.khayyam.R;
import com.example.davod.khayyam.adapter.RecyclerViewAdapterPoems;
import com.example.davod.khayyam.adapter.ViewPagerAdapterSinglePoem;
import com.example.davod.khayyam.dataFakeGenerator.DataFakeGeneratorPoems;
import com.example.davod.khayyam.dataModel.DataModelPoems;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<DataModelPoems> models;
    RecyclerViewAdapterPoems adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFont();
        setContentView(R.layout.activity_search);

        setupViews();
    }

    private void setupViews() {
        setupToolbar();
        setupNavigationView();
        setupSearch();
    }

    private void setupSearch() {
        final EditText editText = findViewById(R.id.editText_search);
        final Button button = findViewById(R.id.btn_search);

        final String[] text = new String[1];

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text[0] = String.valueOf(s);
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    button.callOnClick();
                    return true;
                }else
                  return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int l, n;
                if (editText.getText().length() == 0) {
                    Toast.makeText(SearchActivity.this, "لطفا متنی وارد کنید", Toast.LENGTH_SHORT).show();
                } else {
                    models = new ArrayList<>();
                    for (int i = 1; i <= 4; i++) {
                        switch (i) {
                            case 1:
                                l = DataFakeGeneratorPoems.GROUP1;
                                n = 0;
                                break;
                            case 2:
                                l = DataFakeGeneratorPoems.GROUP2;
                                n = DataFakeGeneratorPoems.GROUP1;
                                break;
                            case 3:
                                l = DataFakeGeneratorPoems.GROUP3;
                                n =DataFakeGeneratorPoems.GROUP1 + DataFakeGeneratorPoems.GROUP2;
                                break;
                            case 4:
                                l = DataFakeGeneratorPoems.GROUP4;
                                n = DataFakeGeneratorPoems.GROUP1 + DataFakeGeneratorPoems.GROUP2 + DataFakeGeneratorPoems.GROUP3;
                                break;
                            case 5:
                                l = DataFakeGeneratorPoems.GROUP5;
                                n = DataFakeGeneratorPoems.GROUP1 + DataFakeGeneratorPoems.GROUP2 + DataFakeGeneratorPoems.GROUP3+ DataFakeGeneratorPoems.GROUP4;
                                break;
                            case 6:
                                l = DataFakeGeneratorPoems.GROUP6;
                                n = DataFakeGeneratorPoems.GROUP1 + DataFakeGeneratorPoems.GROUP2 + DataFakeGeneratorPoems.GROUP3+ DataFakeGeneratorPoems.GROUP4+ DataFakeGeneratorPoems.GROUP5;
                                break;
                            default:
                                l = 0;
                                n = 0;
                                break;
                        }
                        for (int j = 1; j <= l; j++) {
                            String[] poem = PoemHolder.getPoem(i, j);
                            for (int k = 0; k <= 3; k++) {
                                if (poem[k].contains(text[0])) {
                                    DataModelPoems model = new DataModelPoems();
                                    model.setText(poem[k]);
                                    model.setTitle_cover(" رباعی" + (j + n));
                                    model.setId(j + n);
                                    model.setNumber_of_poem(j);
                                    model.setUnder_title_cover(poem[0]);
                                    model.setImage_dir(R.drawable.cover_list);
                                    model.setPoem_id(i);
                                    models.add(model);
                                    setupRecyclerView();
                                }
                            }
                        }

                    }
                    if (models.isEmpty()) {
                        Toast.makeText(SearchActivity.this, "چیزی پیدا نشد", Toast.LENGTH_SHORT).show();
                        models.clear();
                        setupRecyclerView();
                    }
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    ViewPagerAdapterSinglePoem.setModels(models);
                }
            }
        });

    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_search);
        adapter = new RecyclerViewAdapterPoems(this, models,-1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view_search);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu:
                        Intent mainIntent = new Intent(SearchActivity.this,MainActivity.class);
                        startActivity(mainIntent);
                        break;
                    case R.id.favorites:
                        Intent intent = new Intent(SearchActivity.this,PoemsActivity.class);
                        intent.putExtra(PoemMenuActivity.ID,0);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        Intent intent_setting = new Intent(SearchActivity.this,SettingActivity.class);
                        startActivity(intent_setting);
                        break;
                    case R.id.about_us:
                        Intent intent_about_us = new Intent(SearchActivity.this,AboutUsActivity.class);
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
        toolbar = findViewById(R.id.toolbar_search);
        drawerLayout = findViewById(R.id.drawer_layout_search);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };
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

