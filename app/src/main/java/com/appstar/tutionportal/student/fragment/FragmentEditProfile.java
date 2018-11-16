package com.appstar.tutionportal.student.fragment;

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

import com.appstar.tutionportal.Dialog.DialogPhone;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;

public class FragmentEditProfile extends Fragment {
    Activity mActivity;
    SharePreferenceData sharePreferenceData;
    ImageView ivBack;
    TextView tvClassName;
    private UtilsStudent utilsStudent;
    private DialogPhone dialogPhone;
    ImageView ivMobileEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editprofile, container, false);
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        utilsStudent = new UtilsStudent();
        dialogPhone = new DialogPhone();
        findViews(view);
        setData();
        onclickListener();
        return view;
    }

    private void findViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassName.setText("Edit Profile");
        ivMobileEdit = view.findViewById(R.id.ivMobileEdit);

    }

    private void setData() {

    }

    private void onclickListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });

        ivMobileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogPhone.showDialog(mActivity, new Runnable() {
                   @Override
                   public void run() {

                   }
               });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_EDIT);
    }
}
