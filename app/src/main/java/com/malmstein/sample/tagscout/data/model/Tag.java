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
    public Tag(@NonNull int id, @NonNull String tag, @NonNull String color) {
        this.id = id;
        this.tag = tag;
        this.color = "#" + color;
        this.selected = false;
    }

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
        this.color = "#" + color;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tag tag1 = (Tag) o;

        if (id != tag1.id) {
            return false;
        }
        if (selected != tag1.selected) {
            return false;
        }
        if (tag != null ? !tag.equals(tag1.tag) : tag1.tag != null) {
            return false;
        }
        return color != null ? color.equals(tag1.color) : tag1.color == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }
}
