package com.appstar.tutionportal.student.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.views.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StudentClassAdapter extends RecyclerView.Adapter<StudentClassAdapter.MyHolder> {

    int width;
    private Context mContext;
    private ArrayList<ClassDetail> arrayList;
    private LayoutInflater inflater;
    private UtilsStudent utilsStudent;

    public StudentClassAdapter(Context context, ArrayList<ClassDetail> arrayList) {
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
    public void onBindViewHolder(@NonNull StudentClassAdapter.MyHolder holder, int position) {
        ClassDetail classDetail = arrayList.get(position);
//        holder.ratingbar.setRating(position);
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
        //setDistance(classDetail.getD)

        // Glide.with(mContext).load(arrayList.get(position).getImage()).override(width, width + 20).into(holder.imgTeacher);
        // Glide.with(mContext).load(arrayList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imgTeacher);
    }

    private void setDistance(MyTextView tvDistance, double distance) {
        distance = distance * 1000;
        tvDistance.setText(new DecimalFormat("##.##").format(distance));

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
        ImageView imgTeacher;

        public MyHolder(View itemView) {
            super(itemView);
            ratingbar = itemView.findViewById(R.id.ratingTeacher);
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
            llViewClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilsStudent.openFragment((Activity) mContext, FragmentNames.STUDENT_CLASS_INFO, FragmentNames._STUDENT_CLASS_INFO, null, false);

                 /*   Intent intent =new Intent(mContext,FragmentViewTeacherInfo.class);
                    mContext.startActivity(intent);*/
                }
            });
            imgTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilsStudent.openFragment((Activity) mContext, FragmentNames.STUDENT_TEACHER_PROFILE, FragmentNames._STUDENT_TEACHER_PROFILE, null, false);

                 /*   Intent intent =new Intent(mContext,FragmentViewTeacherInfo.class);
                    mContext.startActivity(intent);*/
                }
            });

        }
    }
}
