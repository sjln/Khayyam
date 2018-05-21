package com.example.davod.khayyam.dataModel;

/**
 * Created by DaVoD on 2/8/2018.
 */

public class DataModelSetting {

    public static final byte FONT_NASTALIGH = 0;
    public static final byte FONT_SHEKASTE = 1;

    private byte font = 0;
    private int fontSize = 26;

    public byte getFont() {
        return font;
    }

    public void setFont(byte font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
