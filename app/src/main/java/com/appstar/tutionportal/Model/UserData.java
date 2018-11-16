
package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("customer")
    @Expose
    private TeacherDetail customer;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TeacherDetail getUserDeatil() {
        return customer;
    }

    public void setUserDetail(TeacherDetail customer) {
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
