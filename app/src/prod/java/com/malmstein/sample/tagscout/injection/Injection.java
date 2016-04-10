package com.malmstein.sample.tagscout.injection;

import com.malmstein.sample.tagscout.data.RemoteTagDataSource;
import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;
import com.malmstein.sample.tagscout.tags.domain.RetrieveTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.SelectTagUseCase;
import com.malmstein.sample.tagscout.tags.domain.FilterTagsUseCase;

/**
 * Enables injection of mock implementations for
 * {@link com.malmstein.sample.tagscout.data.TagDataSource} at compile time.
 * This is useful for testing, since it allows us to use a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static TagRepository provideTagRepository() {
        return TagRepository.getInstance(RemoteTagDataSource.getInstance());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static RetrieveTagsUseCase provideRetrieveTagsUseCase() {
        return new RetrieveTagsUseCase(Injection.provideTagRepository());
    }

    public static SelectTagUseCase provideSelectTagUseCase() {
        return new SelectTagUseCase(Injection.provideTagRepository());
    }

    public static FilterTagsUseCase provideFilterTagsUseCase() {
        return new FilterTagsUseCase(Injection.provideTagRepository());
    }
}
