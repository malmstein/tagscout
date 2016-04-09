package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malmstein.sample.tagscout.R;
import com.malmstein.sample.tagscout.data.model.Tag;

public class TagFilter extends RelativeLayout {

    public static final int DEFAULT_LINE_MARGIN = 8;
    public static final int DEFAULT_TAG_MARGIN = 8;
    public static final float DEFAULT_INNER_PADDING = 10;
    public static final float LAYOUT_WIDTH_OFFSET = 2;
    public static final float DEFAULT_TAG_RADIUS = 10;

    private int defaultPadding;
    private int lineMargin;
    private int tagMargin;
    private int offset;

    private LayoutInflater layoutInflater;
    private ViewTreeObserver viewTreeObserver;
    private PixelConversor pixelConversor;
    private TagFilterDisplayer displayer;
    private Listener deleteTagListener;

    private boolean initialized = false;

    public TagFilter(Context context) {
        super(context, null);
        init(context);
    }

    public TagFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TagFilter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        pixelConversor = new PixelConversor();
        displayer = new TagFilterDisplayer();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!initialized) {
                    initialized = true;
                    removeAllViews();
                    drawTags();
                }
            }
        });
        initUnits(context);
    }

    private void initUnits(Context context) {
        defaultPadding = pixelConversor.dipToPx(context, DEFAULT_INNER_PADDING);
        lineMargin = pixelConversor.dipToPx(context, DEFAULT_LINE_MARGIN);
        tagMargin = pixelConversor.dipToPx(context, DEFAULT_TAG_MARGIN);
        offset = pixelConversor.dipToPx(context, LAYOUT_WIDTH_OFFSET);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        displayer.setWidth(w);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (width <= 0) {
            return;
        }
        displayer.setWidth(getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTags();
    }

    private void drawTags() {
        if (!initialized) {
            return;
        }

        removeAllViews();

        displayer.setSpaceUsed(getPaddingLeft() + getPaddingRight());

        int listIndex = 1;
        int indexBottom = 1;
        int indexHeader = 1;

        for (Tag tag : displayer.getTags()) {
            View tagLayout = createTagLayout(listIndex, tag);
            LayoutParams tagParams;

            float tagWidth = computeTagWidth(tagLayout);

            if (displayer.fitsInSameRow(tagWidth)) {
                if (listIndex != indexHeader) {
                    tagParams = createHeaderSameLineLayoutParams(indexHeader, listIndex);
                    displayer.addToSpaceUsed(tagMargin);
                } else {
                    tagParams = createSameLineLayoutParams(indexHeader);
                }
            } else {
                tagParams = createNewLineLayoutParams(indexBottom);
                displayer.setSpaceUsed(getPaddingLeft() + getPaddingRight());
                indexBottom = listIndex;
                indexHeader = listIndex;
            }
            displayer.addToSpaceUsed(tagWidth);
            addView(tagLayout, tagParams);
            listIndex++;
        }
    }

    private float computeTagWidth(View tagLayout) {
        TextView tagText = (TextView) tagLayout.findViewById(R.id.tag_label);
        TextView deletableView = (TextView) tagLayout.findViewById(R.id.tag_delete);

        float tagTextWidth = tagText.getPaint().measureText(tagText.getText().toString()) + defaultPadding + offset;
        float tagCrossWidth = deletableView.getPaint().measureText("X") + defaultPadding + defaultPadding;
        float tagWidth = tagTextWidth + tagCrossWidth;

        return tagWidth;
    }

    public View createTagLayout(int listIndex, final Tag tag) {
        View tagLayout = layoutInflater.inflate(R.layout.view_tag_filter, null);
        tagLayout.setId(listIndex);
        tagLayout.setBackground(getSelector(tag));

        TextView tagView = (TextView) tagLayout.findViewById(R.id.tag_label);
        tagView.setText(tag.getTag());

        TextView deletableView = (TextView) tagLayout.findViewById(R.id.tag_delete);
        deletableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteTagListener != null) {
                    deleteTagListener.onTagDeleted(tag);
                }
            }
        });

        return tagLayout;
    }

    private Drawable getSelector(Tag tag) {
        StateListDrawable states = new StateListDrawable();

        int tagColor = Color.parseColor("#" + tag.getColor());
        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setColor(tagColor);
        gdNormal.setCornerRadius(DEFAULT_TAG_RADIUS);

        GradientDrawable gdPress = new GradientDrawable();
        gdPress.setColor(Color.parseColor("#88363636"));
        gdPress.setCornerRadius(DEFAULT_TAG_RADIUS);
        states.addState(new int[]{android.R.attr.state_pressed}, gdPress);
        states.addState(new int[]{}, gdNormal);
        return states;
    }

    public RelativeLayout.LayoutParams createNewLineLayoutParams(int indexBottom) {
        RelativeLayout.LayoutParams tagParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagParams.bottomMargin = lineMargin;
        tagParams.addRule(RelativeLayout.BELOW, indexBottom);
        return tagParams;
    }

    public RelativeLayout.LayoutParams createHeaderSameLineLayoutParams(int indexHeader, int listIndex) {
        RelativeLayout.LayoutParams tagParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagParams.bottomMargin = lineMargin;
        tagParams.addRule(RelativeLayout.ALIGN_TOP, indexHeader);
        tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
        tagParams.leftMargin = tagMargin;
        return tagParams;
    }

    public RelativeLayout.LayoutParams createSameLineLayoutParams(int indexHeader) {
        RelativeLayout.LayoutParams tagParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagParams.bottomMargin = lineMargin;
        tagParams.addRule(RelativeLayout.ALIGN_TOP, indexHeader);
        return tagParams;
    }

    public void setOnTagDeleteListener(Listener deleteListener) {
        deleteTagListener = deleteListener;
    }

    public void addTag(Tag tag) {
        displayer.addTag(tag);
        drawTags();
    }

    public void removeTag(Tag tag) {
        displayer.removeTag(tag);
        drawTags();
    }

    public interface Listener {
        void onTagDeleted(Tag tag);
    }
}
