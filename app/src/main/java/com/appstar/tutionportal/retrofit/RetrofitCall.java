package com.appstar.tutionportal.retrofit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.appstar.tutionportal.student.interfaces.ApiResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCall {

    private static Object[] objects = new Object[2];
    //    private Context context;
    private ApiResponse apiResponse;
    private int method;

    public RetrofitCall(ApiResponse context, int method) {
//        this.context = context;
        this.method = method;
        apiResponse =  context;
    }



    public void CallHome(ApiInterface apiInterface, final int method) {
        this.method = method;
        Call call1 = apiInterface.getTeachersList();
        call1.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (call.isExecuted()) {
                    apiResponse.onResultSuccess(method, response);
                } else {
                    apiResponse.onResultFailed(method, "Some Error Occurred");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("FAILED", ":::  " + t.getMessage());
                apiResponse.onResultFailed(method, "Some Error Occurred");
            }
        });

    }


    public void callApiPost(ApiInterface apiInterface, JSONObject jsonObject, String url) {

        Call call = apiInterface.callApi(url, jsonObject);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String s = response.body().toString();
                if (call.isExecuted()) {
                    apiResponse.onResultSuccess(method, response);
                } else {
                    apiResponse.onResultFailed(method, "Some Error Occurred");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("FAILED", ":::  " + t.getMessage());
                apiResponse.onResultFailed(method, "Some Error Occurred");
            }
        });
    }


}
