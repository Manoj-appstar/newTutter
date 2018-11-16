package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.views.MyTextView;

public class MenuFragment extends Fragment {
    public static FragmentManager fragmentManager;
    private static Activity mActivity;
    private static ListView navListView;
    private static ImageView imgProfile;
    private static MyTextView txtName;
    private static UtilsStudent utilsStudent;
    private static String[] navArray;
    private static SharePreferenceData sharePreferenceData;
    private LinearLayout llSetting, llSupport, llAbout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mActivity = getActivity();
        utilsStudent = new UtilsStudent();
        sharePreferenceData = SharePreferenceData.getInstance();
        fragmentManager = getActivity().getSupportFragmentManager();
        findViews(view);
        onClick();
        return view;
    }

    private void findViews(View view) {
        llSetting = view.findViewById(R.id.llSetting);
        llSupport = view.findViewById(R.id.llSupport);
        llAbout = view.findViewById(R.id.llAbout);
    }

    private void onClick() {
        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity,FragmentNames.STUDENT_SETTING,FragmentNames._STUDENT_SETTING,null,false);
            }
        });

        llSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity,FragmentNames.STUDENT_SUPPORT,FragmentNames._STUDENT_SUPPORT,null,false);
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity,FragmentNames.STUDENT_SUPPORT,FragmentNames._STUDENT_SUPPORT,null,false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_HOME);
    }
}
