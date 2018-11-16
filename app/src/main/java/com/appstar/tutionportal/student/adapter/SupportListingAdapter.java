package com.appstar.tutionportal.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.list.SingleRow;

import java.util.ArrayList;

public class SupportListingAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context myContext;
    ArrayList<SingleRow> arrayList;
    String[] names;

    public SupportListingAdapter(Context myContext, String[] array) {
        this.myContext = myContext;
        names = array;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = LayoutInflater.from(myContext).inflate(R.layout.listview_item_support, null);
        //  View v = inflater.inflate(R.layout.listview_item, parent,false);
        TextView tvtext_item = v.findViewById(R.id.tvtext_item);
        //   SingleRow temp_name =names[position];
        tvtext_item.setText(names[position]);
        return v;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];

    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}