package com.malmstein.sample.tagscout.data;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Integration test for the {@link FakeTagRemoteDataSource}}.
 */
public class FakeTagRemoteDataSourceTest {

    FakeTagRemoteDataSource fakeTagRemoteDataSource;

    @Before
    public void setup() {
        fakeTagRemoteDataSource = FakeTagRemoteDataSource.getInstance();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(fakeTagRemoteDataSource);
    }

    @Test
    public void getTags_retrievesLocalTags() {
        fakeTagRemoteDataSource.getTags(new TagDataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                assertNotNull(tags);
                assertEquals(50, tags.size());
            }

            @Override
            public void onDataNotAvailable() {
                fail();
            }
        });

    }

}
