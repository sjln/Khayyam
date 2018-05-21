package com.example.davod.khayyam.saveData;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.davod.khayyam.dataModel.DataModelSetting;

/**
 * Created by DaVoD on 2/8/2018.
 */

public class SettingSharedPrefManager {

    public static final String SETTING_SHARED_PREF = "setting_shared_pref";
    public static final String FONT_NAME = "font_name";
    public static final String FONT_SIZE = "font_size";

    private SharedPreferences sharedPreferences;

    public SettingSharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SETTING_SHARED_PREF,Context.MODE_PRIVATE);
    }

    public void saveSetting(DataModelSetting setting){
        if (setting != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(FONT_NAME,setting.getFont());
            editor.putInt(FONT_SIZE,setting.getFontSize());
            editor.apply();
        }
    }

    public DataModelSetting getSetting(){
        DataModelSetting setting = new DataModelSetting();
        setting.setFont((byte) sharedPreferences.getInt(FONT_NAME,DataModelSetting.FONT_NASTALIGH));
        setting.setFontSize(sharedPreferences.getInt(FONT_SIZE,26));
        return setting;
    }
}
