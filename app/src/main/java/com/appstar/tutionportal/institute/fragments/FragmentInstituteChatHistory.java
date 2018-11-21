package com.appstar.tutionportal.institute.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.UtilsInstitute;

public class FragmentInstituteChatHistory extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institute_chat, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsInstitute.setCurrentScreen(FragmentNames._INSTITUTE_HOME);
    }
}
