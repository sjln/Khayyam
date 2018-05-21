package com.example.davod.khayyam.saveData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.davod.khayyam.dataModel.DataModelPoems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaVoD on 1/22/2018.
 */

public class KhayyamDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_khayiam";
    public static final int DATABASE_VERSION = 1;
    public static final String POEMS_TABLE_NAME = "table_poems";

    public static final String COL_NUMBER_OF_POEM = "col_number_of_poem";
    public static final String COL_TITLE_COVER = "col_title_cover";
    public static final String COL_UNDER_TITLE_COVER = "col_under_title_cover";
    public static final String COL_IMAGE_DIR = "image_dir";
    public static final String COL_IS_FAVORITE = "col_is_favorite";
    public static final String COL_ID = "col_id";
    public static final String COL_POEM_ID = "col_poem_id";

    public static final String SQL_COMMAND_CREATE_FAVORITES_TABLE = "CREATE TABLE IF NOT EXISTS "+ POEMS_TABLE_NAME +" ("+
            COL_ID+" INTEGER PRIMARY KEY, "+
            COL_TITLE_COVER+" TEXT, "+
            COL_UNDER_TITLE_COVER+ " TEXT, "+
            COL_NUMBER_OF_POEM+" INTEGER, "+
            COL_IMAGE_DIR+" INTEGER, "+
            COL_IS_FAVORITE+" INTEGER DEFAULT 0, "+
            COL_POEM_ID+" INTEGER );";

    private Context context;

    public KhayyamDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_COMMAND_CREATE_FAVORITES_TABLE);

        }catch (SQLException e){
            Log.e("TAG", "onCreate: sql error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPoem(DataModelPoems model){
        ContentValues cv = new ContentValues();
        cv.put(COL_ID,model.getId());
        cv.put(COL_TITLE_COVER,model.getTitle_cover());
        cv.put(COL_UNDER_TITLE_COVER,model.getUnder_title_cover());
        cv.put(COL_NUMBER_OF_POEM,model.getNumber_of_poem());
        cv.put(COL_IMAGE_DIR,model.getImage_dir());
        cv.put(COL_IS_FAVORITE,model.isFavorite());
        cv.put(COL_POEM_ID,model.getPoem_id());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(POEMS_TABLE_NAME,null,cv);

    }

    public void addPoems(List<DataModelPoems> models){
        for (int i = 0; i < models.size(); i++) {
            addPoem(models.get(i));
        }
    }

    public List<DataModelPoems> getPoems(){
        List<DataModelPoems> models = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
       Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ POEMS_TABLE_NAME,null);
       cursor.moveToFirst();
       if (cursor.getCount()>0){
           while (!cursor.isAfterLast()){
               DataModelPoems model = new DataModelPoems();
               model.setId(cursor.getInt(0));
               model.setTitle_cover(cursor.getString(1));
               model.setUnder_title_cover(cursor.getString(2));
               model.setNumber_of_poem(cursor.getInt(3));
               model.setImage_dir(cursor.getInt(4));
               model.setIs_favorite(cursor.getInt(5));
               model.setPoem_id(cursor.getInt(6));
               models.add(model);
               cursor.moveToNext();
           }
       }
        cursor.close();
       sqLiteDatabase.close();
        return models;
    }

    public void setPoemsIsFavorite(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_FAVORITE,1);
        sqLiteDatabase.update(POEMS_TABLE_NAME,contentValues,COL_ID+" = ?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
    public int getPoemIsFavorite(int id){
        int a = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+POEMS_TABLE_NAME+" WHERE "+COL_ID +" = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            a = cursor.getInt(5);
        }
        cursor.close();
        sqLiteDatabase.close();
        return a;
    }

    public void deletePoem(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(POEMS_TABLE_NAME,COL_ID+" = ?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}
