package com.appstar.tutionportal.teacher.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.VpClassAdapter;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.teacher.adapter.ViewPagerAdapter;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.FadeOutTransformation;
import com.appstar.tutionportal.views.ZoomOutTransformation;

import java.util.ArrayList;
import java.util.Timer;

public class FragmentViewTeacherClass extends Fragment {
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvShow, tvClassName;
    LinearLayout llShowLayout;
    RecyclerView vpClasses;
    ViewPager vpPager;
    LinearLayout sliderDotspanel;
    int currentPage = 0;
    Timer timer;
    ImageView ivBack, ivEdit;
    ImageView ivProfile;
    TextView tvReview;
    String class_id;
    Utils utils;
    private ViewPagerAdapter viewPagerAdapter;
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

        if (extras != null) {
            class_id = extras.getString("class_id");
        }
        findView(view);
        onClick();
        return view;
    }

    private void findView(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivEdit = view.findViewById(R.id.ivEdit);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvShow = view.findViewById(R.id.tvShow);
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassName.setVisibility(View.GONE);
        llShowLayout = view.findViewById(R.id.llShowLayout);
        llShowLayout.setVisibility(View.GONE);

        vpPager = view.findViewById(R.id.vpPager);
        vpPager.setPageTransformer(true, new FadeOutTransformation());
        sliderDotspanel = view.findViewById(R.id.SliderDots);
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

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                utils.openFragment(mActivity, FragmentNames.STUDENT_HOME, FragmentNames._STUDENT_HOME, null, true);
            }
        });


    }

    private void bindData(ClassDetail classDetail) {
        if (classDetail.getClassImage() != null && classDetail.getClassImage().size() > 0) {
            //  showData.setVisibility(View.VISIBLE);
            vpPager.setPageTransformer(true, new ZoomOutTransformation());
            viewPagerAdapter = new ViewPagerAdapter(mActivity, classDetail.getClassImage());
            vpPager.setAdapter(viewPagerAdapter);


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
        } else {

        }
    }

}
