package com.appstar.tutionportal.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.appstar.tutionportal.student.extras.FontCache;


public class MyEditText extends AppCompatEditText {
    public MyEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/ClanPro-NarrNews.otf", context);
        setTypeface(customFont);
    }
}
