package com.malmstein.sample.tagscout.tags;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.domain.UseCase;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;
import com.malmstein.sample.tagscout.tags.domain.RetrieveTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.SelectTagUseCase;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

public class TagsPresenter implements TagsContract.Presenter {

    @NonNull
    private final UseCaseHandler useCaseHandler;
    @NonNull
    private final RetrieveTagsUseCase retrieveTagsUseCase;
    @NonNull
    private final SelectTagUseCase selectTagUseCase;
    @NonNull
    private final TagsContract.View tagsView;
    @NonNull
    private final TagsContract.ContainerView tagsContainerView;

    public TagsPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull RetrieveTagsUseCase retrieveTagsUseCase,
                         @NonNull SelectTagUseCase selectTagUseCase, @NonNull TagsContract.View tagsView,
                         @NonNull TagsContract.ContainerView tagsContainerView) {
        this.useCaseHandler = useCaseHandler;
        this.retrieveTagsUseCase = retrieveTagsUseCase;
        this.selectTagUseCase = selectTagUseCase;
        this.tagsView = tagsView;
        this.tagsContainerView = tagsContainerView;
    }

    @Override
    public void loadTags() {
        useCaseHandler.execute(retrieveTagsUseCase, null, new UseCase.UseCaseCallback<RetrieveTagsUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RetrieveTagsUseCase.ResponseValue response) {
                tagsView.showTags(response.getTags());
                tagsContainerView.showTags(response.getTags());
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

}
