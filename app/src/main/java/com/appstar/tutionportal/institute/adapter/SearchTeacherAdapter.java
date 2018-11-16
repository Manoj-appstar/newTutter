package com.appstar.tutionportal.institute.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.Model.TeacherDetail;
import com.appstar.tutionportal.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchTeacherAdapter extends ArrayAdapter {
    Context context;
    private ArrayList<Object> objList, tempItems, suggestions;
    int res;

    public SearchTeacherAdapter(@NonNull Context context, int res, ArrayList<Object> objList) {
        super(context, res, objList);
              this.res = res;
        this.objList = objList;
        tempItems = new ArrayList<Object>(objList); // this makes the difference.
        suggestions = new ArrayList<Object>();
    }

    /* @Override
     public View getView(int position, View convertView, @NonNull ViewGroup parent) {
         final ViewHolder holder;
         TeacherDetail detail = teacherList.get(position);
         if (convertView == null) {
             holder = new ViewHolder();
             convertView = LayoutInflater.from(context).inflate(R.layout.search_teacher_item, null);
             holder.tvName = convertView.findViewById(R.id.tvTeacherName);
             holder.imageView = convertView.findViewById(R.id.imageView);
             convertView.setTag(holder);
         } else {
             holder = (ViewHolder) convertView.getTag();
         }
         holder.tvName.setText(detail.getFirstName() + " " + detail.getLastName());
         if (!TextUtils.isEmpty(detail.getImage()))
             Glide.with(context).load(detail.getImage()).into(holder.imageView);
         else
             holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
         return convertView;
     }*/

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent){
        Object obj = objList.get(position);
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(res, parent, false);
        }
        TextView strName = view.findViewById(R.id.tvTeacherName);
        ImageView imageView = view.findViewById(R.id.imageView);

        if (obj instanceof TeacherDetail) {
            strName.setText(((TeacherDetail) obj).getFirstName());
            //strName.setText(((TeacherDetail) obj).getPic());
            Glide.with(context).load(((TeacherDetail) obj).getPic()).apply(RequestOptions.circleCropTransform()).into(imageView);
        }
        return view;
    }

    @Override
    public int getCount() {
        return objList.size();
    }


}
