package com.appstar.tutionportal.student.model;

import com.appstar.tutionportal.teacher.adapter.ClassListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassModel implements Serializable {
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String  className;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String  subject;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    private String rating;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    private int classId;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private  String location;

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    private  String time_to;

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    private  String time_from;

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    private  String capacity;
    private ArrayList<ClassModel> arrayList;

 /*   public ClassModel(String class_name) {

        this.className=class_name;
    }
*/
   /* public ClassModel(String resp) {
        try {
            JSONObject jsonObject = new JSONObject(resp);
            if (jsonObject.getString("data") != "") {
                JSONArray jo1 = jsonObject.getJSONArray("data");
                for (int i = 0; i < jo1.length(); i++) {
                    JSONObject c = jo1.getJSONObject(i);

                    ClassModel classModel = new ClassModel(c.getString("class_name"));
                    arrayList.add(classModel);

                }
                setArrayList(arrayList);
                }

        } catch (Exception ex) {
        }

    }


    public void setArrayList(ArrayList<ClassModel> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<ClassModel> getArrayList() {
        return arrayList;
    }

    public String getClassName() {
        return className;
    }*/

}
