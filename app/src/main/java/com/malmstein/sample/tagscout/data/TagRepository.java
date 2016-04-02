package com.malmstein.sample.tagscout.data;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

/**
 * Concrete implementation to load tasks from the data sources
 */
public class TagRepository implements TagDataSource {

    private static TagRepository INSTANCE = null;

    private final TagDataSource tagRemoteSource;

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tagRemoteDataSource the backend data source
     * @return the {@link TagRepository} instance
     */
    public static TagRepository getInstance(TagDataSource tagRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TagRepository(tagRemoteDataSource);
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TagRepository(@NonNull TagDataSource tagRemoteDataSource) {
        tagRemoteSource = tagRemoteDataSource;
    }

    @Override
    public void getTags(@NonNull final LoadTagsCallback callback) {
        tagRemoteSource.getTags(new LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                callback.onTagsLoaded(tags);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Used to force {@link #getInstance(TagDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
