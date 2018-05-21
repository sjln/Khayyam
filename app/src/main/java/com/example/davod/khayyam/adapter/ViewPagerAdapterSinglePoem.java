package com.example.davod.khayyam.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.davod.khayyam.dataFakeGenerator.DataFakeGeneratorPoems;
import com.example.davod.khayyam.dataModel.DataModelPoems;
import com.example.davod.khayyam.fragment.SinglePoemFragment;
import com.example.davod.khayyam.saveData.KhayyamDatabaseOpenHelper;

import java.util.List;

/**
 * Created by DaVoD on 1/24/2018.
 */

public class ViewPagerAdapterSinglePoem extends FragmentStatePagerAdapter {


    private int id;
    private Context context;
    private static List<DataModelPoems> models;
    private KhayyamDatabaseOpenHelper openHelper;

    public ViewPagerAdapterSinglePoem(FragmentManager fm, int id, Context context) {
        super(fm);
        this.id = id;
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        if (id>=0)
          models = DataFakeGeneratorPoems.getData(id,context);
         return SinglePoemFragment.newInstance(id,models.get(position));
    }

    @Override
    public int getCount() {
        switch (id) {
            case 1:
                return DataFakeGeneratorPoems.GROUP1;
            case 2:
                return DataFakeGeneratorPoems.GROUP2;
            case 3:
                return DataFakeGeneratorPoems.GROUP3;
            case 4:
                return DataFakeGeneratorPoems.GROUP4;
            case 5:
                return DataFakeGeneratorPoems.GROUP5;
            case 6:
                return DataFakeGeneratorPoems.GROUP6;
            case -1:
                return models.size();
            default:
                openHelper = new KhayyamDatabaseOpenHelper(context);
                return openHelper.getPoems().size();

        }
    }

    public static void setModels(List<DataModelPoems> models1) {
        models= models1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
