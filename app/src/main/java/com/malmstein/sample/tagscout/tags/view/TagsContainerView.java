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

public class TagsContainerView extends ScrollView implements TagsContract.ContainerView, TagFilterView.Listener {

    private TagsPresenter tagsPresenter;
    private TagFilterView tagFilter;

    public TagsContainerView(Context context) {
        super(context);
    }

    public TagsContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagsContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_tag_container, this, true);

        tagFilter = (TagFilterView) findViewById(R.id.tag_filter);
        tagFilter.setOnTagDeleteListener(this);
    }

    public void setPresenter(TagsPresenter tagsPresenter) {
        this.tagsPresenter = tagsPresenter;
    }

    @Override
    public void showSelectedTags(List<Tag> tags) {
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
