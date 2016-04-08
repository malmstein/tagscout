package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.tags.TagsPresenter;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

public class TagsContainer extends ScrollView implements TagsContract.ContainerView, TagsSelected.Listener {

    private TagsPresenter tagsPresenter;
    private TagsSelected tagsSelected;

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

        tagsSelected = (TagsSelected) findViewById(R.id.tags_selected);
        tagsSelected.setOnTagDeleteListener(this);
    }

    public void setPresenter(TagsPresenter tagsPresenter) {
        this.tagsPresenter = tagsPresenter;
    }

    @Override
    public void addTag(Tag tag) {
        tagsSelected.addTag(tag);
    }

    @Override
    public void removeTag(Tag tag) {
       tagsSelected.removeTag(tag);
    }

    @Override
    public void onTagDeleted(TagsSelected view, Tag tag, int position) {
       tagsPresenter.unSelect(tag);
    }
}
