package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.ViewImageActivity;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.MyTextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.MyHolder> {

    private static SharePreferenceData sharePreferenceData;
    int width;
    private Context mContext;
    private List<ClassDetail> arrayList;
    private LayoutInflater inflater;
    private Utils utils;


    public ClassListAdapter(Context context, List<ClassDetail> arrayList) {
        this.mContext = context;
        sharePreferenceData = new SharePreferenceData();
        utils = new Utils();
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public ClassListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassListAdapter.MyHolder(inflater.inflate(R.layout.li_class, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassListAdapter.MyHolder holder, final int position) {
        //holder.ratingbar.setRating(position);
        final ClassDetail model = arrayList.get(position);
        //   callAPI(model.getClassId());
        holder.tvClassName.setText(model.getBatchName());
        holder.tvLocation.setText(model.getAddress());
        holder.tvTimeTo.setText(model.getTimingTo());
        holder.tvTimeFrom.setText(model.getTimingFrom());
        holder.ratingBar.setRating(3.0f);
        holder.ratingBar.setNumStars(5);
        if (model.getClassImage() != null && model.getClassImage().size() > 0) {
            Glide.with(mContext).load(model.getClassImage().get(0).getImageUrl()).into(holder.imgTeacher);
        } else {
            holder.imgTeacher.setImageResource(R.drawable.temp_profile);
        }

        holder.cvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("class_id", String.valueOf(arrayList.get(position).getId()));
                //utils.openFragment((Activity) mContext, FragmentNames.VIEW_TEACHER_CLASS_INFO, FragmentNames._VIEW_TEACHER_CLASS_INFO, bundle, true);
            }
        });

        holder.imgTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getClassImage() != null && model.getClassImage().size() > 0) {
                    Intent intent = new Intent(mContext, ViewImageActivity.class);
                    intent.putExtra("class_id", model.getId());
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "No Image Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        //return 10;
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CardView cvClass;
        MyTextView tvClassName;
        MyTextView tvLocation;
        MyTextView tvTimeTo, tvTimeFrom;
        ImageView ivEdit, imgTeacher;
        TextView vieImages, tvStatus;
        RatingBar ratingBar;
        TextView tvSubject;


        public MyHolder(View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            cvClass = itemView.findViewById(R.id.cvClass);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTimeTo = itemView.findViewById(R.id.tvTimeTo);
            tvTimeFrom = itemView.findViewById(R.id.tvTimeFrom);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            imgTeacher = itemView.findViewById(R.id.imgTeacher);
            vieImages = itemView.findViewById(R.id.vieImages);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ratingBar = itemView.findViewById(R.id.ratingClass);
            imgTeacher.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    Animation animation1 =
                            AnimationUtils.loadAnimation(mContext,
                                    R.anim.img_anim);
                    imgTeacher.startAnimation(animation1);

                }
            });
        }

    }
}
