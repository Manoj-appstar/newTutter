package com.appstar.tutionportal.teacher.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.TouchImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FragmentViewImage extends Fragment {
    TouchImageView ivFullImage;
    Activity mActivity;
    ImageView ivBack;
    TextView tvClassName;
    String from;
    private SharePreferenceData sharePreferenceData;
    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_full_image, container, false);
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        utils = new Utils();
        Bundle extras = getArguments();
        if (extras != null) {
            from = extras.getString("from");
        }
        ivFullImage = view.findViewById(R.id.ivFullImage);
        if (sharePreferenceData.getUserImage(mActivity).equalsIgnoreCase("")) {
            Glide.with(mActivity).load(R.drawable.temp_profile).apply(RequestOptions.centerCropTransform()).into(ivFullImage);
        } else {
            Glide.with(mActivity).load(sharePreferenceData.getUserImage(mActivity)).apply(RequestOptions.centerCropTransform()).into(ivFullImage);
        }
        ivBack = view.findViewById(R.id.ivBack);
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassName.setText("Profile Image");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equalsIgnoreCase("ProfileTeacherFragment")) {
                    utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                } else {
                    utils.openFragment(mActivity, FragmentNames.EDIT_TEACHER_PROFILE, FragmentNames._EDIT_TEACHER_PROFILE, null, true);
                }
            }
        });

        return view;
    }
}
