package com.malmstein.sample.tagscout.data;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation to load tasks from the data sources
 */
public class TagRepository implements TagDataSource {

    private static TagRepository INSTANCE = null;

    private final TagDataSource tagRemoteSource;
    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Integer, Tag> cachedTags;

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

    /**
     * Gets tasks from cache or remote data source, whichever is available first.
     * This is done synchronously because it's used by the {@link com.malmstein.sample.tagscout.tags.TagsLoader},
     * which implements the async mechanism.
     */
    @Override
    public List<Tag> getTags() {
        // Grab remote data if cache is empty
        List<Tag> tags = getCachedTags();
        if (cachedTags != null) {
            return tags;
        } else {
            tags = tagRemoteSource.getTags();
            for (Tag tag : tags) {
                cachedTags.put(tag.getId(), tag);
            }
            return tags;
        }

    }

    private List<Tag> getCachedTags() {
        return cachedTags == null ? null : new ArrayList<>(cachedTags.values());
    }

    /**
     * Used to force {@link #getInstance(TagDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
