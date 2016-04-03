package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.malmstein.sample.tagscout.R;

public class TagsContainer extends FrameLayout {

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

        LayoutInflater.from(getContext()).inflate(R.layout.view_tags_container, this, true);
    }

}
