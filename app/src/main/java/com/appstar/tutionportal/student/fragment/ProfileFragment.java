package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.Dialog.DialogPhone;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.ProfilePagerAdapter;
import com.appstar.tutionportal.student.adapter.TeacherListAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.views.MyViewPager;
import com.appstar.tutionportal.volley.RequestServer;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    protected TabLayout profileTab;
    EditText etPassword, etMobile;
    TextView txtSpecialization;
    Button btnRegister;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RequestServer requestServer;
    int REQ_TYPE_LOGIN = 678;
    LinearLayout llText, profile;
    ImageView ivProfile;
    TextView tvUserName;
    RecyclerView rvLiClass;
    TeacherListAdapter teacherListAdapter;
    LinearLayoutManager teacherLLM;
    TextView tvUserNamed, tvUserEmail;
    ImageView ivMainProfile;
    ImageView ivEditProfile;
    TextView tvUserEmail1;
    private Activity mActivity;
    private MyViewPager profilePager;
    private ProfilePagerAdapter profilePagerAdapter;
    private ArrayList<TeachersModel> teacherlist = new ArrayList<>();
    private UtilsStudent utilsStudent;
    private DialogPhone dialogPhone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mActivity = getActivity();
        utilsStudent = new UtilsStudent();
        findViews(view);
        setData();
        onClick();

        return view;
    }

    private void findViews(View view) {

        final Toolbar mToolbar = view.findViewById(R.id.anim_toolbar);
        //   ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserName.setText("Manoj Pandey");
        tvUserName.setVisibility(View.GONE);
        ivProfile = view.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        rvLiClass = view.findViewById(R.id.rvLiClass);
        teacherLLM = new LinearLayoutManager(mActivity);
        rvLiClass.setLayoutManager(teacherLLM);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserNamed = view.findViewById(R.id.tvUserNamed);
        ivMainProfile = view.findViewById(R.id.ivMainProfile);
        ivEditProfile = view.findViewById(R.id.ivEditProfile);
        tvUserEmail1 = view.findViewById(R.id.tvUserEmail1);
        tvUserEmail1.setVisibility(View.GONE);
        //  getSupportActionBar().setTitle("");
        //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        AppBarLayout mAppBarLayout = view.findViewById(R.id.appbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                int value = scrollRange + verticalOffset;
                if (value <= 50) {
                    tvUserName.setVisibility(View.VISIBLE);
                    ivProfile.setVisibility(View.VISIBLE);
                    tvUserNamed.setVisibility(View.GONE);
                    tvUserEmail.setVisibility(View.GONE);
                    ivMainProfile.setVisibility(View.GONE);
                    tvUserEmail1.setVisibility(View.VISIBLE);
                    isShow = true;

                } else if (isShow) {

                    tvUserName.setVisibility(View.GONE);
                    ivProfile.setVisibility(View.GONE);
                    ivMainProfile.setVisibility(View.VISIBLE);
                    tvUserNamed.setVisibility(View.VISIBLE);
                    tvUserEmail.setVisibility(View.VISIBLE);
                    tvUserEmail1.setVisibility(View.GONE);
                    isShow = false;

                }
            }
        });

    }

    private void setData() {
        setList();
        teacherListAdapter = new TeacherListAdapter(mActivity, teacherlist);
        rvLiClass.setAdapter(teacherListAdapter);
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

    private void onClick() {
        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_EDIT, FragmentNames._STUDENT_EDIT, null, false);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_HOME);
    }
}
