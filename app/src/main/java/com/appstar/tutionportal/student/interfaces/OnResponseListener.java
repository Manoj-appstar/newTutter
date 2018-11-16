package com.appstar.tutionportal.student.interfaces;

public interface OnResponseListener {
    void onSuccess(int reqCode, String response);
    void onFailed(int  reqCode, String response);

}
