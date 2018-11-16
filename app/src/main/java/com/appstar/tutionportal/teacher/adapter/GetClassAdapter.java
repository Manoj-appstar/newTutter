package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.appstar.tutionportal.Model.SubjectList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.list.GetClassList;

import java.util.ArrayList;


public class GetClassAdapter extends ArrayAdapter {
    int itemLayout;
    Context context;
    boolean isClass;
    String strNametv;
    private ArrayList<Object> objList, tempItems, suggestions;

    public GetClassAdapter(@NonNull Context context, int res, ArrayList<Object> objList, boolean isClass) {
        super(context, res, objList);
        this.isClass = isClass;
        this.itemLayout = res;
        this.objList = objList;
        tempItems = new ArrayList<Object>(objList); // this makes the difference.
        suggestions = new ArrayList<Object>();
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        Object obj = objList.get(position);

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }
        TextView strName = view.findViewById(R.id.tvtext_item);

        if (obj instanceof GetClassList) {

            strName.setText(((GetClassList) obj).getClass_name());
        } else {
            strName.setText(((SubjectList) obj).getSubjectName());
        }

        return view;
    }

    @Override
    public int getCount() {
        return objList.size();
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    suggestions.clear();
                    for (Object names : tempItems) {
                        if (isClass) {
                            if (((GetClassList) names).getClass_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                suggestions.add(names);
                            }
                        } else {
                            if (((SubjectList) names).getSubjectName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                suggestions.add(names);
                            }
                        }
                    }
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Object> filterList = (ArrayList<Object>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (Object names : filterList) {
                        add(names);
                        notifyDataSetChanged();
                    }
                }
            }
        };
        return myFilter;
    }

}
