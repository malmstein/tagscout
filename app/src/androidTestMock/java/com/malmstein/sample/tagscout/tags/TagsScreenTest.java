package com.malmstein.sample.tagscout.tags;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.ListView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

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
            new ActivityTestRule<TagsActivity>(TagsActivity.class) {};

    /**
     * A custom {@link Matcher} which matches an item in a {@link ListView} by its text.
     * <p/>
     * View constraints:
     * <ul>
     * <li>View must be a child of a {@link ListView}
     * <ul>
     *
     * @param itemText the text to match
     * @return Matcher that matches text in the given view
     */
    private Matcher<View> withItemText(final String itemText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(ListView.class)),
                        withText(itemText)
                ).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

    public static Matcher<Object> withItemContent(String expectedText) {
        return withItemContent(equalTo(expectedText));
    }

    public static ViewAction swipeDown() {
        return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.TOP_CENTER,
                                      GeneralLocation.BOTTOM_CENTER, Press.FINGER
        );
    }

    @Test
    public void showAllTags_canScroll() {
        // given three specific tag check they are displayed
        onView(withItemText("awns")).check(matches(isDisplayed()));
        onView(withItemText("twiddlier")).check(matches(isDisplayed()));
        onView(withItemText("rewoven")).check(matches(isDisplayed()));

        // and the list can be scrolled
        onData(instanceOf(Tag.class))
                .inAdapterView(allOf(withId(R.id.tags_list), isDisplayed()))
                .atPosition(45)
                .check(matches(isDisplayed()));
    }

    @Test
    public void orientationChange_ShowTags() throws Exception {
        String tagText = "awns";

        // given a specific tag
        onView(withItemText(tagText)).check(matches(isDisplayed()));

        // when rotating the screen
        TestUtils.rotateOrientation(tagsActivityActivityTestRule);

        // tag is still being shown
        onView(withItemText(tagText)).check(matches(isDisplayed()));
    }

    @Test
    public void orientationChange_TagStillSelected() throws Exception {
        String tagText = "awns";

        // given a specific tag
        onView(withItemText(tagText)).perform(click());

        // when selecting the tag it becomes selected
        onView(allOf(withId(R.id.tag_selected), hasSibling(withText(tagText))))
                .check(matches(isDisplayed()));

        // when rotating the screen
        TestUtils.rotateOrientation(tagsActivityActivityTestRule);

        // and is still selected
        onView(allOf(withId(R.id.tag_selected), hasSibling(withText(tagText))))
                .check(matches(isDisplayed()));
    }
}
