package com.appstar.tutionportal.teacher.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.appstar.tutionportal.Model.Group;
import com.appstar.tutionportal.Model.GroupData;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.adapter.AllGroupViewAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.SimpleDividerItemDecoration;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewMyAllGroups extends AppCompatActivity implements OnResponseListener {
    public static List<Group> groupList = new ArrayList<>();
    ProgressBar progressBar;
    RequestServer requestServer;
    SharePreferenceData sharePreferenceData;
    RecyclerView recyclerView;
    AllGroupViewAdapter adapter;
    Activity mActivity;
    LinearLayoutManager layoutManager;
    private int REQ_GET_DETAIL = 678;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_group);
        mActivity = ViewMyAllGroups.this;
        initView();
        setList();
    }

    private void initView() {
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycleView);
        requestServer = new RequestServer(this, this);
        sharePreferenceData = new SharePreferenceData(getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

    }

    private void getAllMyGroupDetail() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("uid", sharePreferenceData.getUserId(mActivity));
                    requestServer.sendStringPost(Data.GET_ALL_GROUP_DETAIL, jsonObject, REQ_GET_DETAIL, false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setList() {
        for (int i = 0; i < 10; i++) {
            Group group = new Group();
            if (i == 0) {
                group.setGroupName("John Stero Branch0");
                group.setGroupAdmin("Hii");
                group.setGroupAdminName(String.valueOf(123));
                group.setGroupCode(String.valueOf(1234));
            } else if (i == 1) {
                group.setGroupName("John Stero Branch0");
                group.setGroupAdmin("Hii");
                group.setGroupAdminName(String.valueOf(123));
                group.setGroupCode(String.valueOf(1234));
            } else {
                if (i % 2 == 0) {
                    group.setGroupName("John Stero Branch0");
                    group.setGroupAdmin("Hii");
                    group.setGroupAdminName(String.valueOf(123));
                    group.setGroupCode(String.valueOf(1234));
                } else {
                    group.setGroupName("John Stero Branch0");
                    group.setGroupAdmin("Hii");
                    group.setGroupAdminName(String.valueOf(123));
                    group.setGroupCode(String.valueOf(1234));
                }
            }
            groupList.add(group);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 100);
        if (reqCode == REQ_GET_DETAIL) {
            Gson gson = new Gson();
            Type category = new TypeToken<GroupData>() {
            }.getType();
            GroupData data = gson.fromJson(response, category);
            if (data != null) {
                if (data.getData().size() > 0) {
                    adapter = new AllGroupViewAdapter(this, data.getData());
                    if (adapter != null) {
                        adapter.setMyId(String.valueOf(sharePreferenceData.getUserId(mActivity)));
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
