package com.appstar.tutionportal.retrofit;

import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.model.CategoryResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET(UrlManager.GET_CATEGORIES)
    Call getTeachersList();


    @Headers("Content-Type: application/json")
    Call callApi(@Url String url,
                 @Body JSONObject jsonObject);
}
