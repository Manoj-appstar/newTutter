package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appstar.tutionportal.Model.ClassImage;
import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    Context mContext;
    List<ClassImage> images=new ArrayList<>();
    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context, List<ClassImage> lists) {
        this.mContext = context;
        this.images = lists;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //View v = LayoutInflater.from(mContext).inflate(R.layout.layout_viewimages, null);

        View v = mLayoutInflater.inflate(R.layout.layout_viewimages, container, false);
        ImageView viewImages = v.findViewById(R.id.viewImages);
     //   viewImages.setImageResource(R.drawable.ic_logo);
        //String url=images.get(position).getClass_image();

        Glide.with(mContext).load(images.get(position).getImageUrl()).into(viewImages);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
