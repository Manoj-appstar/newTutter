package com.appstar.tutionportal.teacher.fragments;

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

public class FragmentTeacherNotification extends Fragment implements OnResponseListener {
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_notification, container, false);
        mActivity = getActivity();
        return view;
    }

    @Override
    public void onSuccess(int reqCode, String response) {

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
