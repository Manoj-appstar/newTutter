package com.appstar.tutionportal.institute.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.Model.TeacherDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.DashboardPagerAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.UtilsInstitute;
import com.appstar.tutionportal.volley.RequestServer;

public class FragmentInstituteHome extends Fragment implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    private static UtilsInstitute utilsInstitutet;
    private static SharePreferenceData sharePreferenceData;
    RequestServer requestServer;
    TextView tvCancel, tvAdd;
    AlertDialog alertDialog;
    int REQ_CLASS = 198;
    EditText etGraduationType, etGraduationDetail, etPostGraduationType, etPostGraduationDetail, etOthers, etSpecialist;
    TeacherDetail teacherDetail;
    FrameLayout flNotification;
    TextView tvNotification;
    private ViewPager homePager;
    private LinearLayout layoutChat, layoutProfile, layoutHome, layoutNotification;
    private FragmentActivity mActivity;
    private CardView cvCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institute_home, container, false);
        mActivity = getActivity();
        fragmentManager = getChildFragmentManager();
        utilsInstitutet = new UtilsInstitute();
        sharePreferenceData = new SharePreferenceData();
        teacherDetail = new TeacherDetail();

        findViews(view);
        layoutChat.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutNotification.setOnClickListener(this);

        setupViewPager(homePager);
        setupPagerListener();
        return view;

    }

    private void findViews(View view) {

        homePager = view.findViewById(R.id.homePager);
        layoutChat = view.findViewById(R.id.layoutChat);
        layoutProfile = view.findViewById(R.id.layoutProfile);
        layoutHome = view.findViewById(R.id.layoutHome);
        layoutNotification = view.findViewById(R.id.layoutNotification);
        flNotification = view.findViewById(R.id.flNotification);
        tvNotification = view.findViewById(R.id.tvNotification);

    }

    private void setupViewPager(ViewPager viewPager) {

        DashboardPagerAdapter dashboardPagerAdapter = new DashboardPagerAdapter(fragmentManager);

        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, FragmentInstituteChatHistory.class.getName()), "CHAT");
        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, FragmentInstituteViewClass.class.getName()), "HOME");
        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, FragmentInstituteProfile.class.getName()), "PROFILE");
        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, FragmentInstituteNotification.class.getName()), "NOTIFICATION");

        viewPager.setAdapter(dashboardPagerAdapter);
        viewPager.setCurrentItem(UtilsInstitute.getCurrentTab());

        selectView(UtilsInstitute.getCurrentTab());
    }

    private void setupPagerListener() {
        homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                UtilsInstitute.setCurrentTab(position);
                selectView(UtilsInstitute.getCurrentTab());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void selectView(int position) {

        layoutHome.setSelected(false);
        layoutChat.setSelected(false);
        layoutProfile.setSelected(false);
        layoutNotification.setSelected(false);

        switch (position) {
            case 0:
                layoutChat.setSelected(true);
                break;

            case 1:
                layoutHome.setSelected(true);
                break;

            case 2:
                layoutProfile.setSelected(true);
                break;

            case 3:
                layoutNotification.setSelected(true);
                break;

            default:
                layoutHome.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layoutChat:
                homePager.setCurrentItem(0);
                break;

            case R.id.layoutHome:
                homePager.setCurrentItem(1);
                break;

            case R.id.layoutProfile:
                homePager.setCurrentItem(2);
                break;

            case R.id.layoutNotification:
                homePager.setCurrentItem(3);
                break;

            default:
                homePager.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsInstitute.setCurrentScreen(FragmentNames._INSTITUTE_HOME);
    }


}
