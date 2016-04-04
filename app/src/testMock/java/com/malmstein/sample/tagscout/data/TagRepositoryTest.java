package com.malmstein.sample.tagscout.data;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class TagRepositoryTest {

    private static List<Tag> TAGS = new ArrayList<>();

    private TagRepository tagRepository;

    @Mock
    private TagDataSource tagRemoteDataSource;

    @Mock
    private TagDataSource.LoadTagsCallback loadTagsCallback;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<TagDataSource.LoadTagsCallback> tagsCallbackArgumentCaptor;

    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        tagRepository = TagRepository.getInstance(tagRemoteDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
        TagRepository.destroyInstance();
    }

    @Test
    public void getTasks_requestsAllTagsFromRemoteDataSourceWhenCacheIsEmpty() {
        // Given the cache is empty
        tagRepository.cleanCache();

        // And tags are requested from the tasks repository
        tagRepository.getTags(loadTagsCallback);

        // Then tags are loaded from the remote data source
        verify(tagRemoteDataSource).getTags(any(TagDataSource.LoadTagsCallback.class));
    }

    @Test
    public void getTasksWithRemoteDataSourceUnavailable_noDataIsAvailable() {
        // Given the cache is empty
        tagRepository.cleanCache();

        // When calling getTags in the repository
        tagRepository.getTags(loadTagsCallback);

        // And the remote data source has no data available
        verify(tagRemoteDataSource).getTags(tagsCallbackArgumentCaptor.capture());
        tagsCallbackArgumentCaptor.getValue().onDataNotAvailable();

        // Verify no data is returned
        verify(loadTagsCallback).onDataNotAvailable();
    }

    @Test
    public void getTasksFromRemoteDataSource_returnsListOfTagsAndSavesInCache() {
        // Given the cache is empty
        tagRepository.cleanCache();

        // When calling getTags in the repository
        tagRepository.getTags(loadTagsCallback);

        // And the remote data source data is  available
        TAGS.add(new Tag(1, "text1", "color1"));
        TAGS.add(new Tag(2, "text2", "color2"));
        verify(tagRemoteDataSource).getTags(tagsCallbackArgumentCaptor.capture());
        tagsCallbackArgumentCaptor.getValue().onTagsLoaded(TAGS);

        verify(loadTagsCallback).onTagsLoaded(TAGS);

        // And stored in the cache
        assertEquals(2, tagRepository.getCachedTags().size());
    }

    @Test
    public void deleteAllTags_deleteTasksToServiceAPIUpdatesCache() {
        // When all tags are deleted to the tasks repository
        tagRepository.deleteAllTags();

        // Verify the data sources were called
        verify(tagRemoteDataSource).deleteAllTags();

        // And the cache is now empty
        assertThat(tagRepository.cachedTags.size(), is(0));
    }


}
