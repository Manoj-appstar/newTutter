package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDetail {

    @SerializedName("student_id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("pursuing")
    @Expose
    private String pursuing;
    @SerializedName("pursuing_detail")
    @Expose
    private String pursuingDetail;
    @SerializedName("bachelor_degree")
    @Expose
    private String bachelorDegree;
    @SerializedName("bachelor_degree_detail")
    @Expose
    private String bachelorDegreeDetail;
    @SerializedName("master_degree")
    @Expose
    private String masterDegree;
    @SerializedName("master_degree_detail")
    @Expose
    private String masterDegreeDetail;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("specialist")
    @Expose
    private String specialist;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("fcm_id")
    @Expose
    private String fcmId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPursuing() {
        return pursuing;
    }

    public void setPursuing(String pursuing) {
        this.pursuing = pursuing;
    }

    public String getPursuingDetail() {
        return pursuingDetail;
    }

    public void setPursuingDetail(String pursuingDetail) {
        this.pursuingDetail = pursuingDetail;
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

    public String getMasterDegreeDetail() {
        return masterDegreeDetail;
    }

    public void setMasterDegreeDetail(String masterDegreeDetail) {
        this.masterDegreeDetail = masterDegreeDetail;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}