package com.malmstein.sample.tagscout.tags.domain;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public class TagsContract {

    public interface Presenter {

        void loadTags();

        void markAsSelected(Tag tag);
    }

    public interface View {

        void showTags(List<Tag> tags);

        void showLoadingTagsError();

    }
}
