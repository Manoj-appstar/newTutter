package com.appstar.tutionportal.institute.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appstar.common.adapter.AddClassAdapter;
import com.appstar.tutionportal.Model.Branch;
import com.appstar.tutionportal.Model.ClassDataDetail;
import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActivityBranchClass extends AppCompatActivity implements OnResponseListener {

    TextView tvText;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Activity mActivity;
    Branch branchInfo;
    String branch_id;
    CardView cvAddInstitute;
    int REQ_GET_CLASS = 636;
    RequestServer requestServer;
    FloatingActionButton btnAddClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_class);
        mActivity = ActivityBranchClass.this;
        initView();
        getData();
        onClick();
    }

    private void getData() {
        branchInfo = (Branch) getIntent().getSerializableExtra("obj");
        branch_id = branchInfo.getBranchId();
        if (Data.getClassList().size() > 0) {
            List<ClassDetail> sList = new ArrayList();
            sList = getFilterClassByBranchId();
            bindData(sList);
        } else {
            downloadData();
        }
    }

    private void downloadData() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("branch_id", branchInfo.getBranchId());
            requestServer.sendStringPostWithHeader(UrlManager.GET_ALL_BRANCH_CLASSES, jsonObject, REQ_GET_CLASS, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bindData(List<ClassDetail> sList) {
        progressBar.setVisibility(View.GONE);
        if (sList.size() < 0) {
            cvAddInstitute.setVisibility(View.VISIBLE);
            tvText.setText("No institute added");
            tvText.setVisibility(View.VISIBLE);
        } else {
            tvText.setVisibility(View.GONE);
            cvAddInstitute.setVisibility(View.GONE);
            AddClassAdapter adapter = new AddClassAdapter(mActivity, sList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initView() {
        requestServer = new RequestServer(this, this);
        tvText = findViewById(R.id.tvText);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(manager);
        cvAddInstitute = findViewById(R.id.cvAddInstitute);
        btnAddClass = findViewById(R.id.btnAddClass);

    }

    private List<ClassDetail> getFilterClassByBranchId() {
        List<ClassDetail> details = new ArrayList<>();
        for (ClassDetail obj : Data.getClassList()) {
            Log.d("string",branch_id);
            if (obj.getBranchId().equals(branch_id)) {
                details.add(obj);
            }
        }
        return details;
    }

    private void onClick() {
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityBranchClass.this, AddClasses.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            if (reqCode == REQ_GET_CLASS) {

                List<ClassDetail> sList = new ArrayList<>();
                Gson gson = new Gson();
                Type category = new TypeToken<ClassDataDetail>() {
                }.getType();

                ClassDataDetail data = gson.fromJson(response, category);
                if (data.getStatus()== true)
                    if (data.getData().size() > 0) {
                        sList = data.getData();
                    }
                bindData(sList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
