package com.appstar.tutionportal.institute.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appstar.common.adapter.AddClassAdapter;
import com.appstar.tutionportal.Model.ClassDataDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class FragmentClasses extends Fragment implements OnResponseListener {
    View view;
    TextView tvNoData;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    int REQ_GET_CLASS = 733;
    SharePreferenceData sharePreferenceData;
    RequestServer requestServer;
    LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.institute_fragment_classes, container, false);
        this.view = view;
        sharePreferenceData = new SharePreferenceData();
        initView();
        getData();
        return view;
    }


    private void initView() {
        requestServer = new RequestServer(getActivity(), this);
        tvNoData = view.findViewById(R.id.tvNoData);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
    }

    private void getData() {
        if (Data.getClassList().size() > 0) {
            bindData();
        } else {
            downloadData();
        }
    }

    private void downloadData() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("director_id", sharePreferenceData.getUserId(getActivity()));
            requestServer.sendStringPostWithHeader(UrlManager.GET_ALL_CLASSES_DIRECTOR, jsonObject, REQ_GET_CLASS, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bindData() {
        progressBar.setVisibility(View.GONE);
        AddClassAdapter adapter = new AddClassAdapter(getActivity(), Data.getClassList(), this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onSuccess(int reqCode, String response) {
        progressBar.setVisibility(View.GONE);
        try {
            if (reqCode == REQ_GET_CLASS) {
                Gson gson = new Gson();
                Type category = new TypeToken<ClassDataDetail>() {
                }.getType();

                ClassDataDetail data = gson.fromJson(response, category);
                if (data.getStatus() == true) {
                    if (data.getData().size() > 0) {
                        tvNoData.setVisibility(View.GONE);
                        Data.setClassList(data.getData());
                        bindData();
                    } else
                        tvNoData.setVisibility(View.VISIBLE);
                } else
                    tvNoData.setVisibility(View.VISIBLE);

            }
        } catch (Exception ex) {

        }

    }

    @Override
    public void onFailed(int reqCode, String response) {
        progressBar.setVisibility(View.GONE);
    }
}
