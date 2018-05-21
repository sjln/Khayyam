package com.example.davod.khayyam.dataModel;

import android.graphics.drawable.Drawable;

/**
 * Created by DaVoD on 1/8/2018.
 */

public class DataModelMain {

    private int id;
    private int image_dir;
    private Drawable image;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage_dir() {
        return image_dir;
    }

    public void setImage_dir(int image_dir) {
        this.image_dir = image_dir;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
