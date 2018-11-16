package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.SupportListingAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;

public class FragmentListingSupport extends Fragment {
    ListView myListView;
    String data;
    String[] array;
    TextView tvClassName;
    ImageView ivBack;
    private UtilsStudent utilsStudent;
    private Activity mActivity;
    private SharePreferenceData sharePreferenceData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_listview, container, false);
        utilsStudent = new UtilsStudent();
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        Bundle extras = getArguments();
        if (extras != null) {
            data = extras.getString("data");
        }
        if (data.equalsIgnoreCase("Others")) {
            array = getResources().getStringArray(R.array.others);
        } else {
            array = getResources().getStringArray(R.array.PaymentsRefunds);
        }
        findView(view);
        onClick();
        return view;
    }

    private void findView(View view) {
        myListView = view.findViewById(R.id.myListView);
        tvClassName = view.findViewById(R.id.tvClassName);
        ivBack = view.findViewById(R.id.ivBack);
        tvClassName.setText(data);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (data.equalsIgnoreCase("Others")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("data", (array[position]));
                    bundle.putString("data1", "Others");
                    utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_QUESTION, FragmentNames._STUDENT_QUESTION, bundle, false);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("data", (array[position]));
                    bundle.putString("data1", "Payments & Refunds");
                    utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_QUESTION, FragmentNames._STUDENT_QUESTION, bundle, false);
                }
            }
        });
        myListView.setAdapter(new SupportListingAdapter(mActivity, array));
    }

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_QUESTION);
    }
}
