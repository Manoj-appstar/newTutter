package com.appstar.common;

import android.app.Activity;

import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.ClassImage;
import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.adapter.AvatarAdapter;

import java.util.Arrays;
import java.util.List;

public class BindData {
    AddClasses obj;
    List<String> weekDaysList;
    List<List<Subject>> subjectList;
    List<List<ClassImage>> imageList;

    String strWeek, strSubject;
    Activity mActivity;
    AvatarAdapter adapter;

    public void bindAllData(Activity context, final AddClasses obj, final ClassDetail classDetail) {
        this.mActivity = context;
        this.obj = obj;
        if (obj != null) {

            obj.batchName.setText(classDetail.getBatchName());
            obj.aClass.setText(classDetail.getClassName());
            obj.aDate.setText(classDetail.getStartingDate());
            obj.aDate.setText(classDetail.getStartingDate());
            obj.tvEndDate.setText(classDetail.getEndDate());
            obj.aTimeTo.setText(classDetail.getTimingFrom());
            obj.aTimeFrom.setText(classDetail.getTimingTo());

            weekDaysList = Arrays.asList(classDetail.getWeekDays().split(","));
            imageList = Arrays.asList(classDetail.getClassImage());

            try {
                for (int i = 0; i <= weekDaysList.size(); i++) {
                    if (weekDaysList.get(i).equalsIgnoreCase("1")) {
                        if (strWeek == null) {
                            strWeek = "Monday,";
                        } else {
                            strWeek += "Monday,";
                        }
                    } else if (weekDaysList.get(i).equalsIgnoreCase("2")) {
                        if (strWeek == null) {
                            strWeek = "Tuesday,";
                        } else {
                            strWeek += "Tuesday,";
                        }

                    } else if (weekDaysList.get(i).equalsIgnoreCase("3")) {
                        if (strWeek == null) {
                            strWeek = "Wednesday,";
                        } else {
                            strWeek += "Wednesday,";
                        }
                    } else if (weekDaysList.get(i).equalsIgnoreCase("4")) {
                        if (strWeek == null) {
                            strWeek = "Thursday,";
                        } else {
                            strWeek += "Thursday,";
                        }
                    } else if (weekDaysList.get(i).equalsIgnoreCase("5")) {
                        if (strWeek == null) {
                            strWeek = "Friday,";
                        } else {
                            strWeek += "Friday,";
                        }
                    } else if (weekDaysList.get(i).equalsIgnoreCase("6")) {
                        if (strWeek == null) {
                            strWeek = "Saturday,";
                        } else {
                            strWeek += "Saturday,";
                        }
                    } else if (weekDaysList.get(i).equalsIgnoreCase("7")) {
                        if (strWeek == null) {
                            strWeek = "Sunday,";
                        } else {
                            strWeek += "Sunday,";
                        }
                    }


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (strWeek.endsWith(",")) {
                strWeek = strWeek.substring(0, strWeek.length() - 1);
                obj.tvDays.setText(strWeek);
            }


            obj.aLimit.setText(classDetail.getStudentLimit());
            obj.etClassPhone.setText(classDetail.getPhone());
            obj.etPrice.setText(classDetail.getPrice());
            obj.aLocation.setText(classDetail.getAddress());
            obj.lat = String.valueOf(classDetail.getLatitude());
            obj.longt = String.valueOf(classDetail.getLongitude());
            obj.subject_selected_id = classDetail.getSubjectId();
            obj.class_selected_id = classDetail.getClassId();
            obj.startingDate = classDetail.getStartingDate();
            obj.endDate = classDetail.getEndDate();
            obj.check_day = classDetail.getWeekDays();
            obj.strAddress = classDetail.getAddress();
            obj.strHouse_no = classDetail.getHouseNo();
            obj.strLandmark = classDetail.getLandmark();
            obj.strCity = classDetail.getCity();
            obj.strInstituteId = classDetail.getInstituteId();
            obj.strBranchId = classDetail.getBranchId();
        //    obj.Subject_id_list = classDetail.getSubjectId();



            /*dapter = new AvatarAdapter(mActivity, mActivity, obj.listData);
            obj.recyclerView.initRecyclerView(mActivity, adapter);*/
        }
    }
}
