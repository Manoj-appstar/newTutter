package com.appstar.tutionportal.student.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.DashboardPagerAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.volley.RequestServer;

public class FragmentStudentHome extends Fragment implements View.OnClickListener {

    public static FragmentManager fragmentManager;
    private static UtilsStudent utilsStudent;
    private static SharePreferenceData sharePreferenceData;
    private ViewPager homePager;
    private LinearLayout layoutChat, layoutProfile, layoutHome, layoutFav, layoutMenu;
    private FragmentActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);
        mActivity = getActivity();
        fragmentManager = getChildFragmentManager();
        utilsStudent = new UtilsStudent();
        sharePreferenceData = new SharePreferenceData();
        findViews(view);

        layoutChat.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutFav.setOnClickListener(this);
        layoutMenu.setOnClickListener(this);

        setupViewPager(homePager);
        setupPagerListener();
        return view;
    }

    private void findViews(View view) {
        homePager = view.findViewById(R.id.homePager);
        layoutChat = view.findViewById(R.id.layoutChat);
        layoutProfile = view.findViewById(R.id.layoutProfile);
        layoutHome = view.findViewById(R.id.layoutHome);
        layoutFav = view.findViewById(R.id.layoutFavourite);
        layoutMenu = view.findViewById(R.id.layoutMenu);

    }

    private void setupViewPager(ViewPager viewPager) {
        DashboardPagerAdapter dashboardPagerAdapterStudent = new DashboardPagerAdapter(fragmentManager);

        dashboardPagerAdapterStudent.addFragment(Fragment.instantiate(mActivity, ChatFragment.class.getName()), "CHAT");
        dashboardPagerAdapterStudent.addFragment(Fragment.instantiate(mActivity, ProfileFragment.class.getName()), "PROFILE");
        dashboardPagerAdapterStudent.addFragment(Fragment.instantiate(mActivity, HomeFragment.class.getName()), "HOME");
        dashboardPagerAdapterStudent.addFragment(Fragment.instantiate(mActivity, FavouriteFragment.class.getName()), "FAVOURITE");
        dashboardPagerAdapterStudent.addFragment(Fragment.instantiate(mActivity, MenuFragment.class.getName()), "MENU");

        viewPager.setAdapter(dashboardPagerAdapterStudent);
        viewPager.setCurrentItem(UtilsStudent.getCurrentTab());
        selectView(UtilsStudent.getCurrentTab());

      //  viewPager.setCurrentItem(2);
     //   layoutHome.setSelected(true);

    }

    private void setupPagerListener() {
        homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                UtilsStudent.setCurrentTab(position);
                selectView(UtilsStudent.getCurrentTab());

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
        layoutMenu.setSelected(false);
        layoutFav.setSelected(false);

        switch (position) {
            case 0:
                layoutChat.setSelected(true);
                break;
            case 1:
                layoutProfile.setSelected(true);
                break;
            case 2:
                layoutHome.setSelected(true);
                break;
            case 3:
                layoutFav.setSelected(true);
                break;
            case 4:
                layoutMenu.setSelected(true);
                break;
            default:
                layoutHome.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layoutHome:
                homePager.setCurrentItem(2);
                break;
            case R.id.layoutChat:
                homePager.setCurrentItem(0);
                break;
            case R.id.layoutProfile:
                homePager.setCurrentItem(1);
                break;
            case R.id.layoutFavourite:
                homePager.setCurrentItem(3);
                break;
            case R.id.layoutMenu:
                homePager.setCurrentItem(4);
                break;
            default:
                homePager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_HOME);
    }
}
