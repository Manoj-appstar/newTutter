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
        import android.widget.LinearLayout;
        import android.widget.RatingBar;
        import android.widget.Toast;
        import com.appstar.tutionportal.R;
        import com.appstar.tutionportal.login.LogInActivity;
        import com.appstar.tutionportal.login.SignUp;
        import com.appstar.tutionportal.student.activity.StudentDashboard;
        import com.appstar.tutionportal.student.extras.FragmentNames;
        import com.appstar.tutionportal.student.fragment.ChatFragment;
        import com.appstar.tutionportal.student.fragment.FragmentViewTeacherInfo;
        import com.appstar.tutionportal.student.model.TeachersModel;
        import com.appstar.tutionportal.teacher.activities.AddClasses;
        import com.appstar.tutionportal.teacher.activities.TeacherDashboard;
        import com.appstar.tutionportal.teacher.fragments.EditProfile;
        import com.appstar.tutionportal.util.Utils;
        import com.appstar.tutionportal.util.UtilsStudent;
        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.RequestOptions;

        import java.util.ArrayList;

public class  TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<TeachersModel> arrayList;
    private LayoutInflater inflater;
    int width;
    private UtilsStudent utilsStudent;

    public TeacherListAdapter(Context context, ArrayList<TeachersModel> arrayList) {
        this.mContext = context;
        utilsStudent = new UtilsStudent();
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.li_teachers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

//        holder.ratingbar.setRating(position);
        holder.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(mContext, "" + rating, Toast.LENGTH_SHORT).show();
            }
        });
        // Glide.with(mContext).load(arrayList.get(position).getImage()).override(width, width + 20).into(holder.imgTeacher);
        Glide.with(mContext).load(arrayList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imgTeacher);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        RatingBar ratingbar;
        ImageView imgTeacher;
        CardView teachersLI;
        LinearLayout llViewClass;

        public MyHolder(View itemView) {
            super(itemView);
            ratingbar = itemView.findViewById(R.id.ratingTeacher);
            imgTeacher = itemView.findViewById(R.id.imgTeacher);
            teachersLI = itemView.findViewById(R.id.cvClassName);
            llViewClass = itemView.findViewById(R.id.llViewClass);
            llViewClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilsStudent.openFragment((Activity) mContext, FragmentNames.STUDENT_CLASS_INFO,FragmentNames._STUDENT_CLASS_INFO,null,false);

                 /*   Intent intent =new Intent(mContext,FragmentViewTeacherInfo.class);
                    mContext.startActivity(intent);*/
                }
            });
            imgTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilsStudent.openFragment((Activity) mContext, FragmentNames.STUDENT_TEACHER_PROFILE,FragmentNames._STUDENT_TEACHER_PROFILE,null,false);

                 /*   Intent intent =new Intent(mContext,FragmentViewTeacherInfo.class);
                    mContext.startActivity(intent);*/
                }
            });

        }
    }
}
