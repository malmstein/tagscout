package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TagFilter extends RelativeLayout {

    private TagFilterDisplayer displayer;

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

    private void init(Context context){
        displayer = new TagFilterDisplayer(context, new UnitConversor());
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
        displayer.drawTags();
    }
}
