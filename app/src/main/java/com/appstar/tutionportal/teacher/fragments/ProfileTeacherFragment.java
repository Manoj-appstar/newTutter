package com.appstar.tutionportal.teacher.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.login.NewLogInActivity;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

public class ProfileTeacherFragment extends Fragment implements View.OnClickListener, OnResponseListener {
    public static FragmentManager fragmentManager;
    private static Utils utils;
    private static LinearLayout llAddClass;
    private static LinearLayout llViewClass;
    private static SharePreferenceData sharePreferenceData;
    private static RequestServer requestServer;
    int REQ_CLASS = 188;
    int left = 123, right = 1234;
    DBHelper dbHelper;
    private FragmentActivity mActivity;
    private ImageView editProfile1;
    private TextView tvEditMoreDetail;
    private ImageView ivSetting, imgProfile;
    private Switch aSwitch;
    private TextView tvUserName, tvUserMobile, tvUserEmail, tvGraduationType, tvGraduationDetail, tvpostGraduationType, tvOthers, tvOthersDetail, tvPostGraduationDetail;
    private TextView tvEnable, tvDisable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        utils = new Utils();
        mActivity = getActivity();
        dbHelper = new DBHelper(mActivity);
        //fragmentManager = this.getChildFragmentManager();
        fragmentManager = mActivity.getSupportFragmentManager();
        sharePreferenceData = new SharePreferenceData();
        requestServer = new RequestServer(getActivity(), this);
        findViews(view);
        setData();
        return view;
    }

    private void findViews(View view) {
        aSwitch = view.findViewById(R.id.switch1);
        aSwitch.setOnClickListener(this);
        tvEnable = view.findViewById(R.id.tvEnable);
        tvDisable = view.findViewById(R.id.tvDisable);
        Log.d("services",Data.getTeacherDetail().getServices().toLowerCase());

        if (Data.getTeacherDetail().getServices().equalsIgnoreCase("1")) {
            aSwitch.setChecked(false);
            tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
            tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
            tvDisable.setText("Disabled");
            tvEnable.setText("Enable");

        } else {
            aSwitch.setChecked(true);
            tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
            tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
            tvDisable.setText("Disable");
            tvEnable.setText("Enabled");
        }

        aSwitch.setOnClickListener(this);
        editProfile1 = view.findViewById(R.id.editProfile1);
        editProfile1.setOnClickListener(this);
        tvEditMoreDetail = view.findViewById(R.id.tvEditMoreDetail);
        tvEditMoreDetail.setOnClickListener(this);
        ivSetting = view.findViewById(R.id.ivSetting);
        ivSetting.setOnClickListener(this);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserMobile = view.findViewById(R.id.tvUserMobile);
        tvGraduationType = view.findViewById(R.id.tvGraduationType);
        tvGraduationDetail = view.findViewById(R.id.tvGraduationDetail);
        tvpostGraduationType = view.findViewById(R.id.tvpostGraduationType);
        tvPostGraduationDetail = view.findViewById(R.id.tvPostGraduationDetail);
        tvOthers = view.findViewById(R.id.tvOthers);
        tvOthersDetail = view.findViewById(R.id.tvOthersDetail);
        imgProfile = view.findViewById(R.id.imgProfile);
        if (TextUtils.isEmpty(Data.getTeacherDetail().getImage())) {
            Glide.with(mActivity).load(R.drawable.temp_profile).apply(RequestOptions.circleCropTransform()).into(imgProfile);
        } else {
            Glide.with(mActivity).load(Data.getTeacherDetail().getImage()).apply(RequestOptions.circleCropTransform()).into(imgProfile);
        }
        imgProfile.setOnClickListener(this);
    }


    private void setData() {
        tvUserName.setText(Data.getTeacherDetail().getFirstName());
        tvUserEmail.setText(Data.getTeacherDetail().getEmail());
        tvUserMobile.setText(Data.getTeacherDetail().getPhone());
        tvGraduationType.setText(Data.getTeacherDetail().getBachelorDegree());
        tvGraduationDetail.setText(Data.getTeacherDetail().getBachelorDegreeDetail());
        tvpostGraduationType.setText(Data.getTeacherDetail().getMasterDegree());
        tvPostGraduationDetail.setText(Data.getTeacherDetail().getMasterDegreeDatail());
        tvOthers.setText(Data.getTeacherDetail().getOther());
        tvOthersDetail.setText(Data.getTeacherDetail().getSpecialist());
    }

    private void switchApi() {
        //        RetrofitCall.CallHome(HomeFragment.this, apiInterface);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
            if (aSwitch.isChecked()) {
                jsonObject.put("status", "0");
            } else {
                jsonObject.put("status", "1");
            }
            requestServer.sendStringPost(UrlManager.UPDATE_TEACHER_SERVICES, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editProfile1:
                utils.openFragment(mActivity, FragmentNames.EDIT_TEACHER_PROFILE, FragmentNames._EDIT_TEACHER_PROFILE, null, false);
                break;

            case R.id.tvEditMoreDetail:
                utils.openFragment(mActivity, FragmentNames.EDIT_PROFILE_MORE, FragmentNames._EDIT_PROFILE_MORE, null, false);
                break;

            case R.id.imgProfile:

                Bundle bundle = new Bundle();
                bundle.putString("from", "ProfileTeacherFragment");
                utils.openFragment(mActivity, FragmentNames.VIEW_FULL_IMAGE, FragmentNames._VIEW_FULL_IMAGE, bundle, false);
                break;

            case R.id.switch1:
                switchApi();
                break;

            case R.id.ivSetting:
                PopupMenu popup = new PopupMenu(getActivity(), ivSetting);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                sharePreferenceData.logoutUser(mActivity);
                                dbHelper.onLogOutUser();
                                getActivity().finish();
                                utils.openActivity(mActivity, NewLogInActivity.class);
                                return true;
                            case R.id.shareVia:
                                navigateToShareScreen(UrlManager.APP_URL);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();//showing popup menu
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        boolean bool = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("status")) {
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    bool = false;
                    if (aSwitch.isChecked()) {
                        Data.getTeacherDetail().setServices("0");
                        //  sharePreferenceData.setTeacherServices(mActivity, "1");
                        Snackbar.make(aSwitch, "Your all class services has been closed", Snackbar.LENGTH_SHORT).show();
                        aSwitch.setChecked(true);
                        tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                        tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
                        tvDisable.setText("Disable");
                        tvEnable.setText("Enabled");
                    } else {
                        Data.getTeacherDetail().setServices("1");
                        //  sharePreferenceData.setTeacherServices(mActivity, "0");
                        Snackbar.make(aSwitch, "Your all class services has been open", Snackbar.LENGTH_SHORT).show();
                        aSwitch.setChecked(false);
                        tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                        tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
                        tvDisable.setText("Disabled");
                        tvEnable.setText("Enable");
                    }

                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {
    }

    public void navigateToShareScreen(String shareUrl) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl + " -via " + getString(R.string.app_name));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Share applications not found!", Toast.LENGTH_SHORT).show();
        }

    }
}
