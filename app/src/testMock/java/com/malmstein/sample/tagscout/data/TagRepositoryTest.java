package com.malmstein.sample.tagscout.data;

import com.malmstein.sample.tagscout.data.local.LocalTagDataSource;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

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
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class TagRepositoryTest {

    private static List<Tag> TAGS = new ArrayList<>();

    private TagRepository tagRepository;

    @Mock
    private TagDataSource tagRemoteDataSource;

    @Mock
    private LocalTagDataSource tagLocalDataSource;

    @Mock
    private TagDataSource.LoadTagsCallback loadTagsCallback;

    @Mock
    private TagsContract.View tasksView;

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
        tagRepository = TagRepository.getInstance(tagRemoteDataSource, tagLocalDataSource);

        TAGS.clear();
        TAGS.add(new Tag(1, "text1", "color1"));
        TAGS.add(new Tag(2, "text2", "color2"));
    }

    @After
    public void destroyRepositoryInstance() {
        TagRepository.destroyInstance();
    }

    @Test
    public void getTags_repositoryCachesAfterFirstApiCall() {
        // Given a setup Captor to capture callbacks
        // When two calls are issued to the tags repository
        twoTagsLoadCallsToRepository(loadTagsCallback);

        // Then tasks were only requested once from Service API
        verify(tagRemoteDataSource).getTags(any(TagDataSource.LoadTagsCallback.class));
    }

    @Test
    public void getTags_requestsAllTagsFromLocalDataSource() {
        // When tags are requested from the tasks repository
        tagRepository.getTags(loadTagsCallback);

        // Then tags are loaded from the local data source
        verify(tagLocalDataSource).getTags(any(TagDataSource.LoadTagsCallback.class));
    }

    @Test
    public void getTagsWithLocalDataSourceUnavailable_tagsAreRetrievedFromRemote() {
        // When calling getTags in the repository
        tagRepository.getTags(loadTagsCallback);

        // And the local data source has no data available
        setTagsNotAvailable(tagLocalDataSource);

        // And the remote data source has data available
        setTagsAvailable(tagRemoteDataSource, TAGS);

        // Verify the tags from the local data source are returned
        verify(loadTagsCallback).onTagsLoaded(TAGS);
    }

    @Test
    public void getTagsWithBothDataSourcesUnavailable_firesOnDataUnavailable() {
        // When calling getTasks in the repository
        tagRepository.getTags(loadTagsCallback);

        // And the local data source has no data available
        setTagsNotAvailable(tagLocalDataSource);

        // And the remote data source has no data available
        setTagsNotAvailable(tagRemoteDataSource);

        // Verify no data is returned
        verify(loadTagsCallback).onDataNotAvailable();
    }

    @Test
    public void deleteAllTags_deleteTasksToServiceAPIUpdatesCache() {
        // When all tags are deleted to the tasks repository
        tagRepository.deleteAllTags();

        // Verify the data sources were called
        verify(tagRemoteDataSource).deleteAllTags();

        // And the cache is now empty
        assertThat(tagRepository.getCachedTags().size(), is(0));
    }

    @Test
    public void selectTag_marksTagAsSelectedAndUpdatesCache() {
        // When calling getTags in the repository
        tagRepository.getTags(loadTagsCallback);

        // And the local data source has data available
        setTagsAvailable(tagLocalDataSource, TAGS);

        verify(loadTagsCallback).onTagsLoaded(TAGS);

        // When a task is selected to the tasks repository
        tagRepository.toggleTagSelection(TAGS.get(0));

        // Then the service API and persistent repository are called and the cache is updated
        verify(tagRemoteDataSource).toggleTagSelection(TAGS.get(0));
        assertThat(tagRepository.getCachedTag(TAGS.get(0).getId()).isSelected(), is(true));
    }

    @Test
    public void filterTag_returnsFilteredTags() {
        // When calling getTags in the repository
        tagRepository.getTags(loadTagsCallback);

        // And the local data source has data available
        setTagsAvailable(tagLocalDataSource, TAGS);

        verify(loadTagsCallback).onTagsLoaded(TAGS);

        // Querying will return a filtered list
        String query = "1";
        assertThat(tagRepository.filterTags(query).size(), is(1));
    }

    /**
     * Convenience method that issues two calls to the tasks repository
     */
    private void twoTagsLoadCallsToRepository(TagDataSource.LoadTagsCallback callback) {
        // When tasks are requested from repository
        tagRepository.getTags(callback); // First call to API

        // Use the Mockito Captor to capture the callback
        verify(tagLocalDataSource).getTags(tagsCallbackArgumentCaptor.capture());

        // Local data source doesn't have data yet
        tagsCallbackArgumentCaptor.getValue().onDataNotAvailable();

        // Verify the remote data source is queried
        verify(tagRemoteDataSource).getTags(tagsCallbackArgumentCaptor.capture());

        // Trigger callback so tasks are cached
        tagsCallbackArgumentCaptor.getValue().onTagsLoaded(TAGS);

        tagRepository.getTags(callback); // Second call to API
    }

    private void setTagsNotAvailable(TagDataSource dataSource) {
        verify(dataSource).getTags(tagsCallbackArgumentCaptor.capture());
        tagsCallbackArgumentCaptor.getValue().onDataNotAvailable();
    }

    private void setTagsAvailable(TagDataSource dataSource, List<Tag> tags) {
        verify(dataSource).getTags(tagsCallbackArgumentCaptor.capture());
        tagsCallbackArgumentCaptor.getValue().onTagsLoaded(tags);
    }

}
