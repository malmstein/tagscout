package com.malmstein.sample.tagscout.data;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagRepositoryTest {

    private static List<Tag> TAGS = new ArrayList<>();

    private TagRepository tagRepository;

    @Mock
    private TagDataSource tagRemoteDataSource;

    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        tagRepository = TagRepository.getInstance(tagRemoteDataSource);

        // Set two sample Tags
        TAGS.add(new Tag(1, "text1", "color1"));
        TAGS.add(new Tag(2, "text2", "color2"));
    }

    @After
    public void destroyRepositoryInstance() {
        TagRepository.destroyInstance();
    }

    @Test
    public void getTasks_requestsAllTasksFromRemoteDataSource() {
        // When tasks are requested from the tasks repository
        tagRepository.getTags();

        // Then tags are loaded from the remote data source
        verify(tagRemoteDataSource).getTags();
    }

    @Test
    public void getTasksWithRemoteDataSourceUnavailable_noDataIsAvailable() {
        // When the remote data source has no data available
        when(tagRemoteDataSource.getTags()).thenReturn(null);

        // When calling getTags in the repository
        List<Tag> returnedTags = tagRepository.getTags();

        // Then the returned tags are null
        assertNull(returnedTags);
    }

    @Test
    public void getTasksFromRemoteDataSource_returnsListOfTags() {
        // When the remote data source data available
        when(tagRemoteDataSource.getTags()).thenReturn(TAGS);

        // When calling getTags in the repository
        List<Tag> returnedTags = tagRepository.getTags();

        // Verify the tasks from the remote data source are returned
        assertEquals(2, returnedTags.size());
    }



}
