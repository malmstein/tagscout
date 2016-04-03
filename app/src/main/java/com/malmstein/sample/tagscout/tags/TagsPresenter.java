package com.malmstein.sample.tagscout.tags;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.domain.UseCase;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;

public class TagsPresenter implements TagsContract.Presenter{

    @NonNull
    private final UseCaseHandler useCaseHandler;
    @NonNull
    private final GetTags getTags;

    public TagsPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull GetTags getTags){
        this.useCaseHandler = useCaseHandler;
        this.getTags = getTags;
    }

    @Override
    public void loadTags() {
        useCaseHandler.execute(getTags, null, new UseCase.UseCaseCallback<GetTags.ResponseValue>() {
            @Override
            public void onSuccess(GetTags.ResponseValue response) {
                // send data to UI
            }

            @Override
            public void onError(Error error) {
                // show error on UI
            }
        });
    }
}
