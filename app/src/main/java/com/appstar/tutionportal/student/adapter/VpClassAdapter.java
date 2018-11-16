package com.appstar.tutionportal.student.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class VpClassAdapter extends PagerAdapter {
    LayoutInflater mLayoutInflater;
    Context mContext;
    List<TeachersModel> arrayList = new ArrayList<>();

    public VpClassAdapter(Context context, ArrayList<TeachersModel> lists) {
        this.mContext = context;
        this.arrayList = lists;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //View v = LayoutInflater.from(mContext).inflate(R.layout.layout_viewimages, null);
        View v;
        v = mLayoutInflater.inflate(R.layout.layout_class_images, container, false);
        ImageView imgTeacher = v.findViewById(R.id.imgProfile);
        //imgTeacher.setImageResource(R.drawable.ic_logo);
        Glide.with(mContext).load(arrayList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(imgTeacher);

        //String url=images.get(position).getClass_image();
        //  Glide.with(mContext).load(images.get(position).getClass_image()).into(viewImages);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
