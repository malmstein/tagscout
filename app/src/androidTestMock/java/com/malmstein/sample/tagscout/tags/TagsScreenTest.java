package com.malmstein.sample.tagscout.tags;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.malmstein.sample.tagscout.data.TagDataSource;
import com.malmstein.sample.tagscout.injection.Injection;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TagsScreenTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p/>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<TagsActivity> tagsActivityActivityTestRule =
            new ActivityTestRule<TagsActivity>(TagsActivity.class) {

                /**
                 * To avoid a long list of tasks and the need to scroll through the list to find a
                 * task, we call {@link TagDataSource#deleteAllTags()} ()} before each test.
                 */
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    // Doing this in @Before generates a race condition.
                    Injection.provideTagRepository().deleteAllTags();
                }
            };

}
