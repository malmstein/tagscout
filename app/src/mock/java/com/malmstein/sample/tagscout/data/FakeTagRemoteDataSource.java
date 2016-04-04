package com.malmstein.sample.tagscout.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FakeTagRemoteDataSource implements TagDataSource {

    private static FakeTagRemoteDataSource INSTANCE;
    private final Gson gson;

    private List<Tag> tags;

    public static FakeTagRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTagRemoteDataSource(new Gson());
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private FakeTagRemoteDataSource(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void getTags(LoadTagsCallback callback) {
        Type listType = new TypeToken<ArrayList<Tag>>() {
        }.getType();
        try {
            tags = gson.fromJson(FakeJson.tags, listType);
            callback.onTagsLoaded(tags);
        } catch (Exception e) {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void deleteAllTags() {
        if (tags != null) {
            tags.clear();
        }
    }

    @Override
    public void selectTag(Tag tag) {
        Tag selectedTag = new Tag(tag.getId(), tag.getTag(), tag.getColor(), true);
        replaceTag(tag, selectedTag);
    }

    private void replaceTag(Tag tag, Tag selectedTag) {
        int index = Integer.MAX_VALUE;
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId() == selectedTag.getId()) {
                index = i;
            }
        }
        if (index < Integer.MAX_VALUE) {
            tags.set(index, tag);
        }
    }

}
