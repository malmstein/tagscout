package com.malmstein.sample.tagscout.injection;

import android.content.Context;

import com.malmstein.sample.tagscout.data.TagRepository;
import com.malmstein.sample.tagscout.data.local.LocalTagDataSource;
import com.malmstein.sample.tagscout.data.remote.RemoteTagDataSource;
import com.malmstein.sample.tagscout.domain.UseCaseHandler;
import com.malmstein.sample.tagscout.tags.domain.FilterTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.RetrieveTagsUseCase;
import com.malmstein.sample.tagscout.tags.domain.SelectTagUseCase;

/**
 * Enables injection of mock implementations for
 * {@link com.malmstein.sample.tagscout.data.TagDataSource} at compile time.
 * This is useful for testing, since it allows us to use a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    private static TagRepository provideTagRepository(Context context) {
        return TagRepository.getInstance(
                RemoteTagDataSource.getInstance(),
                LocalTagDataSource.getInstance(context)
        );
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static RetrieveTagsUseCase provideRetrieveTagsUseCase(Context context) {
        return new RetrieveTagsUseCase(provideTagRepository(context));
    }

    public static SelectTagUseCase provideSelectTagUseCase(Context context) {
        return new SelectTagUseCase(provideTagRepository(context));
    }

    public static FilterTagsUseCase provideFilterTagsUseCase(Context context) {
        return new FilterTagsUseCase(provideTagRepository(context));
    }
}
