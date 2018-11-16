package com.appstar.tutionportal.teacher.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appstar.tutionportal.Model.ImageList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.adapter.ViewPagerAdapter;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPagerImage extends Fragment implements OnResponseListener {
    ViewPager viewPager;
    String class_id;
    int REQ_CLASS = 198;
    RequestServer requestServer;
    ProgressUtil progressUtil;
    LinearLayout linNoData;
    private Activity mActivity;
    private PagerAdapter mPagerAdapter;
    private ViewPagerAdapter viewPagerAdapter;
    private Utils utils;
    private SharePreferenceData sharePreferenceData;
    private ArrayList<ImageList> imageList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_viewpagerimage, container, false);
        mActivity = getActivity();
        utils = new Utils();
        progressUtil = new ProgressUtil();
        sharePreferenceData = new SharePreferenceData();
        requestServer = new RequestServer(mActivity, this);
        viewPager = view.findViewById(R.id.pager);
        linNoData = view.findViewById(R.id.linNoData);

        //   viewPager.setPageTransformer(true, new ZoomOutTransformation());
        Bundle extras = getArguments();

        if (extras != null) {
            class_id = extras.getString("class_id");
        }
        callAPI();
        //    viewPager.setAdapter(new ViewPagerAdapter(mActivity);
        return view;

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
                    linNoData.setVisibility(View.GONE);
                    JSONObject c = jo1.getJSONObject(i);
                    ImageList imageModel = new ImageList();
                    imageModel.setCid(c.getString("cid"));
                    imageModel.setClass_id(c.getString("class_image_id"));
                    imageModel.setClass_image(c.getString("class_image"));
                    imageList.add(imageModel);
                }
               /* viewPagerAdapter = new ViewPagerAdapter(mActivity, imageList);
                viewPager.setAdapter(viewPagerAdapter);*/
            } else {
                linNoData.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
