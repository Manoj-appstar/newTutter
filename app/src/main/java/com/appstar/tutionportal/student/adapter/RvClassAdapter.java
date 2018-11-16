package com.appstar.tutionportal.student.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.login.LogInActivity;
import com.appstar.tutionportal.login.SignUp;
import com.appstar.tutionportal.student.activity.StudentDashboard;
import com.appstar.tutionportal.student.fragment.ChatFragment;
import com.appstar.tutionportal.student.fragment.FragmentViewTeacherInfo;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.activities.TeacherDashboard;
import com.appstar.tutionportal.teacher.fragments.EditProfile;
import com.appstar.tutionportal.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class  RvClassAdapter extends RecyclerView.Adapter<RvClassAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<TeachersModel> arrayList;
    private LayoutInflater inflater;
    int width;
    private Utils utils;

    public RvClassAdapter(Context context, ArrayList<TeachersModel> arrayList) {
        this.mContext = context;
        utils = new Utils();
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.layout_similar_classes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
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
