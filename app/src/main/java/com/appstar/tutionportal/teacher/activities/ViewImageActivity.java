package com.appstar.tutionportal.teacher.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.adapter.ViewPagerAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.ZoomOutTransformation;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewImageActivity extends AppCompatActivity implements OnResponseListener {
    ViewPager viewPager;

    String class_id;
    int REQ_CLASS = 198;
    RequestServer requestServer;
    LinearLayout sliderDotspanel,linNoData;
    ClassDetail classDetail;
    private Activity mActivity;
    private PagerAdapter mPagerAdapter;
    private ViewPagerAdapter viewPagerAdapter;
    private Utils utils;
    private SharePreferenceData sharePreferenceData;
    private ArrayList<ImageList> imageList = new ArrayList<>();
    private int dotscount;
    private ImageView[] dots;
    LinearLayout showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpagerimage);

        mActivity = this;
        utils = new Utils();
        sharePreferenceData = new SharePreferenceData();
        requestServer = new RequestServer(mActivity, this);
        viewPager = findViewById(R.id.pager);
        sliderDotspanel = findViewById(R.id.SliderDots);
     //   showData = findViewById(R.id.showData);
        linNoData = findViewById(R.id.linNoData);
        linNoData.setVisibility(View.GONE);
        getData();

        callAPI();
    }

    private void getData() {
        class_id = getIntent().getStringExtra("class_id");
        if (Data.getClassList().size() > 0) {
            ClassDetail classDetail = getFilterClassByBranchId();
            bindData(classDetail);
        } else {
        }
    }

    private void bindData(ClassDetail classDetail) {
        if (classDetail.getClassImage()!=null && classDetail.getClassImage().size() > 0) {
          //  showData.setVisibility(View.VISIBLE);
            linNoData.setVisibility(View.GONE);
            viewPager.setPageTransformer(true, new ZoomOutTransformation());
            viewPagerAdapter = new ViewPagerAdapter(mActivity, classDetail.getClassImage());
            viewPager.setAdapter(viewPagerAdapter);


            dotscount = classDetail.getClassImage().size();
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dafault_dot));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dafault_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }else {
          //  showData.setVisibility(View.GONE);
            linNoData.setVisibility(View.VISIBLE);
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

    private void callAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("class_id", class_id);
            requestServer.sendStringPost(UrlManager.GET_TEACHER_CLASS_IMAGE, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jo1 = jsonObject.getJSONArray("data");
            if (jo1.length() > 0) {
                for (int i = 0; i < jo1.length(); i++) {
                    JSONObject c = jo1.getJSONObject(i);
                    ImageList imageModel = new ImageList();
                    imageModel.setCid(c.getString("cid"));
                    imageModel.setClass_id(c.getString("class_image_id"));
                    imageModel.setClass_image(c.getString("class_image"));
                    imageList.add(imageModel);

                }
              /*  viewPagerAdapter = new ViewPagerAdapter(mActivity, imageList);
                viewPager.setAdapter(viewPagerAdapter);

                dotscount = imageList.size();
                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {
                    dots[i] = new ImageView(this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dafault_dot));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);
                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for (int i = 0; i < dotscount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dafault_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        }
                });*/
            } else {

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        this.finish();
        return true;
    }
}

