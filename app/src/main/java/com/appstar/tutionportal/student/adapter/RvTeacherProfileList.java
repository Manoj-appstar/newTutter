package com.appstar.tutionportal.student.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RvTeacherProfileList extends RecyclerView.Adapter<RvTeacherProfileList.MyHolder> {

    int width;
    private Context mContext;
    private ArrayList<TeachersModel> arrayList;
    private LayoutInflater inflater;
    private Utils utils;

    public RvTeacherProfileList(Context context, ArrayList<TeachersModel> arrayList) {
        this.mContext = context;
        utils = new Utils();
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public RvTeacherProfileList.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RvTeacherProfileList.MyHolder(inflater.inflate(R.layout.layout_teacher_classes, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RvTeacherProfileList.MyHolder holder, int position) {
        Glide.with(mContext).load(arrayList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imgTeacher);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgTeacher;


        public MyHolder(View itemView) {
            super(itemView);

            imgTeacher = itemView.findViewById(R.id.imgTeacher);

        }
    }
}
