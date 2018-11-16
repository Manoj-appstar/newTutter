package com.appstar.tutionportal.teacher.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.student.model.ClassModel;
import com.appstar.tutionportal.teacher.adapter.ClassListAdapter;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class FragmentEditClass extends Fragment implements OnResponseListener {
    private static Activity mActivity;
    int REQ_CLASS = 198;
    TextView btnSaveClass;
    private ProgressUtil progressUtil;
    private Utils utils;
    private SharePreferenceData sharePreferenceData;
    private RequestServer requestServer;
    private EditText AddClass, AddSubject, AddLimit;
    private TextView AddLocation, AddDate, AddTimeTo, AddTimeFrom;
    private ClassModel classModel;
    String class_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editteacher_class, container, false);
        progressUtil = ProgressUtil.getInstance();
        utils = new Utils();
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        requestServer = new RequestServer(getActivity(), this);
        Bundle extras = getArguments();

        if (extras != null) {
            class_id = extras.getString("class_id");
        }

        findViews(view);
        callAPI();
        btnSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // EditApi();
            }
        });
        return view;
    }

    private void findViews(View view) {
        AddClass = view.findViewById(R.id.AddClass);
        AddSubject = view.findViewById(R.id.AddSubject);
        AddDate = view.findViewById(R.id.AddDate);
        AddTimeTo = view.findViewById(R.id.AddTimeTo);
        AddTimeFrom = view.findViewById(R.id.AddTimeFrom);
        AddLimit = view.findViewById(R.id.AddLimit);
        AddLocation = view.findViewById(R.id.AddLocation);
        btnSaveClass = view.findViewById(R.id.btnSaveClass);
    }

    private void callAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", sharePreferenceData.getUserId(mActivity));
            jsonObject.put("class_id", class_id);
            requestServer.sendStringPost(UrlManager.EDIT_TEACHER_CLASS, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void EditApi() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", sharePreferenceData.getUserId(mActivity));
            jsonObject.put("class_name", "8");
            jsonObject.put("subject", "8");
            jsonObject.put("class_id", "8");
            jsonObject.put("class_id", "8");
            jsonObject.put("class_id", "8");
            jsonObject.put("class_id", "8");
            jsonObject.put("class_id", "8");
            requestServer.sendStringPost(UrlManager.EDIT_TEACHER_CLASS, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("data") != "") {
                JSONArray jo1 = jsonObject.getJSONArray("data");
                for (int i = 0; i < jo1.length(); i++) {
                    JSONObject c = jo1.getJSONObject(i);
                    //  ClassModel classModel = new ClassModel();
                    AddClass.setText(c.getString("class_name"));
                    AddSubject.setText(c.getString("subject"));
                    AddDate.setText(c.getString("date"));
                    AddTimeTo.setText(c.getString("time_to"));
                    AddTimeFrom.setText(c.getString("time_from"));
                    AddLimit.setText(c.getString("capacity"));
                    AddLocation.setText(c.getString("location"));

                }
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
        Utils.setCurrentScreen(FragmentNames._EDIT_CLASS);
    }
}
