package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagFilterDisplayer {

    public static final int DEFAULT_LINE_MARGIN = 8;
    public static final int DEFAULT_TAG_MARGIN = 8;
    public static final float DEFAULT_INNER_PADDING = 10;
    public static final float LAYOUT_WIDTH_OFFSET = 2;

    public static final float DEFAULT_TAG_RADIUS = 10;

    private int defaultPadding;
    private int lineMargin;
    private int tagMargin;
    private int offset;

    private int viewWidth;

    private UnitConversor unitConversor;
    private List<Tag> tags = new ArrayList<>();

    public TagFilterDisplayer(Context context, UnitConversor unitConversor) {
        this.unitConversor = unitConversor;
        defaultPadding = unitConversor.dipToPx(context, DEFAULT_INNER_PADDING);
        lineMargin = unitConversor.dipToPx(context, DEFAULT_LINE_MARGIN);
        tagMargin = unitConversor.dipToPx(context, DEFAULT_TAG_MARGIN);
        offset = unitConversor.dipToPx(context, LAYOUT_WIDTH_OFFSET);
    }

    public void drawTags() {

    }

    public void setWidth(int width) {
        viewWidth = width;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        int index = findTagIndex(tag);
        if (index > -1) {
            tags.remove(index);
        }
    }

    private int findTagIndex(Tag tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId() == (tag.getId())) {
                return i;
            }
        }
        return -1;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public interface Listener {

    }

}
