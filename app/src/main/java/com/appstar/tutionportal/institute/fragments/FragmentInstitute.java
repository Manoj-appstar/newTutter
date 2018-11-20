package com.appstar.tutionportal.institute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appstar.tutionportal.Model.InstituteData;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.institute.activities.AddInstituteBranch;
import com.appstar.tutionportal.institute.adapter.ExpandableAdapter;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collections;

public class FragmentInstitute extends Fragment implements OnResponseListener {
    View view;
    RequestServer requestServer;
    SharePreferenceData preferenceData;
    ExpandableListView expandableView;
    ProgressBar progress;
    TextView tvText;
    ExpandableAdapter adapter;
    int REQ_GET_INSTITUTE = 123;
    CardView cvAddInstitute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.institute_fragment_branch, container, false);
        this.view = view;
        preferenceData = new SharePreferenceData(getActivity());
        requestServer = new RequestServer(getActivity(), this);
        initView();
        getData();
        onClick();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter == null) {
            bindData();
        } else
            adapter.notifyDataSetChanged();
    }

    private void initView() {
        cvAddInstitute = view.findViewById(R.id.cvAddInstitute);
        expandableView = view.findViewById(R.id.expandableView);
        progress = view.findViewById(R.id.progress);
        tvText = view.findViewById(R.id.tvText);
    }

    private void onClick() {
        cvAddInstitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddInstituteBranch.class));
            }
        });
    }

    private void getData() {
        progress.setVisibility(View.VISIBLE);
        try {
            if (Data.getInstituteList().size() <= 0) {
                String url = UrlManager.INSTITUTE_DATA;
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("director_id=", preferenceData.getUserId(getActivity()));
                jsonObject.put("director_id", preferenceData.getUserId(getContext()));
                requestServer.sendStringPostWithHeader(url, jsonObject, REQ_GET_INSTITUTE, false);
            } else {
                bindData();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
   /*     new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Data.getInstituteList().size() <= 0) {
                        String url = UrlManager.INSTITUTE_DATA;
                        JSONObject jsonObject = new JSONObject();
                        //jsonObject.put("director_id=", preferenceData.getUserId(getActivity()));
                        jsonObject.put("director_id", preferenceData.getUserId(getContext()));
                        requestServer.sendStringPostWithHeader(url, jsonObject, REQ_GET_INSTITUTE, false);
                    } else {
                        bindData();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 2000);*/

    }

    private void bindData() {
        progress.setVisibility(View.GONE);
        Collections.reverse(Data.getInstituteList());
        adapter = new ExpandableAdapter(this, getActivity(), Data.getInstituteList());
        expandableView.setAdapter(adapter);

    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            progress.setVisibility(View.GONE);
            if (reqCode == REQ_GET_INSTITUTE) {

                Gson gson = new Gson();
                Type category = new TypeToken<InstituteData>() {
                }.getType();

                InstituteData data = gson.fromJson(jsonObject.toString(), category);
                if (data.getStatus() == true) {
                    if (data.getData().size() <= 0) {
                        cvAddInstitute.setVisibility(View.VISIBLE);
                        tvText.setText("No institute added");
                        tvText.setVisibility(View.VISIBLE);
                    } else {
                        tvText.setVisibility(View.GONE);
                        cvAddInstitute.setVisibility(View.GONE);
                        Data.setInstituteList(data.getData());
                        bindData();
                    }
                } else {
                    cvAddInstitute.setVisibility(View.VISIBLE);
                    tvText.setVisibility(View.VISIBLE);
                    tvText.setText("No institute added");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onFailed(int reqCode, String response) {
        progress.setVisibility(View.GONE);
    }
}