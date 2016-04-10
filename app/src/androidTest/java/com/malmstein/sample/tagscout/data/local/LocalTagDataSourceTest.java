package com.malmstein.sample.tagscout.data.local;

import android.test.suitebuilder.annotation.LargeTest;

import com.malmstein.sample.tagscout.data.local.LocalTagDataSource;
import com.malmstein.sample.tagscout.data.local.TagDbHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Integration test for the {@link com.malmstein.sample.tagscout.data.TagDataSource}, which uses the {@link TagDbHelper}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocalTagDataSourceTest {

    private TasksLocalDataSource localDataSource;

    @Before
    public void setUp() {
        localDataSource = LocalTagDataSource.getInstance(
                InstrumentationRegistry.getTargetContext());
    }

    @After
    public void cleanUp() {
        localDataSource.deleteAllTags();
    }

    @Test
    public void saveTask_retrievesTask() {

    }
}
