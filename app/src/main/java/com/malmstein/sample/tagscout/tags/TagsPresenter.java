package com.malmstein.sample.tagscout.tags;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.domain.UseCase;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;

public class TagsPresenter implements TagsContract.Presenter{

    @NonNull
    private final UseCaseHandler useCaseHandler;
    @NonNull
    private final RetrieveTagsUseCase retrieveTagsUseCase;

    public TagsPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull RetrieveTagsUseCase retrieveTagsUseCase){
        this.useCaseHandler = useCaseHandler;
        this.retrieveTagsUseCase = retrieveTagsUseCase;
    }

    @Override
    public void loadTags() {
        useCaseHandler.execute(retrieveTagsUseCase, null, new UseCase.UseCaseCallback<RetrieveTagsUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RetrieveTagsUseCase.ResponseValue response) {
                // send data to UI
            }

            @Override
            public void onError(Error error) {
                // show error on UI
            }
        });
    }
}
