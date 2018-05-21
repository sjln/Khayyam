package com.example.davod.khayyam.dataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DaVoD on 1/10/2018.
 */

public class DataModelPoems implements Parcelable {
    private String title_cover;
    private String under_title_cover;
    private String Text="";
    private int image_dir;
    private int number_of_poem;
    private int is_favorite=0;
    private int id;
    private int poem_id;

    public DataModelPoems() {
    }

    public String getTitle_cover() {
        return title_cover;
    }

    public void setTitle_cover(String title_cover) {
        this.title_cover = title_cover;
    }

    public int getImage_dir() {
        return image_dir;
    }

    public void setImage_dir(int image_dir) {
        this.image_dir = image_dir;
    }

    public int getNumber_of_poem() {
        return number_of_poem;
    }

    public void setNumber_of_poem(int number_of_poem) {
        this.number_of_poem = number_of_poem;
    }

    public String getUnder_title_cover() {
        return under_title_cover;
    }

    public void setUnder_title_cover(String under_title_cover) {
        this.under_title_cover = under_title_cover;
    }

    public int isFavorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getPoem_id() {
        return poem_id;
    }

    public void setPoem_id(int poem_id) {
        this.poem_id = poem_id;
    }

    protected DataModelPoems(Parcel in) {
        title_cover = in.readString();
        under_title_cover = in.readString();
        Text = in.readString();
        image_dir = in.readInt();
        number_of_poem = in.readInt();
        is_favorite = in.readInt();
        id = in.readInt();
        poem_id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title_cover);
        dest.writeString(under_title_cover);
        dest.writeString(Text);
        dest.writeInt(image_dir);
        dest.writeInt(number_of_poem);
        dest.writeInt(is_favorite);
        dest.writeInt(id);
        dest.writeInt(poem_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataModelPoems> CREATOR = new Parcelable.Creator<DataModelPoems>() {
        @Override
        public DataModelPoems createFromParcel(Parcel in) {
            return new DataModelPoems(in);
        }

        @Override
        public DataModelPoems[] newArray(int size) {
            return new DataModelPoems[size];
        }
    };
}