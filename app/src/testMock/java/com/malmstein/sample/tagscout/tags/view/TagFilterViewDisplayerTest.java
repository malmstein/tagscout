package com.malmstein.sample.tagscout.tags.view;

import com.malmstein.sample.tagscout.data.model.Tag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.*;

public class TagFilterViewDisplayerTest {

    TagFilterDisplayer displayer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        displayer = new TagFilterDisplayer();
    }

    @Test
    public void addsNewTag(){
        Tag tag = new Tag(1, "tag1", "color1", true);
        displayer.addTag(tag);
        assertEquals(1, displayer.getTags().size());
    }

    @Test
    public void removesTag() {
        Tag tag = new Tag(1, "tag1", "color1", true);
        displayer.addTag(tag);
        displayer.removeTag(tag);
        assertEquals(0, displayer.getTags().size());
    }

    @Test
    public void addsTagToSameRowWhenSpaceAvailable(){
        displayer.setWidth(1000);
        displayer.setSpaceUsed(300);
        assertTrue(displayer.fitsInSameRow(200));
    }

    @Test
    public void addsTagToNewRowWhenSpaceNotAvailable() {
        displayer.setWidth(1000);
        displayer.setSpaceUsed(800);
        assertFalse(displayer.fitsInSameRow(250));
    }

    @Test
    public void keepsAddingToSameRowUntilNoSpace() {
        displayer.setWidth(1000);

        displayer.setSpaceUsed(150);
        assertTrue(displayer.fitsInSameRow(250));

        displayer.setSpaceUsed(400);
        assertTrue(displayer.fitsInSameRow(200));

        displayer.setSpaceUsed(600);
        assertTrue(displayer.fitsInSameRow(300));

        displayer.setSpaceUsed(900);
        assertFalse(displayer.fitsInSameRow(200));
    }

    @Test
    public void addsTagToNewRowWhenExactlyFitsSpace() {
        displayer.setWidth(1000);
        displayer.setSpaceUsed(800);
        assertFalse(displayer.fitsInSameRow(200));
    }

    @Test
    public void tagsAreOrderedAlphabeticallyAfterAdding(){
        Tag awns = new Tag(1, "awns", "color1", true);
        displayer.addTag(awns);
        Tag twiddlier = new Tag(2, "twiddlier", "color1", true);
        displayer.addTag(twiddlier);
        Tag rewoven = new Tag(3, "rewoven", "color1", true);
        displayer.addTag(rewoven);

        assertEquals(displayer.getTags().get(0), awns);
        assertEquals(displayer.getTags().get(1), rewoven);
        assertEquals(displayer.getTags().get(2), twiddlier);
    }

    @Test
    public void tagsAreOrderedAlphabeticallyAfterRemoving() {
        Tag awns = new Tag(1, "awns", "color1", true);
        displayer.addTag(awns);
        Tag twiddlier = new Tag(2, "twiddlier", "color1", true);
        displayer.addTag(twiddlier);
        Tag rewoven = new Tag(3, "rewoven", "color1", true);
        displayer.addTag(rewoven);
        Tag glassblowing = new Tag(4, "glassblowing", "color1", true);
        displayer.addTag(glassblowing);

        displayer.removeTag(twiddlier);

        assertEquals(displayer.getTags().get(0), awns);
        assertEquals(displayer.getTags().get(1), glassblowing);
        assertEquals(displayer.getTags().get(2), rewoven);
    }
}
