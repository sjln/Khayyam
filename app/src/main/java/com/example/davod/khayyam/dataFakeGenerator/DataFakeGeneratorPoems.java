package com.example.davod.khayyam.dataFakeGenerator;

import android.content.Context;

import com.example.davod.khayyam.saveData.KhayyamDatabaseOpenHelper;
import com.example.davod.khayyam.PoemHolder;
import com.example.davod.khayyam.R;
import com.example.davod.khayyam.dataModel.DataModelPoems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaVoD on 1/10/2018.
 */

public class DataFakeGeneratorPoems {

    public static final int GROUP1 = 22;
    public static final int GROUP2 = 24;
    public static final int GROUP3 = 42;
    public static final int GROUP4 = 39;
    public static final int GROUP5 = 14;
    public static final int GROUP6 = 19;

    public static List<DataModelPoems> getData(int id, Context context) {
        List<DataModelPoems> models = new ArrayList<>();
        int j;
        if (id == 0){
            KhayyamDatabaseOpenHelper openHelper = new KhayyamDatabaseOpenHelper(context);
            models = openHelper.getPoems();

        }else{
            switch (id) {
                case 1:
                    j = GROUP1;
                    break;
                case 2:
                    j = GROUP2;
                    break;
                case 3:
                    j = GROUP3;
                    break;
                case 4:
                    j = GROUP4;
                    break;
                case 5:
                    j = GROUP5;
                    break;
                case 6:
                    j = GROUP6;
                    break;
                default:
                    j = 0;
                    break;
            }
            for (int i =1; i <= j; i++) {
                DataModelPoems model = new DataModelPoems();
                model.setImage_dir(R.drawable.cover_list);
                model.setNumber_of_poem(i);
                switch (id){
                    case 1:
                        model.setTitle_cover("رباعی " + i);
                        model.setId(i);
                        break;
                    case 2:
                        model.setTitle_cover("رباعی " + (i+GROUP1));
                        model.setId(i+GROUP1);
                        break;
                    case 3:
                        model.setTitle_cover("رباعی " + (i+GROUP1+GROUP2));
                        model.setId(i+GROUP1+GROUP2);
                        break;
                    case 4:
                        model.setTitle_cover("رباعی " + (i+GROUP1+GROUP2+GROUP3));
                        model.setId(i+GROUP1+GROUP2+GROUP3);
                        break;
                    case 5:
                        model.setTitle_cover("رباعی " + (i+GROUP1+GROUP2+GROUP3+GROUP4));
                        model.setId(i+GROUP1+GROUP2+GROUP3+GROUP4);
                        break;
                    case 6:
                        model.setTitle_cover("رباعی " + (i+GROUP1+GROUP2+GROUP3+GROUP4+GROUP5));
                        model.setId(i+GROUP1+GROUP2+GROUP3+GROUP4+GROUP5);
                        break;
                }
                String[] poem = PoemHolder.getPoem(id, model.getNumber_of_poem());
                model.setUnder_title_cover(poem[0]);
                model.setPoem_id(id);

                models.add(model);
            }
        }
        return models;
    }


}
