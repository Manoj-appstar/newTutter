package com.appstar.tutionportal.teacher.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.VpClassAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.teacher.adapter.ViewClassImageAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.FadeOutTransformation;
import com.appstar.tutionportal.views.ZoomOutTransformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentViewTeacherClass extends Fragment {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvShow, tvClassName;
    LinearLayout llShowLayout;
    ViewPager vpPager;
    LinearLayout sliderDotspanel;
    int currentPage = 0;
    Timer timer;
    ImageView ivBack, ivEdit;
    TextView tvReview;
    String class_id;
    Utils utils;
    TextView tvClassRupeee, tvBatchName, tvAddress, tvViewStudent, tvClassPhoneNumber, tvClass, tvStatus, tvSubject,
            tvFullAddres, tvStartingTime, tvClosingTime, tvTotalNumberSeats, tvAvailableSeats, tvWeekDays;
    RatingBar ratingTeacher;
    ImageView ivViewStudent;
    List<String> weekDaysList;
    String strWeek;
    private ViewClassImageAdapter viewClassImageAdapter;
    private Activity mActivity;
    private VpClassAdapter vpClassAdapter;
    private ArrayList<ImageList> imageList = new ArrayList<>();
    private int dotscount;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_viewteacher_class, container, false);
        Bundle extras = getArguments();
        utils = new Utils();
        mActivity = getActivity();

        if (extras != null) {
            class_id = extras.getString("class_id");
        }

        findView(view);
        onClick();
        getData();

        return view;
    }

    private void getData() {
        if (Data.getClassList().size() > 0) {
            ClassDetail classDetail = getFilterClassByBranchId();
            bindData(classDetail);
        } else {
        }
    }

    private ClassDetail getFilterClassByBranchId() {
        ClassDetail classDetail = null;
        for (ClassDetail obj : Data.getClassList()) {
            if (obj.getId().equals(class_id)) {
                classDetail = obj;
            }
        }
        return classDetail;
    }


    private void findView(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivEdit = view.findViewById(R.id.ivEdit);

        tvClassRupeee = view.findViewById(R.id.tvClassRupeee);
        tvBatchName = view.findViewById(R.id.tvBatchName);
        tvAddress = view.findViewById(R.id.tvAddress);
        ratingTeacher = view.findViewById(R.id.ratingTeacher);

        ivViewStudent = view.findViewById(R.id.ivViewStudent);
        tvViewStudent = view.findViewById(R.id.tvViewStudent);
        tvClassPhoneNumber = view.findViewById(R.id.tvClassPhoneNumber);

        tvClass = view.findViewById(R.id.tvClass);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvSubject = view.findViewById(R.id.tvSubject);
        tvFullAddres = view.findViewById(R.id.tvFullAddres);

        tvStartingTime = view.findViewById(R.id.tvStartingTime);
        tvClosingTime = view.findViewById(R.id.tvClosingTime);
        tvTotalNumberSeats = view.findViewById(R.id.tvTotalNumberSeats);
        tvAvailableSeats = view.findViewById(R.id.tvAvailableSeats);
        tvWeekDays = view.findViewById(R.id.tvWeekDays);

        tvShow = view.findViewById(R.id.tvShow);
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassName.setVisibility(View.GONE);
        llShowLayout = view.findViewById(R.id.llShowLayout);
        //   llShowLayout.setVisibility(View.GONE);

        vpPager = view.findViewById(R.id.vpPager);
        vpPager.setPageTransformer(true, new FadeOutTransformation());
        sliderDotspanel = view.findViewById(R.id.SliderDots);
        tvReview = view.findViewById(R.id.tvReview);

        tvShow.setText("Show More");
        tvShow.setVisibility(View.GONE);
        final Toolbar mToolbar = view.findViewById(R.id.toolbar);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);

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
                    tvClassName.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {

                    tvClassName.setVisibility(View.GONE);
                    isShow = false;

                }
            }
        });
    }

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
            }
        });
    }

    private void bindData(final ClassDetail classDetail) {
        tvClassName.setText(classDetail.getPrice() + "," + classDetail.getBatchName());
        tvClassRupeee.setText("₹" + classDetail.getPrice());
        tvBatchName.setText(classDetail.getBatchName());
        tvAddress.setText(classDetail.getLandmark());

        tvClassPhoneNumber.setText("+91-" + classDetail.getPhone());

        tvClass.setText(classDetail.getClassName());
//        tvSubject.setText((CharSequence) classDetail.getSubjectname());
        tvFullAddres.setText(classDetail.getAddress());

        tvStartingTime.setText(classDetail.getTimingFrom());
        tvClosingTime.setText(classDetail.getTimingTo());
        tvTotalNumberSeats.setText(classDetail.getStudentLimit());
        weekDaysList = Arrays.asList(classDetail.getWeekDays().split(","));
        //    weeks = classDetail.getWeekDays().split(",");
        try {
            for (int i = 0; i <= weekDaysList.size(); i++) {
                if (weekDaysList.get(i).equalsIgnoreCase("1")) {
                    if (strWeek == null) {
                        strWeek = "Monday,";
                    } else {
                        strWeek += "Monday,";
                    }
                } else if (weekDaysList.get(i).equalsIgnoreCase("2")) {
                    if (strWeek == null) {
                        strWeek = "Tuesday,";
                    } else {
                        strWeek += "Tuesday,";
                    }

                } else if (weekDaysList.get(i).equalsIgnoreCase("3")) {
                    if (strWeek == null) {
                        strWeek = "Wednesday,";
                    } else {
                        strWeek += "Wednesday,";
                    }
                } else if (weekDaysList.get(i).equalsIgnoreCase("4")) {
                    if (strWeek == null) {
                        strWeek = "Thursday,";
                    } else {
                        strWeek += "Thursday,";
                    }
                } else if (weekDaysList.get(i).equalsIgnoreCase("5")) {
                    if (strWeek == null) {
                        strWeek = "Friday,";
                    } else {
                        strWeek += "Friday,";
                    }
                } else if (weekDaysList.get(i).equalsIgnoreCase("6")) {
                    if (strWeek == null) {
                        strWeek = "Saturday,";
                    } else {
                        strWeek += "Saturday,";
                    }
                } else if (weekDaysList.get(i).equalsIgnoreCase("7")) {
                    if (strWeek == null) {
                        strWeek = "Sunday,";
                    } else {
                        strWeek += "Sunday,";
                    }
                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (strWeek.endsWith(",")) {
            strWeek = strWeek.substring(0, strWeek.length() - 1);
            tvWeekDays.setText(strWeek);
        }


        if (classDetail.getClassImage() != null && classDetail.getClassImage().size() > 0) {
            //  showData.setVisibility(View.VISIBLE);
            vpPager.setPageTransformer(true, new ZoomOutTransformation());
            viewClassImageAdapter = new ViewClassImageAdapter(mActivity, classDetail.getClassImage());
            vpPager.setAdapter(viewClassImageAdapter);


            dotscount = classDetail.getClassImage().size();
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {
                dots[i] = new ImageView(getActivity());
                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_dot));

            vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == classDetail.getClassImage().size() - 1) {
                        currentPage = 0;
                    }
                    vpPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        } else {

        }
    }


}
