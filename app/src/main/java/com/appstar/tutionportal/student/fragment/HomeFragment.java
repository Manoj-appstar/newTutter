package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.common.GetSearchLocation;
import com.appstar.common.model.Address;
import com.appstar.common.model.FilterClass;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.retrofit.ApiInterface;
import com.appstar.tutionportal.student.adapter.TeacherListAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.interfaces.ApiResponse;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.volley.RequestServer;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnResponseListener {

    private static final float APPBAR_ELEVATION = 10f;
    ApiResponse apiResponse;
    Toolbar toolbar;
    int firstVisibleInListview;
    AppBarLayout appBarLayout;
    LinearLayout llLocation;
    ImageView imgFilter;
    TextView tvLocation;
    int ACTIVITY_GET_LOCATION = 678;
    Address address;
    FilterClass filterClass;
    RequestServer requestServer;
    private Activity mActivity;
    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private TeacherListAdapter teacherListAdapter;
    private ArrayList<TeachersModel> teacherlist = new ArrayList<>();
    private ApiInterface apiInterface;
    private Utils utils;
    private LinearLayout homeLayout;
    private ProgressUtil progressUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
            utils = new Utils();
            mActivity = getActivity();
            findViews(view);
            setData();
            getLocationCheck();
            onclickListener();

        return view;
    }

    private void getLocationCheck() {
        if (address != null) {
            getClasses();
        } else
            llLocation.performClick();
    }


    private void onclickListener() {
        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.setEnableDisbaleView(view, 2000);
                Intent intent = new Intent(getActivity(), GetSearchLocation.class);
                intent.putExtra("key", ACTIVITY_GET_LOCATION);
                startActivityForResult(intent, ACTIVITY_GET_LOCATION);

            }
        });

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.filter_class_batch);

                dialog.show();


            }
        });
    }

    private void findViews(View view) {
        imgFilter = view.findViewById(R.id.imgFilter);
        tvLocation = view.findViewById(R.id.tvLocation);
        llLocation = view.findViewById(R.id.llLocation);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        toolbar = view.findViewById(R.id.toolbar);
        recycleView = view.findViewById(R.id.teachersRecycler);
        layoutManager = new LinearLayoutManager(mActivity);
        recycleView.setLayoutManager(layoutManager);
    }


    private void setData() {
        setList();
        teacherListAdapter = new TeacherListAdapter(mActivity, teacherlist);
        recycleView.setAdapter(teacherListAdapter);
    }


    private void setList() {
        for (int i = 0; i < 10; i++) {
            TeachersModel teachersModel = new TeachersModel();
            if (i == 0) {
                teachersModel.setImage("http://edlanta.org/wp-content/uploads/2017/03/1389121022775.jpg");
            } else if (i == 1) {
                teachersModel.setImage("https://www.iaspaper.net/wp-content/uploads/2017/10/essay-on-teacher.jpg");
            } else {

                if (i % 2 == 0) {
                    teachersModel.setImage("https://uconn-today-universityofconn.netdna-ssl.com/wp-content/uploads/2014/05/MaleMathTeacher.jpg");
                } else {
                    teachersModel.setImage("https://d138zd1ktt9iqe.cloudfront.net/static/website_2_0/images/teacher_1.png");
                }
            }
            teacherlist.add(teachersModel);
        }
    }

    public void getList(Object[] object) {

    }

    private void getClasses() {
        try {

        } catch (Exception ex) {
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_HOME);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_GET_LOCATION) {
            if (data != null) {
                address = (Address) data.getSerializableExtra("obj");
                if (address != null) {
                    tvLocation.setText(address.getLocalAddress());
                }
            }
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
