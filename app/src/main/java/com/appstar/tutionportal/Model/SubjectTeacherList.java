package com.appstar.tutionportal.Model;

public class SubjectTeacherList {
    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getTeacherImage() {
        return TeacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        TeacherImage = teacherImage;
    }

    String TeacherName;
    String TeacherId;
    String TeacherImage;
}
