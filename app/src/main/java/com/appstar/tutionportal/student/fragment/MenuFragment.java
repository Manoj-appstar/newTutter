package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.appstar.tutionportal.Dialog.DialogError;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.login.ChooseScreen;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
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
    DBHelper dbHelper;
    private LinearLayout llSetting, llSupport, llAbout, llLogout;

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
        dbHelper = new DBHelper(getContext());
        llSetting = view.findViewById(R.id.llSetting);
        llSupport = view.findViewById(R.id.llSupport);
        llAbout = view.findViewById(R.id.llAbout);
        llLogout = view.findViewById(R.id.llLogout);
    }

    private void onClick() {
        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_SETTING, FragmentNames._STUDENT_SETTING, null, false);
            }
        });

        llSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_SUPPORT, FragmentNames._STUDENT_SUPPORT, null, false);
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_SUPPORT, FragmentNames._STUDENT_SUPPORT, null, false);
            }
        });
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Are you sure to logout from " + getActivity().getResources().getString(R.string.app_name) + "?";
                DialogError.setMessage(getActivity(), msg, "No", "Yes", false, new Runnable() {
                    @Override
                    public void run() {
                        setLogout();
                    }
                });

            }
        });
    }

    private void setLogout() {
        sharePreferenceData.setUserId(getActivity(), "");
        sharePreferenceData.clearAllData(getActivity());
        sharePreferenceData.setUserLogInFirst(getContext(), true);
        dbHelper.onLogOutUser();
        Intent i = new Intent(getActivity(), ChooseScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_HOME);
    }

}
