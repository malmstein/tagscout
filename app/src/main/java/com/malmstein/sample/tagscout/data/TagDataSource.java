package com.malmstein.sample.tagscout.data;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public interface TagDataSource {

    interface LoadTagsCallback {

        void onTagsLoaded(List<Tag> tags);

        void onDataNotAvailable();
    }

    void getTags(@NonNull LoadTagsCallback callback);

}
