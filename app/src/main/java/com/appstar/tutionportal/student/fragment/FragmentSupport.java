package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;

public class FragmentSupport extends Fragment {
    TextView tvClassName;
    ImageView ivBack;
    CardView cvTeacherRegister, cvPaymentsRefunds;
    private UtilsStudent utilsStudent;
    private Activity mActivity;
    private SharePreferenceData sharePreferenceData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_support, container, false);
        utilsStudent = new UtilsStudent();
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        findView(view);
        onClick();
        return view;
    }

    private void findView(View view) {
        tvClassName = view.findViewById(R.id.tvClassName);
        ivBack = view.findViewById(R.id.ivBack);
        tvClassName.setText("Support");
        cvTeacherRegister = view.findViewById(R.id.cvTeacherRegister);
        cvPaymentsRefunds = view.findViewById(R.id.cvPaymentsRefunds);
    }

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });

        cvTeacherRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("data", "Others");
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_LISTING_SUPPORT, FragmentNames._STUDENT_LISTING_SUPPORT, bundle, false);
            }
        });

        cvPaymentsRefunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("data", "Payments & Refunds");
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_LISTING_SUPPORT, FragmentNames._STUDENT_LISTING_SUPPORT, bundle, false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_SUPPORT);
    }
}
