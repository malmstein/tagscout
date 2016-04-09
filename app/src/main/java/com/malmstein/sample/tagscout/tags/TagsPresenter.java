package com.malmstein.sample.tagscout.tags;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.UseCase;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;
import com.malmstein.sample.tagscout.tags.domain.FilterTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.RetrieveTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.SelectTagUseCase;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

public class TagsPresenter implements TagsContract.Presenter {

    private final UseCaseHandler useCaseHandler;
    private final RetrieveTagsUseCase retrieveTagsUseCase;
    private final SelectTagUseCase selectTagUseCase;
    private final FilterTagsUseCase filterTagsUseCase;

    private final TagsContract.View tagsView;
    private final TagsContract.ContainerView tagsContainerView;

    public TagsPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull RetrieveTagsUseCase retrieveTagsUseCase,
                         @NonNull SelectTagUseCase selectTagUseCase, @NonNull FilterTagsUseCase filterTagsUseCase,
                         @NonNull TagsContract.View tagsView, @NonNull TagsContract.ContainerView tagsContainerView) {
        this.useCaseHandler = useCaseHandler;
        this.retrieveTagsUseCase = retrieveTagsUseCase;
        this.selectTagUseCase = selectTagUseCase;
        this.filterTagsUseCase = filterTagsUseCase;
        this.tagsView = tagsView;
        this.tagsContainerView = tagsContainerView;
    }

    @Override
    public void loadTags() {
        useCaseHandler.execute(retrieveTagsUseCase, null, new UseCase.UseCaseCallback<RetrieveTagsUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RetrieveTagsUseCase.ResponseValue response) {
                tagsView.showTags(response.getTags());
                tagsContainerView.showSelectedTags(response.getSelectedTags());
            }

            @Override
            public void onError(Error error) {
                tagsView.showLoadingTagsError();
            }
        });
    }

    @Override
    public void toggleTagState(final Tag tag) {
        useCaseHandler.execute(selectTagUseCase, new SelectTagUseCase.RequestValues(tag), new UseCase.UseCaseCallback<SelectTagUseCase.ResponseValue>() {
            @Override
            public void onSuccess(SelectTagUseCase.ResponseValue response) {
                tagsView.showTags(response.getTags());
                if (!tag.isSelected()) {
                    tagsContainerView.addTag(response.getToggledTag());
                } else {
                    tagsContainerView.removeTag(response.getToggledTag());
                }
            }

            @Override
            public void onError(Error error) {
                tagsView.showLoadingTagsError();
            }
        });
    }

    @Override
    public void filter(final String query) {
        useCaseHandler.execute(filterTagsUseCase, new FilterTagsUseCase.RequestValues(query), new UseCase.UseCaseCallback<FilterTagsUseCase.ResponseValue>() {
            @Override
            public void onSuccess(FilterTagsUseCase.ResponseValue response) {
                tagsView.showTags(response.getTags());
            }

            @Override
            public void onError(Error error) {
                tagsView.showLoadingTagsError();
            }
        });
    }

}
