package com.appstar.tutionportal.student.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.views.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentClassAdapter extends RecyclerView.Adapter<StudentClassAdapter.MyHolder> {
    int width;
    private Context mContext;
    private List<ClassDetail> arrayList;
    private LayoutInflater inflater;
    private UtilsStudent utilsStudent;

    public StudentClassAdapter(Context context, List<ClassDetail> arrayList) {
        this.mContext = context;
        utilsStudent = new UtilsStudent();
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public StudentClassAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentClassAdapter.MyHolder(inflater.inflate(R.layout.class_studnet_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentClassAdapter.MyHolder holder, int position) {
        final ClassDetail classDetail = arrayList.get(position);
        holder.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(mContext, "" + rating, Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvBatchName.setText(classDetail.getBatchName());
        holder.tvRupees.setText("₹ " + classDetail.getPrice());
        holder.tvAvailableSeats.setText("99");
        holder.tvLocation.setText(classDetail.getAddress());
        setDistance(holder.tvDistance, Double.valueOf(classDetail.getDistance()));
        String subject = "";
        for (Subject obj : classDetail.getSubjectname()) {
            if (subject.trim().equalsIgnoreCase("")) {
                subject += obj.getSubject();
            } else {
                subject += ", " + obj.getSubject();
            }
        }
        holder.tvSubject.setText(subject);
        if(classDetail.isFavorite()){
            holder.imgFavorite.setImageResource(R.drawable.ic_fav_red);
        }
        else
            holder.imgFavorite.setImageResource(R.drawable.ic_favorite_border_black);

        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classDetail.setFavorite(!classDetail.isFavorite());
                notifyDataSetChanged();

            }
        });

        holder.llViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("",classDetail);
                utilsStudent.openFragment((Activity) mContext, FragmentNames.STUDENT_CLASS_INFO, FragmentNames._STUDENT_CLASS_INFO, null, false);
            }
        });

    }

    private void setDistance(MyTextView tvDistance, double distance) {
        //distance = distance * 1000;
        if (distance < 0.5)
            distance = 0.5;
        tvDistance.setText(new DecimalFormat("##.##").format(distance) + " KM");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CardView cvClassName;
        LinearLayout llViewClass;
        MyTextView tvBatchName, tvLocation, tvSubject, tvRupees, tvAvailableSeats, tvDistance;
        RatingBar ratingbar;
        ImageView imgTeacher, imgFavorite;

        public MyHolder(View itemView) {
            super(itemView);
            ratingbar = itemView.findViewById(R.id.ratingTeacher);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            imgTeacher = itemView.findViewById(R.id.imgTeacher);
            cvClassName = itemView.findViewById(R.id.cvClassName);
            llViewClass = itemView.findViewById(R.id.llViewClass);
            tvBatchName = itemView.findViewById(R.id.tvBatchName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvRupees = itemView.findViewById(R.id.tvRupees);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);
            tvDistance = itemView.findViewById(R.id.tvDistance);

            imgTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilsStudent.openFragment((Activity) mContext, FragmentNames.STUDENT_TEACHER_PROFILE, FragmentNames._STUDENT_TEACHER_PROFILE, null, false);
                }
            });

        }
    }
}
