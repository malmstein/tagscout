package com.malmstein.sample.tagscout.tags.domain;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public class TagsContract {

    public interface Presenter {

        void loadTags();

        void select(Tag tag);

        void unSelect(Tag tag);

    }

    public interface View {

        void showTags(List<Tag> tags);

        void showLoadingTagsError();

    }

    public interface ContainerView {

        void addTag(Tag tag);

        void removeTag(Tag tag);

    }

}
