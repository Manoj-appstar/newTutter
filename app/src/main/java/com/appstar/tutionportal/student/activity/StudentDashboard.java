package com.appstar.tutionportal.student.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.interfaces.NetworkResponse;
import com.appstar.tutionportal.util.NetworkChangeReceiver;
import com.appstar.tutionportal.util.UtilsStudent;

public class StudentDashboard extends AppCompatActivity implements NetworkResponse {

    public static FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    AlertDialog alertDialog;
    boolean bool = false;
    private Activity mActivity;
    private UtilsStudent utilsStudent;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fragmentManager = getSupportFragmentManager();
        utilsStudent = new UtilsStudent();
        mActivity = this;
        networkChangeReceiver = new NetworkChangeReceiver(mActivity, this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(networkChangeReceiver, filter);

        utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, false);

    }

    @Override
    public void onBackPressed() {
        switch (UtilsStudent.getCurrentScreen()) {

            case FragmentNames._STUDENT_HOME:
                onBack();
                break;

            case FragmentNames._STUDENT_CLASS_INFO:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
                break;

            case FragmentNames._STUDENT_TEACHER_PROFILE:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
                break;

            case FragmentNames._STUDENT_TEACHER_REVIEW:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_TEACHER_PROFILE, FragmentNames._STUDENT_TEACHER_PROFILE, null, true);
                break;

            case FragmentNames._STUDENT_SETTING:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
                break;

            case FragmentNames._STUDENT_SUPPORT:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
                break;

            case FragmentNames._STUDENT_QUESTION:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_LISTING_SUPPORT, FragmentNames._STUDENT_LISTING_SUPPORT, null, true);
                break;

            case FragmentNames._STUDENT_EDIT:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
                break;

            case FragmentNames._STUDENT_LISTING_SUPPORT:
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
                break;
        }
    }


    private void onBack() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        if (isConnected) {
            if (bool == true) {
                alertDialog.dismiss();
            }
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setCancelable(false);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.no_internet, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            alertDialog.getWindow().setLayout(width, height);
            alertDialog.show();
            bool = true;

        }
    }
}
