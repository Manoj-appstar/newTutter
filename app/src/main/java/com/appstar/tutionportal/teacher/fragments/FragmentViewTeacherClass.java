package com.appstar.tutionportal.teacher.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.VpClassAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.adapter.ViewClassImageAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.FadeOutTransformation;
import com.appstar.tutionportal.views.ZoomOutTransformation;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentViewTeacherClass extends AppCompatActivity implements OnResponseListener {
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
    TextView tvEditClass;
    String strWeek, from;
    Switch aSwitch;
    RequestServer requestServer;
    int REQ_SERVICES = 198;
    private ViewClassImageAdapter viewClassImageAdapter;
    private Activity mActivity;
    private VpClassAdapter vpClassAdapter;
    private ArrayList<ImageList> imageList = new ArrayList<>();
    private int dotscount;
    private ImageView[] dots;
    private TextView tvEnable, tvDisable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewteacher_class);

   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_viewteacher_class, container, false);*/
        //  Bundle extras = getArguments();
        utils = new Utils();
        mActivity = FragmentViewTeacherClass.this;
        requestServer = new RequestServer(this, this);

        /*if (extras != null) {
            class_id = extras.getString("class_id");
        }*/

        class_id = getIntent().getStringExtra("class_id");
        from = getIntent().getStringExtra("from");

        // findView(view);
        findView();
        onClick();
        getData();

        //  return view;
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

    private void findView() {
        ivBack = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        tvEditClass = findViewById(R.id.tvEditClass);

        tvClassRupeee = findViewById(R.id.tvClassRupeee);
        tvBatchName = findViewById(R.id.tvBatchName);
        tvAddress = findViewById(R.id.tvAddress);
        ratingTeacher = findViewById(R.id.ratingTeacher);
        aSwitch = findViewById(R.id.switch1);
        tvEnable = findViewById(R.id.tvEnable);
        tvDisable = findViewById(R.id.tvDisable);
        tvEnable.setText("Enable");
        tvDisable.setText("Disable");


        ivViewStudent = findViewById(R.id.ivViewStudent);
        tvViewStudent = findViewById(R.id.tvViewStudent);
        tvClassPhoneNumber = findViewById(R.id.tvClassPhoneNumber);

        tvClass = findViewById(R.id.tvClass);
        tvStatus = findViewById(R.id.tvStatus);
        tvSubject = findViewById(R.id.tvSubject);
        tvFullAddres = findViewById(R.id.tvFullAddres);

        tvStartingTime = findViewById(R.id.tvStartingTime);
        tvClosingTime = findViewById(R.id.tvClosingTime);
        tvTotalNumberSeats = findViewById(R.id.tvTotalNumberSeats);
        tvAvailableSeats = findViewById(R.id.tvAvailableSeats);
        tvWeekDays = findViewById(R.id.tvWeekDays);

        tvShow = findViewById(R.id.tvShow);
        tvClassName = findViewById(R.id.tvClassName);
        tvClassName.setVisibility(View.GONE);
        llShowLayout = findViewById(R.id.llShowLayout);
        //   llShowLayout.setVisibility(View.GONE);

        vpPager = findViewById(R.id.vpPager);
        vpPager.setPageTransformer(true, new FadeOutTransformation());
        sliderDotspanel = findViewById(R.id.SliderDots);
        tvReview = findViewById(R.id.tvReview);

        tvShow.setText("Show More");
        tvShow.setVisibility(View.GONE);
        final Toolbar mToolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        AppBarLayout mAppBarLayout = findViewById(R.id.appbar);
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
                finish();
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, AddClasses.class);
                intent.putExtra("class", "editClass");
                intent.putExtra("class_id", class_id);
                intent.putExtra("editFrom", from);
                startActivity(intent);
            }
        });

        tvEditClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, AddClasses.class);
                intent.putExtra("class", "editClass");
                intent.putExtra("class_id", class_id);
                intent.putExtra("editFrom", from);
                startActivity(intent);
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchApi();
            }
        });
    }

    private void switchApi() {
        //        RetrofitCall.CallHome(HomeFragment.this, apiInterface);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("batch_id", class_id);
            if (aSwitch.isChecked()) {
                jsonObject.put("status", "0");
            } else {
                jsonObject.put("status", "1");
            }
            requestServer.sendStringPost(UrlManager.UPDATE_TEACHER_SERVICES, jsonObject, REQ_SERVICES, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bindData(final ClassDetail classDetail) {

        if (classDetail.getServices().equalsIgnoreCase("1")) {
            aSwitch.setChecked(false);
            tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
            tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
            tvDisable.setText("Disabled");
            tvEnable.setText("Enable");
            tvStatus.setText("Close");
        } else {
            aSwitch.setChecked(true);
            tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
            tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
            tvDisable.setText("Disable");
            tvEnable.setText("Enabled");
            tvStatus.setText("Open");
        }
        String subjects = "";
        for (Subject subject : classDetail.getSubjectname()) {
            subjects += subject.getSubject() + " ,";
        }
        if (subjects.length() > 1)
            tvSubject.setText(subjects.substring(0, subjects.length() - 1));


        tvClassName.setText("₹" + classDetail.getPrice() + "," + classDetail.getBatchName());
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
                dots[i] = new ImageView(mActivity);
                dots[i].setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.dafault_dot));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.selected_dot));

            vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.dafault_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.selected_dot));
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

    @Override
    public void onResume() {
        super.onResume();
        Utils.setCurrentScreen(FragmentNames._VIEW_TEACHER_CLASS_INFO);
        //   mActivity.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void updateClass(final ClassDetail classDetail) {
        if (aSwitch.isChecked()) {
            classDetail.setServices("0");
            //   Data.getTeacherDetail().setServices("0");
            //sharePreferenceData.setTeacherServices(mActivity, "1");
            Snackbar.make(aSwitch, "Your service for this class has been closed", Snackbar.LENGTH_SHORT).show();
            aSwitch.setChecked(true);
            tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
            tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
            tvDisable.setText("Disable");
            tvEnable.setText("Enabled");
            tvStatus.setText("Open");
        } else {
            classDetail.setServices("1");
            //sharePreferenceData.seetTeacherServices(mActivity, "0");
            Snackbar.make(aSwitch, "Your service for this class has been open", Snackbar.LENGTH_SHORT).show();
            aSwitch.setChecked(false);
            tvDisable.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
            tvEnable.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
            tvDisable.setText("Disabled");
            tvEnable.setText("Enable");
            tvStatus.setText("Close");
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        boolean bool = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("status")) {
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    bool = false;
                    if (Data.getClassList().size() > 0) {
                        ClassDetail classDetail = getFilterClassByBranchId();
                        updateClass(classDetail);
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
