package com.malmstein.sample.tagscout.data.model;

import android.support.annotation.NonNull;

public class Tag {

    private int id;
    private String tag;
    private String color;
    private boolean selected;

    /**
     * Use this constructor to create a new Tag.
     *
     * @param id
     * @param tag
     * @param color
     */
    public Tag(@NonNull int id, @NonNull String tag, @NonNull String color, @NonNull boolean selected) {
        this.id = id;
        this.tag = tag;
        this.color = color;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelection(){
        selected = !selected;
    }
}
