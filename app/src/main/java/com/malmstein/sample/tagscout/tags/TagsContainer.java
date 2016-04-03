package com.malmstein.sample.tagscout.tags;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.malmstein.sample.tagscout.R;

public class TagsContainer extends FrameLayout {

    public TagsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.view_tags_container, null, false);
    }

}
