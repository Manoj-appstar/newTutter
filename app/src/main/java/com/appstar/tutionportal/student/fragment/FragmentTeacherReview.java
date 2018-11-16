package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.appstar.tutionportal.Model.TeacherReviewModel;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.TeacherReviewListAdapter;
import com.appstar.tutionportal.student.adapter.VpReviewAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.views.FadeOutTransformation;

import java.util.ArrayList;

public class FragmentTeacherReview extends Fragment {
    Activity mActivity;
    ImageView ivBack;
    ImageView ivThreeDots;
    RecyclerView rvLiReview;
    private UtilsStudent utilsStudent;
    private SharePreferenceData sharePreferenceData;
    private ProgressUtil progressUtil;
    private VpReviewAdapter vpReviewAdapter;
    private TeacherReviewListAdapter teacherReviewListAdapter;
    private ArrayList<TeacherReviewModel> teacherReviewModels = new ArrayList<>();
    private LinearLayoutManager llmTeacherReview;
    private ViewPager viewPager;
    LinearLayout llYourReview,llEditData;
    FrameLayout flRatingLayout;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_reveiw_teacher, container, false);
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

        ivBack = view.findViewById(R.id.ivBack);
        ivThreeDots = view.findViewById(R.id.ivThreeDots);
        rvLiReview = view.findViewById(R.id.rvLiReview);
        llmTeacherReview = new LinearLayoutManager(mActivity);
        rvLiReview.setLayoutManager(llmTeacherReview);
        flRatingLayout = view.findViewById(R.id.flRatingLayout);
        llYourReview = view.findViewById(R.id.llYourReview);
        llEditData = view.findViewById(R.id.llEditData);
        llEditData.setVisibility(View.GONE);
        sliderDotspanel = view.findViewById(R.id.SliderDots);
        viewPager = view.findViewById(R.id.vpPager);
        viewPager.setPageTransformer(true, new FadeOutTransformation());
        vpReviewAdapter = new VpReviewAdapter(mActivity);
        viewPager.setAdapter(vpReviewAdapter);

        dotscount = vpReviewAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_colorprimary));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isVisible()) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_colorprimary));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_TEACHER_PROFILE, FragmentNames._STUDENT_TEACHER_PROFILE, null, true);
            }
        });

        ivThreeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), ivThreeDots);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.review, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                llEditData.setVisibility(View.VISIBLE);
                                llYourReview.setVisibility(View.GONE);
                                return true;
                            case R.id.delete:

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();//showing popup menu
            }
        });


    }

    private void setData() {
        setList();
        teacherReviewListAdapter = new TeacherReviewListAdapter(mActivity, teacherReviewModels);
        rvLiReview.setAdapter(teacherReviewListAdapter);
    }

    private void setList() {
        for (int i = 0; i < 10; i++) {
            TeacherReviewModel teacherReviewModel = new TeacherReviewModel();
            if (i == 0) {
                teacherReviewModel.setImage("http://edlanta.org/wp-content/uploads/2017/03/1389121022775.jpg");
            } else if (i == 1) {
                teacherReviewModel.setImage("https://www.iaspaper.net/wp-content/uploads/2017/10/essay-on-teacher.jpg");
            } else {

                if (i % 2 == 0) {
                    teacherReviewModel.setImage("https://uconn-today-universityofconn.netdna-ssl.com/wp-content/uploads/2014/05/MaleMathTeacher.jpg");
                } else {
                    teacherReviewModel.setImage("https://d138zd1ktt9iqe.cloudfront.net/static/website_2_0/images/teacher_1.png");
                }
            }
            teacherReviewModels.add(teacherReviewModel);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_TEACHER_REVIEW);
    }
}
