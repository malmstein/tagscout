package com.malmstein.sample.tagscout.data;

import android.support.annotation.NonNull;

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
    public void getTags(@NonNull LoadTagsCallback callback) {

    }

}
