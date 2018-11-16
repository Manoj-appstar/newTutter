package com.appstar.tutionportal.teacher.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.adapter.CustomAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

public class FragmentEditProfileMore extends Fragment implements OnResponseListener {
    private static Utils utils;
    private static RequestServer requestServer;
    EditText etGraduationType, etGraduationDetail, etPostGraduationType, etPostGraduationDetail, etOthers, etSpecialist;
    TextView tvCancel, tvAdd;
    ImageView ivtypeGraduation, ivGraduationDetail, ivTypePostGraduation, ivPostGraduationDetail;
    int REQ_CLASS = 188;
    AlertDialog alertDialog;
    ListView listView;
    String selectType;
    DBHelper dbHelper;
    private CustomAdapter customAdapter;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_edit_detail, container, false);
        utils = new Utils();
        mActivity = getActivity();
        requestServer = new RequestServer(getActivity(), this);
     //   sharePreferenceData = new SharePreferenceData();
        dbHelper = new DBHelper(mActivity);
        findView(view);
        setData();
        onclickListener();
        return view;
    }

    private void findView(View view) {
        etGraduationType = view.findViewById(R.id.etGraduationType);
        etGraduationDetail = view.findViewById(R.id.etGraduationDetail);
        etPostGraduationType = view.findViewById(R.id.etPostGraduationType);
        etPostGraduationDetail = view.findViewById(R.id.etPostGraduationDetail);
        etOthers = view.findViewById(R.id.etOthers);
        etSpecialist = view.findViewById(R.id.etSpecialist);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvAdd = view.findViewById(R.id.tvAdd);
        ivtypeGraduation = view.findViewById(R.id.ivtypeGraduation);
        ivGraduationDetail = view.findViewById(R.id.ivGraduationDetail);
        ivTypePostGraduation = view.findViewById(R.id.ivTypePostGraduation);
        ivPostGraduationDetail = view.findViewById(R.id.ivPostGraduationDetail);
    }

    private void setData() {
        etGraduationType.setText(Data.getTeacherDetail().getBachelorDegree());
        etGraduationDetail.setText(Data.getTeacherDetail().getBachelorDegreeDetail());
        etPostGraduationType.setText(Data.getTeacherDetail().getMasterDegree());
        etPostGraduationDetail.setText(Data.getTeacherDetail().getBachelorDegreeDetail());
        etOthers.setText(Data.getTeacherDetail().getOther());
        etSpecialist.setText(Data.getTeacherDetail().getSpecialist());
    }

    private void onclickListener() {
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
            }
        });
        ivtypeGraduation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("grad", getResources().getStringArray(R.array.graduation_type));
            }
        });
        ivGraduationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("gradDetail", getResources().getStringArray(R.array.graduation_detail));
            }
        });
        ivTypePostGraduation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("post", getResources().getStringArray(R.array.post_graduation));
            }
        });
        ivPostGraduationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("postDetail", getResources().getStringArray(R.array.post_graduation_detail));
            }
        });
    }


    private void callAPI() {
        //  RetrofitCall.CallHome(HomeFragment.this, apiInterface);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
            jsonObject.put("bachelor_degree", etGraduationType.getText().toString());
            jsonObject.put("bachelor_degree_detail", etGraduationDetail.getText().toString());
            jsonObject.put("master_degree", etPostGraduationType.getText().toString());
            jsonObject.put("master_degree_detail", etPostGraduationDetail.getText().toString());
            jsonObject.put("other", etOthers.getText().toString());
            jsonObject.put("specialist", etSpecialist.getText().toString());
            requestServer.sendStringPost(UrlManager.ADD_TEACHER_DETAI_MORE, jsonObject, REQ_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                if (etGraduationType.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setBachelorDegree("Not Specified");
                } else {
                    Data.getTeacherDetail().setBachelorDegree(etGraduationType.getText().toString());
                    //   sharePreferenceData.setGraduationType(mActivity, etGraduationType.getText().toString());
                }
                if (etGraduationDetail.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setBachelorDegreeDetail("Not Specified");
                    //sharePreferenceData.setGraduationDatail(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setBachelorDegreeDetail(etGraduationDetail.getText().toString());
                    //  sharePreferenceData.setGraduationDatail(mActivity, etGraduationDetail.getText().toString());
                }
                if (etPostGraduationType.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setMasterDegree("Not Specified");
                    //sharePreferenceData.setPostGraduationType(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setMasterDegree(etPostGraduationType.getText().toString());
                    //  sharePreferenceData.setPostGraduationType(mActivity, etPostGraduationType.getText().toString());
                }
                if (etPostGraduationDetail.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setMasterDegreeDatail("Not Specified");
                    //  sharePreferenceData.setPostGraduationDatail(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setMasterDegreeDatail(etPostGraduationDetail.getText().toString());
                    //sharePreferenceData.setPostGraduationDatail(mActivity, etPostGraduationDetail.getText().toString());
                }
                if (etOthers.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setOther("Not Specified");
                    //sharePreferenceData.setOthers(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setOther(etOthers.getText().toString());
                    //sharePreferenceData.setOthers(mActivity, etOthers.getText().toString());
                }
                if (etSpecialist.getText().toString().equalsIgnoreCase("")) {
                    Data.getTeacherDetail().setSpecialist("Not Specified");
                    //sharePreferenceData.setOthersDetail(mActivity, "Not Specified");
                } else {
                    Data.getTeacherDetail().setSpecialist(etSpecialist.getText().toString());
                    //sharePreferenceData.setOthersDetail(mActivity, etSpecialist.getText().toString());
                }
                dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                alertDialog.dismiss();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }


    private void showDialog(String type, final String[] array) {
        selectType = type;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
        dialogBuilder.setCancelable(false);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.row_layout, null);


// Include dialog.xml file
        dialogBuilder.setView(dialogView);

        listView = dialogView.findViewById(R.id.myListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (selectType.equalsIgnoreCase("grad")) {
                    etGraduationType.setText(array[position]);
                    etGraduationType.setSelection(etGraduationType.getText().length());
                    alertDialog.dismiss();
                } else if (selectType.equalsIgnoreCase("gradDetail")) {
                    etGraduationDetail.setText(array[position]);
                    etGraduationDetail.setSelection(etGraduationDetail.getText().length());
                    alertDialog.dismiss();
                } else if (selectType.equalsIgnoreCase("post")) {
                    etPostGraduationType.setText(array[position]);
                    etPostGraduationType.setSelection(etPostGraduationType.getText().length());
                    alertDialog.dismiss();
                } else if (selectType.equalsIgnoreCase("postDetail")) {
                    etPostGraduationDetail.setText(array[position]);
                    etPostGraduationDetail.setSelection(etPostGraduationDetail.getText().length());
                    alertDialog.dismiss();
                }
            }
        });

        listView.setAdapter(new CustomAdapter(mActivity, array));
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        //alertDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.setCurrentScreen(FragmentNames._EDIT_PROFILE_MORE);
    }
}
