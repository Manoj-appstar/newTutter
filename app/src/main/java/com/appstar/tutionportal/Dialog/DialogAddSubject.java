package com.appstar.tutionportal.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.Model.SubjectList;
import com.appstar.tutionportal.Model.SubjectTeacherList;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.list.GetClassList;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.adapter.GetClassAdapter;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import java.util.ArrayList;

public class DialogAddSubject implements OnResponseListener {

    RequestServer requestServer;
    int teacher_id = 2;
    int REQ_TEACHER_LIST = 198;
    // private EditText etSubject;
    AutoCompleteTextView etTeacherAssigned, etSubject;
    //  String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};
    Button btnAdd;
    Object addClasses;
    String subject_id;
    GetClassAdapter getClassAdapter;
    private Activity mActivity;
    private AlertDialog alertDialog;
    private Utils utils;
    private SharePreferenceData sharePreferenceData;
    private Dialog dialog;
    // private ArrayList<SubjectTeacherList> arrayList;
    private ArrayList<Object> getSubjectLists = new ArrayList<>();
    private SubjectTeacherList subjectTeacherList;
    private AdapterView.OnItemClickListener onItemSubjectClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object obj = getSubjectLists.get(i);
                    if (obj instanceof GetClassList) {

                    } else {
                        etSubject.setText(((SubjectList) obj).getSubjectName());
                        subject_id = (((SubjectList) obj).getSubjectId());
                     //   getTeacherList();
                        Log.e("subject_id", subject_id);
                    }

                }
            };

    private void getTeacherList() {
        try {
            String url = UrlManager.GET_NEW_USER_OTP + "?subject_id=" + subject_id + "?name=" + etTeacherAssigned.getText().toString();
            requestServer.getRequest(url, REQ_TEACHER_LIST, true);
        } catch (Exception ex) {
        }
    }

    public void showDialog(Activity context, final Object obj, ArrayList<Object> objList) {
        mActivity = context;
        try {
            this.getSubjectLists = objList;
            requestServer = new RequestServer(context, this);
            //   final SubjectTeacherList subjectTeacherList = arrayList;
            //   String[] data = arrayList.toArray(new String[arrayList.size()]);
            sharePreferenceData = new SharePreferenceData();
            utils = new Utils();

            dialog = new Dialog(context);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimation;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_subject_institute);
            etSubject = dialog.findViewById(R.id.etSubject);
            etTeacherAssigned = dialog.findViewById(R.id.etTeacherAssigned);
            btnAdd = dialog.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (obj != null) {
                        if (obj instanceof AddClasses) {
                            Subject subject = new Subject();
                            subject.setSubject(etSubject.getText().toString().trim());
                            subject.setSubjectId(subject_id);
                           // subject.setTeacher(etTeacherAssigned.getText().toString().trim());
                            //subject.setTeacherId(teacher_id);
                            //   subject.setTeacherId(1);
                            ((AddClasses) obj).addSubjectList(subject);
                        }
                    }
                }
            });
            //will start working from first character
            getClassAdapter = new GetClassAdapter(mActivity, R.layout.listview_item, getSubjectLists, false);
            etSubject.setThreshold(1);
            etSubject.setAdapter(getClassAdapter);//setting the adapter data into the AutoCompleteTextView
            etSubject.setOnItemClickListener(onItemSubjectClickListener);
            //  etSubject.setAdapter(adapter);
            etSubject.setTextColor(Color.BLACK);

            dialog.setCancelable(false);
            if (!dialog.isShowing())
                dialog.show();
            dialog.setCancelable(true);
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
