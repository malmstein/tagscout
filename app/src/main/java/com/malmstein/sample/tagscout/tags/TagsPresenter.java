package com.malmstein.sample.tagscout.tags;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.domain.UseCase;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;

public class TagsPresenter implements TagsContract.Presenter{

    @NonNull
    private final UseCaseHandler useCaseHandler;
    @NonNull
    private final RetrieveTagsUseCase retrieveTagsUseCase;
    @NonNull
    private final TagsContract.View tagsView;

    public TagsPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull RetrieveTagsUseCase retrieveTagsUseCase, @NonNull TagsContract.View tagsView){
        this.useCaseHandler = useCaseHandler;
        this.retrieveTagsUseCase = retrieveTagsUseCase;
        this.tagsView = tagsView;
    }

    @Override
    public void loadTags() {
        useCaseHandler.execute(retrieveTagsUseCase, null, new UseCase.UseCaseCallback<RetrieveTagsUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RetrieveTagsUseCase.ResponseValue response) {
                tagsView.showTags(response.getTags());
            }

            @Override
            public void onError(Error error) {
                tagsView.showLoadingTagsError();
            }
        });
    }
}
