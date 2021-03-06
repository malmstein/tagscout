package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ScrollView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;
import com.malmstein.sample.tagscout.tags.TagsPresenter;
import com.malmstein.sample.tagscout.tags.domain.TagsContract;

import java.util.List;

public class TagsContainerView extends ScrollView implements TagsContract.ContainerView, TagFilterView.Listener {

    public static int MAX_HEIGHT_VALUE = 600;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            setScrollBarFadeDuration(0);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (heightSize > MAX_HEIGHT_VALUE) {
                heightSize = MAX_HEIGHT_VALUE;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
            getLayoutParams().height = heightSize;
        } catch (Exception e) {
            Log.e(getClass().getName().toString(), "onMesure: Error forcing height ");
        } finally {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_tag_container, this, true);

        tagFilter = (TagFilterView) findViewById(R.id.tag_filter);
        tagFilter.setOnTagDeleteListener(this);

        EditText tagSearchView = (EditText) findViewById(R.id.tag_search_view);
        tagSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tagsPresenter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        });
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
