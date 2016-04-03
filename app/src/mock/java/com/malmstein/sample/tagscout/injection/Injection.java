package com.malmstein.sample.tagscout.injection;

import com.malmstein.sample.tagscout.data.FakeTagRemoteDataSource;
import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;
import com.malmstein.sample.tagscout.tags.GetTags;

/**
 * Enables injection of mock implementations for
 * {@link com.malmstein.sample.tagscout.data.TagDataSource} at compile time.
 * This is useful for testing, since it allows us to use a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static TagRepository provideTagRepository() {
        return TagRepository.getInstance(FakeTagRemoteDataSource.getInstance());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static GetTags provideGetTags() {
        return new GetTags(Injection.provideTagRepository());
    }

}
