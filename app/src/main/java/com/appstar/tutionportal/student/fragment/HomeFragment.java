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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appstar.common.GetSearchLocation;
import com.appstar.common.model.Address;
import com.appstar.common.model.FilterClass;
import com.appstar.tutionportal.Model.ClassDataDetail;
import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.retrofit.ApiInterface;
import com.appstar.tutionportal.student.adapter.StudentClassAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.ApiResponse;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnResponseListener {

    private static final float APPBAR_ELEVATION = 10f;
    public Address address;
    int MAX_SIZE = 10;
    ApiResponse apiResponse;
    Toolbar toolbar;
    int firstVisibleInListview;
    AppBarLayout appBarLayout;
    LinearLayout llLocation;
    ImageView imgFilter;
    TextView tvLocation;
    int ACTIVITY_GET_LOCATION = 678;
    FilterClass filterClass = new FilterClass();
    RequestServer requestServer;
    int REQ_GET_CLASS = 12345;
    ProgressBar progressMoreLoad;
    boolean loading, isDataCompleted;
    static int page = 1;
    private Activity mActivity;
    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private StudentClassAdapter adapter;
    private ArrayList<TeachersModel> teacherlist = new ArrayList<>();
    private List<ClassDetail> classList = new ArrayList<>();
    private ApiInterface apiInterface;
    private Utils utils;
    private LinearLayout homeLayout;
    private ProgressUtil progressUtil;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        utils = new Utils();
        mActivity = getActivity();
        initViews(view);
        setData();
        onclickListener();
        onScrollListener();
        getLocationCheck();
        return view;
    }

    private void initViews(View view) {
        dbHelper = new DBHelper(getActivity());
        requestServer = new RequestServer(getActivity(), this);
        progressMoreLoad = view.findViewById(R.id.progressMoreLoad);
        imgFilter = view.findViewById(R.id.imgFilter);
        tvLocation = view.findViewById(R.id.tvLocation);
        llLocation = view.findViewById(R.id.llLocation);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        toolbar = view.findViewById(R.id.toolbar);
        recycleView = view.findViewById(R.id.teachersRecycler);
        layoutManager = new LinearLayoutManager(mActivity);
        recycleView.setLayoutManager(layoutManager);
    }

    private void getLocationCheck() {
        address = dbHelper.getLastLocation();
        if (address != null) {
            tvLocation.setText(address.getLocalAddress());
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
                intent.putExtra("from", "search");
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


    private void setData() {
        //setList();
        adapter = new StudentClassAdapter(mActivity, classList);
        recycleView.setAdapter(adapter);
    }

    private void onScrollListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                // Load more if we have reach the end to the recyclerView
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    //      loadMoreItems();
                    if (!loading && !isDataCompleted) {
                        getClasses();
                    }
                }

            }
        });
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

    private void getClasses() {
        try {
            if (Data.getClassList().size() <= 0) {
                loadData();
            } else {
                classList = Data.getClassList();
                setData();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            progressMoreLoad.setVisibility(View.GONE);
        }

    }

    private void loadData() {
        try {
                progressMoreLoad.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                if (!TextUtils.isEmpty(filterClass.getCity()))
                    jsonObject.put("city", filterClass.getCity());
                else if (TextUtils.isEmpty(filterClass.getInstitute()))
                    jsonObject.put("institute", filterClass.getInstitute());
                else if (!TextUtils.isEmpty(filterClass.getSubject()))
                    jsonObject.put("subject_id", filterClass.getSubject());
                jsonObject.put("latitude", address.getLatitude());
                jsonObject.put("longitude", address.getLongitude());
                jsonObject.put("page", page);
                jsonObject.put("student_id", SharePreferenceData.getUserId(getActivity()));
                requestServer.sendStringPostWithHeader(UrlManager.GET_ALL_FILTER_CLASS, jsonObject, REQ_GET_CLASS, false);
                page++;


        } catch (Exception ex) {
            ex.printStackTrace();
            progressMoreLoad.setVisibility(View.GONE);
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
                dbHelper.insertLastLocation(address);
                if (address != null) {
                    tvLocation.setText(address.getLocalAddress());
                    getClasses();
                }
            }
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        if (reqCode == REQ_GET_CLASS) {
            progressMoreLoad.setVisibility(View.GONE);
            try {
                Gson gson = new Gson();
                Type category = new TypeToken<ClassDataDetail>() {
                }.getType();
                ClassDataDetail userDetail = gson.fromJson(response, category);
                if (userDetail != null) {
                    if (userDetail.getData() == null) {
                        List<ClassDetail> sList = new ArrayList<>();
                        userDetail.setData(sList);
                    }
                    if (userDetail.getData().size() < MAX_SIZE) {
                        isDataCompleted = true;
                    }
                    classList.addAll(userDetail.getData());
                    setData();
                    loading = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {
        if (reqCode == REQ_GET_CLASS) {
            progressMoreLoad.setVisibility(View.GONE);
        }
    }
}
