package com.appstar.tutionportal.student.interfaces;

import retrofit2.Response;

public interface ApiResponse {

    void onResultSuccess(int method, Response response);

    void onResultFailed(int method, String response);

}
