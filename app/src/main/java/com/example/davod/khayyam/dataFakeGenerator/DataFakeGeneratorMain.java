package com.example.davod.khayyam.dataFakeGenerator;

import android.content.Context;

import com.example.davod.khayyam.R;
import com.example.davod.khayyam.dataModel.DataModelMain;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaVoD on 1/8/2018.
 */

public class DataFakeGeneratorMain {

    public static List<DataModelMain> getData(Context context){
        List<DataModelMain> models = new ArrayList<>();

        for (int i = 0; i <=3 ; i++) {
            DataModelMain model = new DataModelMain();
            switch (i){
                case 0:
                    model.setTitle(context.getString(R.string.poems_of_khayyam));
                    model.setImage_dir(R.drawable.poems);
                    break;
                case 1:
                    model.setTitle(context.getString(R.string.biography_of_khayyam));
                    model.setImage_dir(R.drawable.biography);
                    break;
                case 2:
                    model.setTitle(context.getString(R.string.result_of_khayyam));
                    model.setImage_dir(R.drawable.result);
                    break;
                case 3:
                    model.setTitle(context.getString(R.string.search));
                    model.setImage_dir(R.drawable.search_fantacy);
                    break;
            }
            models.add(model);
        }
        return models;
    }
}
