package com.appstar.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.institute.activities.AddInstituteClass;
import com.appstar.tutionportal.institute.activities.InstituteDashboard;
import com.appstar.tutionportal.student.activity.StudentDashboard;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.TeacherDashboard;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.volley.RequestServer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssignSubjectToTeacher extends AppCompatActivity implements OnResponseListener {

    RequestServer requestServer;
    List<Subject> list = new ArrayList<>();
    TagFlowLayout tagFlowLayout;
    EditText etSearch;
    TextView tvAdd;
    ProgressBar progressBar, progressSet;

    int REQ_GET_SUBJECT = 1002, REQ_SET_SUBJECT = 1001;
    SharePreferenceData preferenceData;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_set_subject);
        setToolBar();
        initView();
        onClick();
        getSubject();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etSearch = findViewById(R.id.etSearch);
    }


    private void initView() {
        dbHelper = new DBHelper(getApplicationContext());
        preferenceData = new SharePreferenceData(this);
        requestServer = new RequestServer(this, this);
        tagFlowLayout = findViewById(R.id.id_flowlayout);
        tvAdd = findViewById(R.id.tvAdd);
        progressBar = findViewById(R.id.progressBar);
        progressSet = findViewById(R.id.progressSet);
    }

    private void onClick() {
        findViewById(R.id.llAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List list = getSelectedList();
                if (getSelectedList().size() > 0) {
                    tvAdd.setText("Adding...");
                    progressSet.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setSubject(list.toString());
                        }
                    }, 1000);

                }

            }
        });
    }

    private void getSubject() {
        progressBar.setVisibility(View.VISIBLE);
        requestServer.getRequest(UrlManager.GET_SUBJECT, REQ_GET_SUBJECT, false);
    }

    private void setSubject(String subjects) {
        try {
            subjects = subjects.substring(1, subjects.length() - 1);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("subject_id", subjects);
            //jsonObject.put("teacher_id", preferenceData.getUserId(getApplicationContext()));
            jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
            requestServer.sendStringPost(UrlManager.ADD_SUBJECT_TEACHER, jsonObject, REQ_SET_SUBJECT, false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setData() {
        tagFlowLayout.setAdapter(new TagAdapter<Subject>(list) {
            @Override
            public View getView(FlowLayout parent, int position, final Subject s) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tag_search_item,
                        tagFlowLayout, false);
                final CardView cardView = view.findViewById(R.id.cardView);
                TextView tvCategory = view.findViewById(R.id.tvCategory);
                tvCategory.setText(s.getSubject());
                if (s.isSelected()) {
                    tvCategory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    cardView.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                } else {
                    tvCategory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    cardView.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_200));
                }

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        s.setSelected(!s.isSelected());

                        notifyDataChanged();
                    }
                });

                return view;
            }
        });
    }

    private List<String> getSelectedList() {
        List<String> subjects = new ArrayList<>();
        for (Subject subject : list) {
            if (subject.isSelected()) {
                subjects.add(subject.getSubjectId());
            }
        }
        return subjects;
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        progressBar.setVisibility(View.GONE);
        progressSet.setVisibility(View.GONE);
        tvAdd.setText("Add");

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                if (reqCode == REQ_GET_SUBJECT) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            Subject subject = new Subject();
                            subject.setSubject(jsonObject.getString("subject_name"));
                            subject.setSubjectId(jsonObject.getString("subject_id"));
                            list.add(subject);
                        }
                        setData();
                    }
                } else {
                    tvAdd.setText("Successfully added");
                    SharePreferenceData.setSubjectAdded(getApplicationContext(), true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (SharePreferenceData.getUserType(getApplicationContext()).equals("student")) {
                                Data.setStudentDetail(dbHelper.getStudentDetail());
                                startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                            } else if (SharePreferenceData.getUserType(getApplicationContext()).equals("teacher")) {
                                Data.setTeacherDetail(dbHelper.getTeacherDetail());
                                startActivity(new Intent(getApplicationContext(), TeacherDashboard.class));
                            } else {
                                Data.setDirectorDetail(dbHelper.getDirectorDetail());
                                startActivity(new Intent(getApplicationContext(), AddInstituteClass.class));
                            }
                        }
                    }, 600);
                    finish();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {
        progressBar.setVisibility(View.GONE);
        progressSet.setVisibility(View.GONE);
        tvAdd.setText("Add");
    }
}
