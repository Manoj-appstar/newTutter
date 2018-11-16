package com.appstar.tutionportal.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ClassDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("starting_date")
    @Expose
    private String startingDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("timing_from")
    @Expose
    private String timingFrom;
    @SerializedName("timing_to")
    @Expose
    private String timingTo;
    @SerializedName("week_days")
    @Expose
    private String weekDays;
    @SerializedName("student_limit")
    @Expose
    private String studentLimit;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("category_type")
    @Expose
    private String categoryType;
    @SerializedName("Institute name")
    @Expose
    private String instituteName;
    @SerializedName("Branch name")
    @Expose
    private String branchName;
    @SerializedName("Class Name")
    @Expose
    private String className;
    @SerializedName("Teacher Name")
    @Expose
    private String teacherName;
    @SerializedName("subjectname")
    @Expose
    private List<Subject> subjectname = null;
    @SerializedName("image")
    @Expose
    private List<ClassImage> classImage = null;

    public List<ClassImage> getClassImage() {
        return classImage;
    }

    public void setClassImage(List<ClassImage> classImage) {
        this.classImage = classImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTimingFrom() {
        return timingFrom;
    }

    public void setTimingFrom(String timingFrom) {
        this.timingFrom = timingFrom;
    }

    public String getTimingTo() {
        return timingTo;
    }

    public void setTimingTo(String timingTo) {
        this.timingTo = timingTo;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getStudentLimit() {
        return studentLimit;
    }

    public void setStudentLimit(String studentLimit) {
        this.studentLimit = studentLimit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<Subject> getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(List<Subject> subjectname) {
        this.subjectname = subjectname;
    }

}
