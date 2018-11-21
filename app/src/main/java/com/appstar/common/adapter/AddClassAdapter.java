package com.appstar.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.fragments.FragmentViewTeacherClass;
import com.bumptech.glide.Glide;

import java.util.List;

public class AddClassAdapter extends RecyclerView.Adapter<AddClassAdapter.MyHolder> {
    Context mContext;
    private List<ClassDetail> classList;

    public AddClassAdapter(Context context, List<ClassDetail> arrayList, Object obj) {
        this.mContext = context;
        this.classList = arrayList;
    }

    @NonNull
    @Override
    public AddClassAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddClassAdapter.MyHolder(LayoutInflater.from(mContext).inflate(R.layout.classes_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AddClassAdapter.MyHolder holder, final int position) {
        final ClassDetail classDetail = classList.get(position);
        holder.tvBatchName.setText(classDetail.getBatchName());
        holder.tvInstitute.setText(classDetail.getInstituteName());
        holder.tvTeacher.setText(classDetail.getTeacherName());
        holder.tvLocation.setText(classDetail.getCity());
        String subjects = "";
        for (Subject subject : classDetail.getSubjectname()) {
            subjects += subject.getSubject() + " ,";
        }

        holder.cvInstituteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FragmentViewTeacherClass.class);
               // intent.putExtra("class", "editClass");
                intent.putExtra("class_id", classDetail.getId());
                intent.putExtra("from", "institute");
              //  intent.putExtra("editFrom", "institute");
                mContext.startActivity(intent);
            }
        });

        if (subjects.length() > 1)
            holder.tvSubject.setText(subjects.substring(0, subjects.length() - 1));

        if (classDetail.getClassImage() != null && classDetail.getClassImage().size() > 0) {
            //   holder.vieImages.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(classDetail.getClassImage().get(0).getImageUrl()).into(holder.imgClassImage);

        } else {
            //   holder.vieImages.setVisibility(View.GONE);
            Glide.with(mContext).load(R.drawable.ic_logo).into(holder.imgClassImage);
            holder.imgClassImage.setImageResource(R.drawable.temp_profile);
        }
    }

    @Override
    public int getItemCount() {
        //return 10;
        return classList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvBatchName, tvSubject, tvTeacher, tvInstitute, tvLocation, tvAvailableSeats, tvRupees;
        ImageView imgClassImage;
        CardView cvInstituteClass;

        public MyHolder(View itemView) {
            super(itemView);
            cvInstituteClass = itemView.findViewById(R.id.cvInstituteClass);
            tvRupees = itemView.findViewById(R.id.tvRupees);
            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);
            tvBatchName = itemView.findViewById(R.id.tvBatchName);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTeacher = itemView.findViewById(R.id.tvTeacher);
            tvInstitute = itemView.findViewById(R.id.tvInstitute);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgClassImage = itemView.findViewById(R.id.imgClassImage);
        }
    }
}
