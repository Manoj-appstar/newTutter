package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.util.Utils;

import java.util.List;

public class AddSubjectAdapter extends RecyclerView.Adapter<AddSubjectAdapter.MyHolder> {
    int width;
    Object addclasses;
    private Context mContext;
    private List<Subject> arrayList;
    private LayoutInflater inflater;
    private Utils utils;


    public AddSubjectAdapter(Context context, List<Subject> arrayList, Object obj) {
        this.mContext = context;
        utils = new Utils();
        this.arrayList = arrayList;
        this.addclasses = obj;
        inflater = LayoutInflater.from(mContext);
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public AddSubjectAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddSubjectAdapter.MyHolder(inflater.inflate(R.layout.layout_subject_individual, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AddSubjectAdapter.MyHolder holder, final int position) {

        final Subject model = arrayList.get(position);
        if (arrayList.size() > 1) {
            // holder.tvSubject.setVisibility(View.GONE);
            holder.etSubject.setText(model.getSubject());
            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addclasses != null) {
                        arrayList.remove(position);
                        notifyDataSetChanged();
                        if (addclasses instanceof AddClasses) {
                            ((AddClasses) addclasses).showHideMoreSubject();
                        }
                    }
                }
            });
        } else {
            //  holder.tvSubject.setVisibility(View.VISIBLE);
            holder.etSubject.setText(model.getSubject());
            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addclasses != null) {
                        arrayList.remove(position);
                        notifyDataSetChanged();
                        if (addclasses instanceof AddClasses) {
                            ((AddClasses) addclasses).showHideMoreSubject();
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //return 10;
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView etSubject, etTeacherAssigned, tvSubject;
        ImageView ivRemove;


        public MyHolder(View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            etSubject = itemView.findViewById(R.id.etSubject);
            etTeacherAssigned = itemView.findViewById(R.id.etTeacherAssigned);
            ivRemove = itemView.findViewById(R.id.ivRemove);
        }

    }
}
