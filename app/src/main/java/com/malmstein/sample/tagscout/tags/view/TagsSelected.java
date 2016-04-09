package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TagsSelected extends RelativeLayout {

    public static final int DEFAULT_LINE_MARGIN = 8;
    public static final int DEFAULT_TAG_MARGIN = 8;
    public static final float DEFAULT_INNER_PADDING = 10;
    public static final float LAYOUT_WIDTH_OFFSET = 2;

    public static final float DEFAULT_TAG_RADIUS = 10;
    public static final int DEFAULT_TAG_LAYOUT_COLOR_PRESSED = Color.parseColor("#88363636");

    private List<Tag> mTags = new ArrayList<>();

    private LayoutInflater layoutInflater;
    private ViewTreeObserver viewTreeObserver;
    private Listener mDeleteListener;

    private int defaultPadding;
    private int lineMargin;
    private int tagMargin;
    private int offset;

    private int mWidth;
    private boolean mInitialized = false;

    public TagsSelected(Context ctx) {
        super(ctx, null);
        initialize(ctx);
    }

    public TagsSelected(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialize(ctx);
    }

    public TagsSelected(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        initialize(ctx);
    }

    private void initialize(Context ctx) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    mInitialized = true;
                    drawTags();
                }
            }
        });

        defaultPadding = dipToPx(getContext(), DEFAULT_INNER_PADDING);
        lineMargin = dipToPx(getContext(), DEFAULT_LINE_MARGIN);
        tagMargin = dipToPx(getContext(), DEFAULT_TAG_MARGIN);
        offset = dipToPx(getContext(), LAYOUT_WIDTH_OFFSET);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (width <= 0) {
            return;
        }
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTags();
    }

    private void drawTags() {
        if (!mInitialized) {
            return;
        }

        removeAllViews();

        float total = getPaddingLeft() + getPaddingRight();

        int listIndex = 1;
        int indexBottom = 1;
        int indexHeader = 1;

        for (Tag item : mTags) {
            final Tag tag = item;

            View tagLayout = layoutInflater.inflate(R.layout.view_tag_filter, null);
            tagLayout.setId(listIndex);
            tagLayout.setBackground(getSelector(tag));

            TextView tagView = (TextView) tagLayout.findViewById(R.id.tag_label);
            tagView.setText(tag.getTag());

            TextView deletableView = (TextView) tagLayout.findViewById(R.id.tag_delete);
            deletableView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteListener != null) {
                        mDeleteListener.onTagDeleted(tag);
                    }
                }
            });

            float tagWidth = tagView.getPaint().measureText(tag.getTag()) + defaultPadding + offset;
            tagWidth += deletableView.getPaint().measureText("X") + defaultPadding + defaultPadding;

            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagParams.bottomMargin = lineMargin;

            if (mWidth <= total + tagWidth) {
                //need to add in new line
                tagParams.addRule(RelativeLayout.BELOW, indexBottom);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                indexBottom = listIndex;
                indexHeader = listIndex;
            } else {
                //no need to add new line
                tagParams.addRule(RelativeLayout.ALIGN_TOP, indexHeader);
                //not header of the line
                if (listIndex != indexHeader) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                }

            }
            total += tagWidth;
            addView(tagLayout, tagParams);
            listIndex++;
        }

    }

    private Drawable getSelector(Tag tag) {
        StateListDrawable states = new StateListDrawable();

        int tagColor = Color.parseColor("#" + tag.getColor());
        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setColor(tagColor);
        gdNormal.setCornerRadius(DEFAULT_TAG_RADIUS);

        GradientDrawable gdPress = new GradientDrawable();
        gdPress.setColor(DEFAULT_TAG_LAYOUT_COLOR_PRESSED);
        gdPress.setCornerRadius(DEFAULT_TAG_RADIUS);
        states.addState(new int[]{android.R.attr.state_pressed}, gdPress);
        states.addState(new int[]{}, gdNormal);
        return states;
    }

    public void addTag(Tag tag) {
        mTags.add(tag);
        sortAlphabetically();
        drawTags();
    }

    private void sortAlphabetically() {
        Collections.sort(mTags, new Comparator<Tag>() {
            @Override
            public int compare(final Tag object1, final Tag object2) {
                return object1.getTag().compareTo(object2.getTag());
            }
        });
    }

    public void removeTag(Tag removedTag) {
        int index = findTagIndex(removedTag);
        if (index > -1) {
            remove(index);
            sortAlphabetically();
        }
    }

    private int findTagIndex(Tag tag) {
        for (int i = 0; i < mTags.size(); i++) {
            if (mTags.get(i).getId() == (tag.getId())) {
                return i;
            }
        }
        return -1;
    }

    private void remove(int position) {
        if (position < mTags.size()) {
            mTags.remove(position);
            drawTags();
        }
    }

    public void setOnTagDeleteListener(Listener deleteListener) {
        mDeleteListener = deleteListener;
    }

    public interface Listener {
        void onTagDeleted(Tag tag);
    }

    private static int dipToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
