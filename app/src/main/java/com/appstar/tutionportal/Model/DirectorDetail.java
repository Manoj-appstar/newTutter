package com.appstar.tutionportal.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DirectorDetail {
    @SerializedName("director_id")
    @Expose
    private Integer directorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("device_id")
    @Expose
    private Object deviceId;
    @SerializedName("device_type")
    @Expose
    private Object deviceType;
    @SerializedName("fcm_id")
    @Expose
    private Object fcmId;
    @SerializedName("bachelor_degree")
    @Expose
    private Object bachelorDegree;
    @SerializedName("bachelor_degree_detail")
    @Expose
    private Object bachelorDegreeDetail;
    @SerializedName("master_degree")
    @Expose
    private Object masterDegree;
    @SerializedName("master_degree_detail")
    @Expose
    private Object masterDegreeDetail;
    @SerializedName("other")
    @Expose
    private Object other;
    @SerializedName("specialist")
    @Expose
    private Object specialist;
    @SerializedName("dob")
    @Expose
    private Object dob;

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    public Object getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Object deviceType) {
        this.deviceType = deviceType;
    }

    public Object getFcmId() {
        return fcmId;
    }

    public void setFcmId(Object fcmId) {
        this.fcmId = fcmId;
    }

    public Object getBachelorDegree() {
        return bachelorDegree;
    }

    public void setBachelorDegree(Object bachelorDegree) {
        this.bachelorDegree = bachelorDegree;
    }

    public Object getBachelorDegreeDetail() {
        return bachelorDegreeDetail;
    }

    public void setBachelorDegreeDetail(Object bachelorDegreeDetail) {
        this.bachelorDegreeDetail = bachelorDegreeDetail;
    }

    public Object getMasterDegree() {
        return masterDegree;
    }

    public void setMasterDegree(Object masterDegree) {
        this.masterDegree = masterDegree;
    }

    public Object getMasterDegreeDetail() {
        return masterDegreeDetail;
    }

    public void setMasterDegreeDetail(Object masterDegreeDetail) {
        this.masterDegreeDetail = masterDegreeDetail;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public Object getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Object specialist) {
        this.specialist = specialist;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

}