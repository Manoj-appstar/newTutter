package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import com.appstar.tutionportal.util.UtilsStudent;

public class FragmentSettings extends Fragment {
    TextView tvClassName, tvChangeSettings;
    ImageView ivBack;
    private UtilsStudent utilsStudent;
    private Activity mActivity;
    private SharePreferenceData sharePreferenceData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_settings, container, false);
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
        tvClassName.setText("Settings");
        tvChangeSettings = view.findViewById(R.id.tvChangeSettings);
    }

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });

        tvChangeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_SETTING);
    }
}
