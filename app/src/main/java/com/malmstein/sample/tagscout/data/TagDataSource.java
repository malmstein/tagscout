package com.malmstein.sample.tagscout.data;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public interface TagDataSource {

    interface LoadTagsCallback {

        void onTagsLoaded(List<Tag> tags);

        void onDataNotAvailable();
    }

    void getTags(LoadTagsCallback callback);

    void filterTags(String query, LoadTagsCallback callback);

    void deleteAllTags();

    void toggleTagSelection(Tag tag);

}
