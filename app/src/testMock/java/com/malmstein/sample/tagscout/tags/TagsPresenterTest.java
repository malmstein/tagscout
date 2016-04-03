package com.malmstein.sample.tagscout.tags;

import com.malmstein.sample.tagscout.data.TagDataSource;
import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.domain.TestUseCaseScheduler;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class TagsPresenterTest {

    private static List<Tag> TAGS;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagsContract.View tagsView;

    private TagsPresenter tagsPresenter;

    @Captor
    private ArgumentCaptor<TagDataSource.LoadTagsCallback> loadTagsCallbackArgumentCaptor;

    @Before
    public void setupTagsPresenter() {
        MockitoAnnotations.initMocks(this);

        givenMockTags();
        givenTagsPresenter();
    }

    private void givenTagsPresenter(){
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        RetrieveTagsUseCase retrieveTagsUseCase = new RetrieveTagsUseCase(tagRepository);

        tagsPresenter = new TagsPresenter(useCaseHandler, retrieveTagsUseCase, tagsView);
    }

    private void givenMockTags(){
        TAGS = new ArrayList<>();
        TAGS.add(new Tag(1, "text1", "color1"));
        TAGS.add(new Tag(2, "text2", "color2"));
    }

    @Test
    public void loadAllTagsFromRepositoryAndLoadIntoView(){
        // When tasks are loaded
        tagsPresenter.loadTags();

        // And there is some data
        verify(tagRepository).getTags(loadTagsCallbackArgumentCaptor.capture());
        loadTagsCallbackArgumentCaptor.getValue().onTagsLoaded(TAGS);

        // Then the view shows the proper amount of tags
        ArgumentCaptor<List> showTagsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(tagsView).showTags(showTagsArgumentCaptor.capture());
        assertTrue(showTagsArgumentCaptor.getValue().size() == 2);
    }

    @Test
    public void unavailableTagsShowsError() {
        // When tasks are loaded
        tagsPresenter.loadTags();

        // And there is some data
        verify(tagRepository).getTags(loadTagsCallbackArgumentCaptor.capture());
        loadTagsCallbackArgumentCaptor.getValue().onDataNotAvailable();

        // Then the view shows the proper amount of tags
        verify(tagsView).showLoadingTagsError();
    }

}
