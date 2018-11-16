
package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDetail {
    @SerializedName("teacher_id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("image")
    @Expose
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("bachelor_degree")
    @Expose
    private String bachelorDegree;
    @SerializedName("bachelor_degree_detail")
    @Expose
    private String bachelorDegreeDetail;
    @SerializedName("master_degree")
    @Expose
    private String masterDegree;
    @SerializedName("master_degree_datail")
    @Expose
    private String masterDegreeDatail;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("specialist")
    @Expose
    private String specialist;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("user_type")
    @Expose
    private String userType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBachelorDegree() {
        return bachelorDegree;
    }

    public void setBachelorDegree(String bachelorDegree) {
        this.bachelorDegree = bachelorDegree;
    }

    public String getBachelorDegreeDetail() {
        return bachelorDegreeDetail;
    }

    public void setBachelorDegreeDetail(String bachelorDegreeDetail) {
        this.bachelorDegreeDetail = bachelorDegreeDetail;
    }

    public String getMasterDegree() {
        return masterDegree;
    }

    public void setMasterDegree(String masterDegree) {
        this.masterDegree = masterDegree;
    }

    public String getMasterDegreeDatail() {
        return masterDegreeDatail;
    }

    public void setMasterDegreeDatail(String masterDegreeDatail) {
        this.masterDegreeDatail = masterDegreeDatail;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}