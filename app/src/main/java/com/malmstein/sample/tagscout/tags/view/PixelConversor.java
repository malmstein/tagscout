package com.malmstein.sample.tagscout.tags.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class PixelConversor {

    public int dipToPx(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

}
