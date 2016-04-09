package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;

import com.malmstein.sample.tagscout.data.model.Tag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.when;

public class TagFilterDisplayerTest {

    TagFilterDisplayer displayer;

    @Mock
    Context context;

    @Mock
    UnitConversor unitConversor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(unitConversor.dipToPx(any(Context.class), anyFloat())).thenReturn(1);
        displayer = new TagFilterDisplayer(context, unitConversor);
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
}
