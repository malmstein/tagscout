package com.malmstein.sample.tagscout.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Tag {

    private int id;
    private String tag;
    private String color;

    /**
     * Use this constructor to create a new Tag.
     *
     * @param id
     * @param tag
     * @param color
     */
    public Tag(@NonNull int id, @NonNull String tag, @Nullable String color) {
        this.id = id;
        this.tag = tag;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getColor() {
        return color;
    }
}
