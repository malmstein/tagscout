package com.malmstein.sample.tagscout.tags;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.List;

public class TagsView extends FrameLayout implements TagsContract.View{

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
    }

    @Override
    public void showTags(List<Tag> tags) {
        Snackbar.make(this, "Showing " + tags.size() + " tags", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingTagsError() {
        Snackbar.make(this, "Couldn't load tags", Snackbar.LENGTH_LONG).show();
    }
}
