package com.malmstein.sample.tagscout.tags;

import com.malmstein.sample.tagscout.data.TagDataSource;
import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.domain.TestUseCaseScheduler;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;
import com.malmstein.sample.tagscout.injection.Injection;
import com.malmstein.sample.tagscout.tags.domain.RetrieveTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

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

    @Mock
    private TagsContract.ContainerView tagsContainerView;

    private TagsPresenter tagsPresenter;

    @Captor
    private ArgumentCaptor<TagDataSource.LoadTagsCallback> loadTagsCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<Tag> toggleTagArgumentCaptor;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        givenMockTags();
        givenTagsPresenter();
    }

    private void givenTagsPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        RetrieveTagsUseCase retrieveTagsUseCase = new RetrieveTagsUseCase(tagRepository);

        tagsPresenter = new TagsPresenter(useCaseHandler, retrieveTagsUseCase,
                                          Injection.provideSelectTagUseCase(), tagsView,
                                          tagsContainerView
        );
    }

    private void givenMockTags() {
        TAGS = new ArrayList<>();
        TAGS.add(new Tag(1, "text1", "color1", true));
        TAGS.add(new Tag(2, "text2", "color2", false));
    }

    @Test
    public void loadAllTagsFromRepositoryAndLoadIntoView() {
        // When tasks are loaded
        tagsPresenter.loadTags();

        // And there is some data
        verify(tagRepository).getTags(loadTagsCallbackArgumentCaptor.capture());
        loadTagsCallbackArgumentCaptor.getValue().onTagsLoaded(TAGS);

        // Then the view shows the proper amount of tags
        ArgumentCaptor<List> showTagsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(tagsView).showTags(showTagsArgumentCaptor.capture());
        verify(tagsContainerView).showSelectedTags(showTagsArgumentCaptor.capture());
        assertTrue(showTagsArgumentCaptor.getAllValues().get(0).size() == 2);
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
