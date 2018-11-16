package com.appstar.tutionportal.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class MyViewPager extends ViewPager {
    private int width = 0;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;

        if (getChildCount() > getCurrentItem()) {
            View child = getChildAt(getCurrentItem());
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    public void onRefresh() {
//
//        try {
//            int height = 0;
//            if (getChildCount() > getCurrentItem()) {
//                View child = getChildAt(getCurrentItem());
//                child.measure(width, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                int h = child.getMeasuredHeight();
//                if (h > height) height = h;
//            }
//
//            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
//            layoutParams.height = heightMeasureSpec;
//            this.setLayoutParams(layoutParams);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}