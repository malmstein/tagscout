package com.malmstein.sample.tagscout.injection;

import com.malmstein.sample.tagscout.data.RemoteTagDataSource;
import com.malmstein.sample.tagscout.data.TagRepository;

/**
 * Enables injection of mock implementations for
 * {@link com.malmstein.sample.tagscout.data.TagDataSource} at compile time.
 * This is useful for testing, since it allows us to use a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static TagRepository provideTagRepository() {
        return TagRepository.getInstance(RemoteTagDataSource.getInstance());
    }

}
