package com.appstar.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appstar.tutionportal.Model.SearchLocation;
import com.appstar.tutionportal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class CustomLocationSearchAdapter extends ArrayAdapter {
    List<SearchLocation> searchList;
    Context context;


    public CustomLocationSearchAdapter(List<SearchLocation> sList, Context context, int rId) {
        super(context, rId, sList);
        this.context = context;
        this.searchList = sList;


    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    class ViewHolder {
        TextView tvText;
        ImageView imgLocation;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.auto_search_item, null);
            holder.tvText = convertView.findViewById(R.id.tvAddress);
            holder.imgLocation = convertView.findViewById(R.id.imgLocation);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvText.setText(searchList.get(position).getDesc());


        //holder.tvText.setText(str[position]);
        return convertView;
    }
}
