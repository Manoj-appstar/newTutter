package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.RvTeacherProfileList;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;

import java.util.ArrayList;

public class TeacherProfile extends Fragment {
    ImageView ivBack, ivFavorite;
    TextView tvClassName;
    TextView tvReview;
    LinearLayout llReview;
    RatingBar ratingTeacher;
    RecyclerView rvClasses;
    TextView tvGraduationType, tvGraduationDetail, tvpostGraduationType, tvPostGraduationDetail, tvOthers, tvOthersDetail, tvTeacherName, tvTeacherEmail, tvNoFavorite;
    private Activity mActivity;
    private UtilsStudent utilsStudent;
    private SharePreferenceData sharePreferenceData;
    private ProgressUtil progressUtil;
    private RvTeacherProfileList rvTeacherProfileList;
    private ArrayList<TeachersModel> teacherlist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        mActivity = getActivity();
        utilsStudent = new UtilsStudent();
        sharePreferenceData = new SharePreferenceData();
        progressUtil = new ProgressUtil();
        findView(view);
        setData();
        onClick();

        return view;

    }

    private void findView(View view) {

        rvClasses = view.findViewById(R.id.rvClasses);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvClasses.setLayoutManager(layoutManager);
        tvGraduationType = view.findViewById(R.id.tvGraduationType);
        tvGraduationDetail = view.findViewById(R.id.tvGraduationDetail);
        tvpostGraduationType = view.findViewById(R.id.tvpostGraduationType);
        tvPostGraduationDetail = view.findViewById(R.id.tvPostGraduationDetail);
        tvOthers = view.findViewById(R.id.tvOthers);
        tvOthersDetail = view.findViewById(R.id.tvOthersDetail);
        tvTeacherName = view.findViewById(R.id.tvTeacherName);
        tvTeacherEmail = view.findViewById(R.id.tvTeacherEmail);
        tvNoFavorite = view.findViewById(R.id.tvNoFavorite);
        ratingTeacher = view.findViewById(R.id.ratingTeacher);
        tvReview = view.findViewById(R.id.tvReview);
        tvClassName  =view.findViewById(R.id.tvClassName);
        tvClassName.setText("Ayushman Classes");
        ivBack = view.findViewById(R.id.ivBack);

    }

    private void onClick() {
        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_TEACHER_REVIEW, FragmentNames._STUDENT_TEACHER_REVIEW, null, false);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });

    }

    private void setData() {

        setList();
        rvTeacherProfileList = new RvTeacherProfileList(mActivity, teacherlist);
        rvClasses.setAdapter(rvTeacherProfileList);

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

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_TEACHER_PROFILE);
    }
}