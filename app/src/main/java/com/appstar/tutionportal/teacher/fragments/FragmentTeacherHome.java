package com.appstar.tutionportal.teacher.fragments;

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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.Model.TeacherDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.student.adapter.DashboardPagerAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

public class FragmentTeacherHome extends Fragment implements View.OnClickListener, OnResponseListener {

    public static FragmentManager fragmentManager;
    private static Utils utils;
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
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
        mActivity = getActivity();
        fragmentManager = getChildFragmentManager();
        utils = new Utils();
        sharePreferenceData = new SharePreferenceData();
        teacherDetail = new TeacherDetail();
        dbHelper = new DBHelper(mActivity);
        findViews(view);
        layoutChat.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutNotification.setOnClickListener(this);
        setupViewPager(homePager);
        setupPagerListener();
        requestServer = new RequestServer(mActivity, this);
        //Log.d("id",Data.getTeacherDetail().getBachelorDegree());
        if (TextUtils.isEmpty(Data.getTeacherDetail().getBachelorDegree())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowDialog();
                }
            }, 2000);
        }
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

        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, FragmentTeacherChatHistory.class.getName()), "CHAT");
        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, ViewClassFragment.class.getName()), "HOME");
        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, ProfileTeacherFragment.class.getName()), "PROFILE");
        dashboardPagerAdapter.addFragment(Fragment.instantiate(mActivity, FragmentTeacherNotification.class.getName()), "NOTIFICATION");

        viewPager.setAdapter(dashboardPagerAdapter);
        viewPager.setCurrentItem(Utils.getCurrentTab());

        selectView(Utils.getCurrentTab());
    }

    private void setupPagerListener() {
        homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Utils.setCurrentTab(position);
                selectView(Utils.getCurrentTab());
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

    private void ShowDialog() {

// ...Irrelevant code for customizing the buttons and title
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
        dialogBuilder.setCancelable(false);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.teacher_edit_detail, null);
        dialogBuilder.setView(dialogView);

        etGraduationType = dialogView.findViewById(R.id.etGraduationType);
        etGraduationDetail = dialogView.findViewById(R.id.etGraduationDetail);
        etPostGraduationType = dialogView.findViewById(R.id.etPostGraduationType);
        etPostGraduationDetail = dialogView.findViewById(R.id.etPostGraduationDetail);
        etOthers = dialogView.findViewById(R.id.etOthers);
        etSpecialist = dialogView.findViewById(R.id.etSpecialist);
        tvCancel = dialogView.findViewById(R.id.tvCancel);
        cvCancel = dialogView.findViewById(R.id.cvCancel);
        cvCancel.setVisibility(View.GONE);
        tvAdd = dialogView.findViewById(R.id.tvAdd);
        tvAdd.setGravity(R.id.center_horizontal);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void callAPI() {
        //  RetrofitCall.CallHome(HomeFragment.this, apiInterface);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
            jsonObject.put("bachelor_degree", etGraduationType.getText().toString());
            jsonObject.put("bachelor_degree_detail", etGraduationDetail.getText().toString());
            jsonObject.put("master_degree", etPostGraduationType.getText().toString());
            jsonObject.put("master_degree_detail", etPostGraduationDetail.getText().toString());
            jsonObject.put("other", etOthers.getText().toString());
            jsonObject.put("specialist", etSpecialist.getText().toString());
            requestServer.sendStringPost(UrlManager.ADD_TEACHER_DETAI_MORE, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                if (etGraduationType.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setBachelorDegree("Not Specified");
                } else {
                    Data.getTeacherDetail().setBachelorDegree(etGraduationType.getText().toString());
                    //   sharePreferenceData.setGraduationType(mActivity, etGraduationType.getText().toString());
                }
                if (etGraduationDetail.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setBachelorDegreeDetail("Not Specified");
                    //sharePreferenceData.setGraduationDatail(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setBachelorDegreeDetail(etGraduationDetail.getText().toString());
                    //  sharePreferenceData.setGraduationDatail(mActivity, etGraduationDetail.getText().toString());
                }
                if (etPostGraduationType.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setMasterDegree("Not Specified");
                    //sharePreferenceData.setPostGraduationType(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setMasterDegree(etPostGraduationType.getText().toString());
                    //  sharePreferenceData.setPostGraduationType(mActivity, etPostGraduationType.getText().toString());
                }
                if (etPostGraduationDetail.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setMasterDegreeDatail("Not Specified");
                    //  sharePreferenceData.setPostGraduationDatail(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setMasterDegreeDatail(etPostGraduationDetail.getText().toString());
                    //sharePreferenceData.setPostGraduationDatail(mActivity, etPostGraduationDetail.getText().toString());
                }
                if (etOthers.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setOther("Not Specified");
                    //sharePreferenceData.setOthers(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setOther(etOthers.getText().toString());
                    //sharePreferenceData.setOthers(mActivity, etOthers.getText().toString());
                }
                if (etSpecialist.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setSpecialist("Not Specified");
                    //sharePreferenceData.setOthersDetail(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setSpecialist(etSpecialist.getText().toString());
                    //sharePreferenceData.setOthersDetail(mActivity, etSpecialist.getText().toString());
                }
                dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());
                alertDialog.dismiss();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.setCurrentScreen(FragmentNames._TEACHER_HOME);
    }
}
