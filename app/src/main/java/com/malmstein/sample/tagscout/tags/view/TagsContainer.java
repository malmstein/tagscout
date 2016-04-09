package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.tags.TagsPresenter;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

import java.util.List;

public class TagsContainer extends ScrollView implements TagsContract.ContainerView, TagFilter.Listener {

    private TagsPresenter tagsPresenter;
    private TagFilter tagFilter;

    public TagsContainer(Context context) {
        super(context);
    }

    public TagsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagsContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_tag_container, this, true);

        tagFilter = (TagFilter) findViewById(R.id.tag_filter);
        tagFilter.setOnTagDeleteListener(this);
    }

    public void setPresenter(TagsPresenter tagsPresenter) {
        this.tagsPresenter = tagsPresenter;
    }

    @Override
    public void showTags(List<Tag> tags) {
        tagFilter.showTags(tags);
    }

    @Override
    public void addTag(Tag tag) {
        tagFilter.addTag(tag);
    }

    @Override
    public void removeTag(Tag tag) {
        tagFilter.removeTag(tag);
    }

    @Override
    public void onTagDeleted(Tag tag) {
        tagsPresenter.toggleTagState(tag);
    }
}
