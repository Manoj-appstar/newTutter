package com.appstar.tutionportal.retrofit;

import com.appstar.tutionportal.student.extras.UrlManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    private static Retrofit getClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;

    }

    public static ApiInterface getInterfaceInstance() {
        return getClient().create(ApiInterface.class);
    }

}
