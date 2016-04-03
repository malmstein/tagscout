package com.malmstein.sample.tagscout.tags;

import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public class TagsContract {

    interface Presenter {

        void loadTags();

    }

    interface View {

        void showTags(List<Tag> tags);

        void showLoadingTagsError();
    }
}
