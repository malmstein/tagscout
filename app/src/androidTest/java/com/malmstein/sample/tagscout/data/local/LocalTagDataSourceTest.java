package com.malmstein.sample.tagscout.data.local;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.malmstein.sample.tagscout.data.TagDataSource;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Integration test for the {@link com.malmstein.sample.tagscout.data.TagDataSource}, which uses the {@link TagDbHelper}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocalTagDataSourceTest {

    private LocalTagDataSource localDataSource;

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
    public void saveTags_retrievesTags() {
        // Given two tags
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "text1", "color1", true));
        tags.add(new Tag(2, "text2", "color2", false));

        // When saved into the persistent repository
        localDataSource.saveTags(tags);

        // Then the tags can be retrieved from the persistent repository
        localDataSource.getTags(new TagDataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                assertThat(tags.size(), is(2));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void toggleUnselectedTag_retrievedTagIsSelected() {
        // Given two tags unselected
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "text1", "color1", false));
        tags.add(new Tag(2, "text2", "color2", false));

        // When saved into the persistent repository
        localDataSource.saveTags(tags);

        // When completed in the persistent repository
        localDataSource.toggleTagSelection(tags.get(0));

        // Then the tags can be retrieved from the persistent repository
        localDataSource.getTags(new TagDataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                assertThat(tags.get(0).isSelected(), is(true));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void toggleSelectedTag_retrievedTagIsNotSelected() {
        // Given two tags unselected
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "text1", "color1", true));
        tags.add(new Tag(2, "text2", "color2", false));

        // When saved into the persistent repository
        localDataSource.saveTags(tags);

        // When completed in the persistent repository
        localDataSource.toggleTagSelection(tags.get(0));

        // Then the tags can be retrieved from the persistent repository
        localDataSource.getTags(new TagDataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                assertThat(tags.get(0).isSelected(), is(false));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }
}
