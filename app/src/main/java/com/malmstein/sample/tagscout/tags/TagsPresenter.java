package com.malmstein.sample.tagscout.tags;

import android.support.annotation.NonNull;

import com.malmstein.sample.tagscout.domain.UseCase;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;

public class TagsPresenter implements TagsContract.Presenter{

    @NonNull
    private final UseCaseHandler useCaseHandler;

    public TagsPresenter(@NonNull UseCaseHandler useCaseHandler){
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void loadTags() {
        // create get tasks use case
        useCaseHandler.execute(null, null, new UseCase.UseCaseCallback<UseCase.ResponseValue>() {
            @Override
            public void onSuccess(UseCase.ResponseValue response) {
                // send data to UI
            }

            @Override
            public void onError(Error error) {
                // show error on UI
            }
        });
    }
}
