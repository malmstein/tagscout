package com.malmstein.sample.tagscout.tags.view;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TagFilterDisplayer {

    private int viewWidth;
    private int spaceUsed;

    private List<Tag> tags = new ArrayList<>();

    public void setWidth(int width) {
        viewWidth = width;
    }

    public void setSpaceUsed(int total){
        spaceUsed = total;
    }

    public void addToSpaceUsed(float total){
        spaceUsed += total;
    }

    public boolean fitsInSameRow(float tagWidth) {
        return viewWidth > spaceUsed + tagWidth;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        sortAlphabetically();
    }

    public void removeTag(Tag tag) {
        int index = findTagIndex(tag);
        if (index > -1) {
            tags.remove(index);
            sortAlphabetically();
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

    private void sortAlphabetically() {
        Collections.sort(tags, new Comparator<Tag>() {
            @Override
            public int compare(final Tag object1, final Tag object2) {
                return object1.getTag().compareTo(object2.getTag());
            }
        });
    }

}
