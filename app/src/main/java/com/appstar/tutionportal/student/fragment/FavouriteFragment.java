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

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.RvFavouriteRecycler;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.UtilsStudent;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    private RecyclerView favoriteRecycler;
    private RvFavouriteRecycler rvFavouriteRecycler;
    private Activity mActivity;
    LinearLayoutManager favoriteLLm;
    private ArrayList<TeachersModel> teacherlist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        mActivity = getActivity();
        findView(view);
        setData();

        return view;
    }


    private void findView(View view) {
      favoriteRecycler = view.findViewById(R.id.favoriteRecycler);
        favoriteLLm = new LinearLayoutManager(mActivity);
        favoriteRecycler.setLayoutManager(favoriteLLm);
    }

    private void onClick() {
    }

    private void setData() {
        setList();
        rvFavouriteRecycler = new RvFavouriteRecycler(mActivity,teacherlist);
        favoriteRecycler.setAdapter(rvFavouriteRecycler);
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
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_HOME);
    }

}



