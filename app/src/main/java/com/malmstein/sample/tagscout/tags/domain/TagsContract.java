package com.malmstein.sample.tagscout.tags.domain;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public class TagsContract {

    public interface Presenter {

        void loadTags();

        void toggleTagState(Tag tag);

        void filter(String query);
    }

    public interface View {

        void showTags(List<Tag> tags);

        void showLoadingTagsError();

    }

    public interface ContainerView {

        void showSelectedTags(List<Tag> tags);

        void addTag(Tag tag);

        void removeTag(Tag tag);

    }

}
