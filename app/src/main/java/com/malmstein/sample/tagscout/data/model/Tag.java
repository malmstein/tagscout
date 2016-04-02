package com.malmstein.sample.tagscout.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Tag {

    private int id;
    private String text;
    private String color;

    /**
     * Use this constructor to create a new Tag.
     *
     * @param id
     * @param text
     * @param color
     */
    public Tag(@NonNull int id, @NonNull String text, @Nullable String color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }
}
