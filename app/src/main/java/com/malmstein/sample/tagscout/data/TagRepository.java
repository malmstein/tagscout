package com.malmstein.sample.tagscout.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.malmstein.sample.tagscout.data.local.LocalTagDataSource;
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
    private final LocalTagDataSource tagLocalSource;

    private Map<Integer, Tag> cachedTags;

    private String savedFilter;

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tagRemoteDataSource the backend data source
     * @return the {@link TagRepository} instance
     */
    public static TagRepository getInstance(TagDataSource tagRemoteDataSource, LocalTagDataSource tagLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TagRepository(tagRemoteDataSource, tagLocalDataSource);
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TagRepository(@NonNull TagDataSource tagRemoteDataSource, @NonNull LocalTagDataSource tagLocalDataSource) {
        tagRemoteSource = tagRemoteDataSource;
        tagLocalSource = tagLocalDataSource;
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
            tagLocalSource.getTags(new LoadTagsCallback() {
                @Override
                public void onTagsLoaded(List<Tag> tags) {
                    refreshCache(tags);
                    callback.onTagsLoaded(new ArrayList<>(cachedTags.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getTagsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void getTagsFromRemoteDataSource(@NonNull final TagDataSource.LoadTagsCallback callback) {
        tagRemoteSource.getTags(new TagDataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                refreshCache(tags);
                refreshLocalDataSource(tags);
                callback.onTagsLoaded(new ArrayList<>(cachedTags.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void filterTags(String query, final LoadTagsCallback callback) {
        savedFilter = query;
        List<Tag> filteredTags = filterTags(query);
        callback.onTagsLoaded(filteredTags);
    }

    protected List<Tag> filterTags(String query) {
        List<Tag> tags = cachedTags == null ? new ArrayList<Tag>() : new ArrayList<>(cachedTags.values());
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
        tagLocalSource.toggleTagSelection(tag);

        Tag toggleTag = new Tag(tag.getId(), tag.getTag(), tag.getColor(), !tag.isSelected());
        if (cachedTags == null) {
            cachedTags = new LinkedHashMap<>();
        }
        cachedTags.put(tag.getId(), toggleTag);
    }

    private void refreshCache(List<Tag> tags) {
        cleanCache();
        for (Tag tag : tags) {
            cachedTags.put(tag.getId(), tag);
        }
    }

    private void refreshLocalDataSource(List<Tag> tags) {
        tagLocalSource.deleteAllTags();
        tagLocalSource.saveTags(tags);
    }

    public List<Tag> getCachedTags() {
        if (savedFilter != null) {
            return filterTags(savedFilter);
        } else {
            return cachedTags == null ? null : new ArrayList<>(cachedTags.values());
        }
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
     * Used to force {@link #(TagDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
