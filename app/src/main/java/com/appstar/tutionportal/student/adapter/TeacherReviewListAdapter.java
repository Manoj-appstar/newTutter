package com.appstar.tutionportal.student.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Model.TeacherReviewModel;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.util.UtilsStudent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class TeacherReviewListAdapter extends RecyclerView.Adapter<TeacherReviewListAdapter.MyHolder> {

    int width;
    private Context mContext;
    private ArrayList<TeacherReviewModel> arrayList;
    private LayoutInflater inflater;
    private UtilsStudent utilsStudent;

    public TeacherReviewListAdapter(Context context, ArrayList<TeacherReviewModel> arrayList) {
        this.mContext = context;
        utilsStudent = new UtilsStudent();
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.li_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

//        holder.ratingbar.setRating(position);
        holder.ratingStudent.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(mContext, "" + rating, Toast.LENGTH_SHORT).show();
            }
        });
        // Glide.with(mContext).load(arrayList.get(position).getImage()).override(width, width + 20).into(holder.imgTeacher);
        Glide.with(mContext).load(arrayList.get(position).getImage()).apply(RequestOptions.circleCropTransform()).into(holder.imgStudent);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        RatingBar ratingStudent;
        ImageView imgStudent, ivLike, ivMore;
        TextView tvStudentName, tvDate, tvReview;

        public MyHolder(View itemView) {
            super(itemView);
            ratingStudent = itemView.findViewById(R.id.ratingStudent);
            imgStudent = itemView.findViewById(R.id.imgStudent);
            ivLike = itemView.findViewById(R.id.imgStudent);
            ivMore = itemView.findViewById(R.id.ivMore);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvReview = itemView.findViewById(R.id.tvReview);
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
