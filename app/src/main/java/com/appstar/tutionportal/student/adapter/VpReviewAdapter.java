package com.appstar.tutionportal.student.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appstar.tutionportal.R;

public class VpReviewAdapter extends PagerAdapter {
    int[] layouts = {R.layout.layout_reveiw_teacher, R.layout.layout_write_review};
    LayoutInflater layoutInflater;
    private Context mContext;

    public VpReviewAdapter(Context context) {
        this.mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //View v = LayoutInflater.from(mContext).inflate(R.layout.layout_viewimages, null)
        View v = layoutInflater.inflate(R.layout.layout_rate_teacher, container, false);
        View v1 = layoutInflater.inflate(R.layout.layout_write_review, container, false);
        View viewarr[] = {v, v1};
        container.addView(viewarr[position]);
        return viewarr[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}

