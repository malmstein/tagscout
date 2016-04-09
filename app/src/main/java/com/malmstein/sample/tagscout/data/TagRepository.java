package com.malmstein.sample.tagscout.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
     * Note: {@link com.malmstein.sample.tagscout.data.TagDataSource.LoadTagsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getTags(final TagDataSource.LoadTagsCallback callback) {
        // Grab remote data if cache is empty
        List<Tag> tags = getCachedTags();
        if (cachedTags != null && cachedTags.size() > 0) {
            callback.onTagsLoaded(tags);
        } else {
            tagRemoteSource.getTags(new LoadTagsCallback() {
                @Override
                public void onTagsLoaded(List<Tag> tags) {
                    processLoadedTags(tags, callback);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    @Override
    public void filterTags(String query, final LoadTagsCallback callback) {
        List<Tag> filteredTags = filterTags(query);
        callback.onTagsLoaded(filteredTags);
    }

    protected List<Tag> filterTags(String query) {
        List<Tag> tags = getCachedTags();
        ArrayList<Tag> filteredTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getTag().toUpperCase().contains(query.toUpperCase())) {
                filteredTags.add(tag);
            }
        }
        return filteredTags;
    }

    @Override
    public void deleteAllTags() {
        tagRemoteSource.deleteAllTags();
        if (cachedTags == null) {
            cachedTags = new LinkedHashMap<>();
        }
        cachedTags.clear();
    }

    @Override
    public void toggleTagSelection(Tag tag) {
        tagRemoteSource.toggleTagSelection(tag);

        Tag toggleTag = new Tag(tag.getId(), tag.getTag(), tag.getColor(), !tag.isSelected());
        if (cachedTags == null) {
            cachedTags = new LinkedHashMap<>();
        }
        cachedTags.put(tag.getId(), toggleTag);
    }

    private void processLoadedTags(List<Tag> tags, final LoadTagsCallback callback) {
        cleanCache();
        for (Tag tag : tags) {
            cachedTags.put(tag.getId(), tag);
        }
        callback.onTagsLoaded(new ArrayList<>(cachedTags.values()));
    }

    public List<Tag> getCachedTags() {
        return cachedTags == null ? null : new ArrayList<>(cachedTags.values());
    }

    public Tag getCachedTag(int id) {
        return cachedTags.get(id);
    }

    @VisibleForTesting
    protected void cleanCache() {
        if (cachedTags == null) {
            cachedTags = new LinkedHashMap<>();
        }
        cachedTags.clear();
    }

    /**
     * Used to force {@link #getInstance(TagDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
