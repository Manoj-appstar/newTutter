package com.appstar.tutionportal.institute.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.UtilsInstitute;

public class FragmentInstituteNotification extends Fragment implements OnResponseListener {
    Activity mActivity;
    UtilsInstitute utilsInstitute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institute_notification, container, false);
        mActivity = getActivity();
        utilsInstitute = new UtilsInstitute();
        return view;
    }

   /* @Override
    public void onResume() {
        super.onResume();
        UtilsInstitute.setCurrentScreen(FragmentNames._INSTITUTE_HOME);
    }*/

    @Override
    public void onSuccess(int reqCode, String response) {

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}