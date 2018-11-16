package com.appstar.tutionportal.student.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Dialog.DialogAskQuery;
import com.appstar.tutionportal.Dialog.DialogOtherFacility;
import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.RvClassAdapter;
import com.appstar.tutionportal.student.adapter.VpClassAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.model.TeachersModel;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;
import com.appstar.tutionportal.views.FadeOutTransformation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentViewTeacherInfo extends Fragment {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvShow, tvSeeOtherFacility, tvClassName;
    LinearLayout llShowLayout;
    ImageView ivSeeOtherFacility;
    RecyclerView vpClasses;
    ViewPager vpPager;
    LinearLayout sliderDotspanel;
    int currentPage = 0;
    Timer timer;
    TextView tvEmail, tvCall;
    ImageView ivBack, ivShare, ivFavorite;
    ImageView ivProfile;
    CardView cvAskNow;
    private Activity mActivity;
    private DialogOtherFacility dialogOtherFacility;
    private VpClassAdapter vpClassAdapter;
    private RvClassAdapter rvClassAdapter;
    private ArrayList<TeachersModel> teacherlist = new ArrayList<>();
    private ArrayList<ImageList> imageList = new ArrayList<>();
    private int dotscount;
    private ImageView[] dots;
    private ProgressUtil progressUtil;
    private UtilsStudent utilsStudent;
    private SharePreferenceData sharePreferenceData;
    private DialogAskQuery dialogAskQuery;
    TextView tvReview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_class_info, container, false);
        progressUtil = ProgressUtil.getInstance();

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_class_info);*/

        mActivity = getActivity();
        dialogOtherFacility = new DialogOtherFacility();
        utilsStudent = new UtilsStudent();
        sharePreferenceData = new SharePreferenceData();
        dialogAskQuery = new DialogAskQuery();

        findView(view);
        setData();
        onClick();
        return view;

    }

    private void findView(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivShare = view.findViewById(R.id.ivShare);
        ivFavorite = view.findViewById(R.id.ivFavorite);
        cvAskNow = view.findViewById(R.id.cvAskNow);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvShow = view.findViewById(R.id.tvShow);
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassName.setVisibility(View.GONE);
        tvSeeOtherFacility = view.findViewById(R.id.tvSeeOtherFacility);
        ivSeeOtherFacility = view.findViewById(R.id.ivSeeOtherFacility);
        llShowLayout = view.findViewById(R.id.llShowLayout);
        llShowLayout.setVisibility(View.GONE);
        vpClasses = view.findViewById(R.id.vpClasses);
        vpPager = view.findViewById(R.id.vpPager);
        vpPager.setPageTransformer(true, new FadeOutTransformation());
        sliderDotspanel = view.findViewById(R.id.SliderDots);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvCall = view.findViewById(R.id.tvCall);
        tvReview = view.findViewById(R.id.tvReview);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        vpClasses.setLayoutManager(layoutManager);

        tvShow.setText("Show More");
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

    private void setData() {
        setList();
        rvClassAdapter = new RvClassAdapter(mActivity, teacherlist);
        vpClasses.setAdapter(rvClassAdapter);
        vpClassAdapter = new VpClassAdapter(mActivity, teacherlist);
        vpPager.setAdapter(vpClassAdapter);

        dotscount = teacherlist.size();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));


        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isVisible()) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dot_colorprimary));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dafault_dot));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == teacherlist.size() - 1) {
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
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvShow.getText().toString().equalsIgnoreCase("Show More")) {
                    tvShow.setText("Show Less...");
                    llShowLayout.setVisibility(View.VISIBLE);
                } else {
                    tvShow.setText("Show More...");
                    llShowLayout.setVisibility(View.GONE);
                }
            }
        });

        tvSeeOtherFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOtherFacility.showDialog(mActivity);

            }
        });

        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_TEACHER_REVIEW, FragmentNames._STUDENT_TEACHER_REVIEW, null, false);
                }
        });

        cvAskNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAskQuery.showDialog(mActivity);

            }
        });

        ivSeeOtherFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOtherFacility.showDialog(mActivity);

            }
        });

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jimblackler@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Type Your Mail");
                intent.putExtra(Intent.EXTRA_TEXT, "hi");
                mActivity.startActivity(intent);

            }
        });

        tvCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:09639857411"));
                    startActivity(intent);
                   /* Intent intentCall11 = new Intent(Intent.ACTION_CALL);
                    intentCall11.setData(Uri.parse("tel:" + "9876543210"));
                    mActivity.startActivity(intentCall11);*/
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                navigateToShareScreen(UrlManager.APP_URL);

            }
        });
    }

    public void navigateToShareScreen(String shareUrl) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl + " -via " + getString(R.string.app_name));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Share applications not found!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_CLASS_INFO);
    }
}
