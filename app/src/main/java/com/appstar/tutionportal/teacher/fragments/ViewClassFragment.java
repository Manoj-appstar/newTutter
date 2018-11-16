package com.appstar.tutionportal.teacher.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Dialog.DialogClass;
import com.appstar.tutionportal.Model.ClassDataDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.NetworkResponse;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.student.model.ClassModel;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.adapter.ClassListAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.NetworkChangeReceiver;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ViewClassFragment extends Fragment implements OnResponseListener, NetworkResponse {
    private static Utils utils;
    private static SharePreferenceData sharePreferenceData;
    private static NetworkChangeReceiver networkChangeReceiver;
    RequestServer requestServer;
    int REQ_CLASS = 188;
    RelativeLayout rl_noData;
    RelativeLayout rl_data;
    TextView AddClass;
    private Activity mActivity;
    private RecyclerView classRecycler;
    private ClassListAdapter classListAdapter;
    private LinearLayoutManager classLLM;
    private FloatingActionButton btnAddClass;
    private ArrayList<ClassModel> classList = new ArrayList<>();
    private ProgressBar progress_bar;
    private DialogClass dialogClass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_class, container, false);
        utils = new Utils();
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        dialogClass = new DialogClass();
        requestServer = new RequestServer(getActivity(), this);
        networkChangeReceiver = new NetworkChangeReceiver(mActivity, this);
        findViews(view);
        getData();
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, AddClasses.class);
                intent.putExtra("from", "individual");
                startActivity(intent);
                //dialogClass.showDialog(mActivity);
                //utils.openFragment(mActivity, FragmentNames.ADD_Class, FragmentNames._ADD_CLASS, null, false);
            }
        });
        AddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, AddClasses.class);
                intent.putExtra("from", "individual");
                startActivity(intent);
                //  utils.openFragment(mActivity, FragmentNames.ADD_Class, FragmentNames._ADD_CLASS, null, false);
                //  dialogClass.showDialog(mActivity);
            }
        });
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        mActivity.registerReceiver(networkChangeReceiver, filter);
        return view;

    }

    private void findViews(View view) {
        classRecycler = view.findViewById(R.id.viewClassRecycler);
        btnAddClass = view.findViewById(R.id.btnAddClass);
        classLLM = new LinearLayoutManager(mActivity);
        classRecycler.setLayoutManager(classLLM);
        rl_noData = view.findViewById(R.id.relNoData);
        rl_data = view.findViewById(R.id.linForData);
        AddClass = view.findViewById(R.id.addClass);
        progress_bar = view.findViewById(R.id.progress_bar);
    }

    private void getData() {
        if (Data.getClassList().size() > 0) {
            bindData();
        } else {
            callAPI();
        }
    }

    public void callAPI() {
        progress_bar.setVisibility(View.VISIBLE);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
            requestServer.sendStringPostWithHeader(UrlManager.GET_TEACHER_CLASS, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bindData() {
        progress_bar.setVisibility(View.GONE);
        rl_data.setVisibility(View.VISIBLE);
        btnAddClass.setVisibility(View.VISIBLE);
        rl_noData.setVisibility(View.GONE);
        classListAdapter = new ClassListAdapter(mActivity, Data.getClassList());
        classRecycler.setAdapter(classListAdapter);
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        progress_bar.setVisibility(View.GONE);
        if (reqCode == REQ_CLASS) {
            try {
                Gson gson = new Gson();
                Type category = new TypeToken<ClassDataDetail>() {
                }.getType();

                ClassDataDetail data = gson.fromJson(response, category);
                if (data.getStatus() == true) {
                    if (data.getData().size() > 0) {
                        rl_data.setVisibility(View.VISIBLE);
                        btnAddClass.setVisibility(View.VISIBLE);
                        rl_noData.setVisibility(View.GONE);
                        Data.setClassList(data.getData());
                        bindData();
                    }
      /*      JSONObject jsonObject = new JSONObject(response);
            JSONArray jo1 = jsonObject.getJSONArray("data");
            if (jo1.length() > 0) {
                // progressUtil.dismiss();
                progress_bar.setVisibility(View.GONE);
                for (int i = 0; i < jo1.length(); i++) {
                    rl_data.setVisibility(View.VISIBLE);
                    btnAddClass.setVisibility(View.VISIBLE);
                    rl_noData.setVisibility(View.GONE);
                    JSONObject c = jo1.getJSONObject(i);
                    ClassModel classModel = new ClassModel();
                    classModel.setClassId(c.getInt("cid"));
                    classModel.setClassName(c.getString("class_name"));
                    classModel.setSubject(c.getString("subject"));
                    classModel.setLocation(c.getString("location"));
                    classModel.setTime_to(c.getString("time_to"));
                    classModel.setTime_from(c.getString("time_from"));
                    classModel.setCapacity(c.getString("capacity"));
                    classModel.setStatus(c.getString("status"));
                    classModel.setRating(c.getString("rating"));
                    classList.add(classModel);
                    classListAdapter = new ClassListAdapter(mActivity, classList);
                    classRecycler.setAdapter(classListAdapter);
                }
            }*/
                } else {
                    rl_noData.setVisibility(View.VISIBLE);
                    rl_data.setVisibility(View.GONE);
                    btnAddClass.setVisibility(View.GONE);
                }
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "Error in response", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {
    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        if (isConnected) {
            callAPI();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
