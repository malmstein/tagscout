package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.tags.TagsPresenter;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

import java.util.ArrayList;
import java.util.List;

public class TagsView extends FrameLayout implements TagsContract.View{

    private TagsPresenter tagsPresenter;

    private TagsAdapter tagsAdapter;
    private ListView tagsList;

    public TagsView(Context context) {
        super(context);
    }

    public TagsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_tags_view, this, true);
        tagsList = (ListView) findViewById(R.id.tags_list);
    }

    public void setPresenter(TagsPresenter tagsPresenter) {
        this.tagsPresenter = tagsPresenter;
    }

    @Override
    public void showTags(List<Tag> tags) {
        setAdapter();
        tagsAdapter.replaceData(tags);
    }

    @Override
    public void showLoadingTagsError() {
        Snackbar.make(this, "Couldn't load tags", Snackbar.LENGTH_LONG).show();
    }

    private void setAdapter(){
        if (tagsAdapter == null){
            tagsAdapter = new TagsAdapter(new ArrayList<Tag>(), tagItemListener);
            tagsList.setAdapter(tagsAdapter);
        }
    }

    TagsAdapter.TagItemListener tagItemListener = new TagsAdapter.TagItemListener() {
        @Override
        public void onTagClick(Tag clickedTag) {
            tagsPresenter.markAsSelected(clickedTag);
        }
    };


}
