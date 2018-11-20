package com.appstar.tutionportal.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.common.AssignSubjectToTeacher;
import com.appstar.tutionportal.Dialog.DialogError;
import com.appstar.tutionportal.Model.DirectorDetail;
import com.appstar.tutionportal.Model.StudentDetail;
import com.appstar.tutionportal.Model.TeacherDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.institute.activities.InstituteDashboard;
import com.appstar.tutionportal.student.activity.StudentDashboard;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.TeacherDashboard;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class VerifyOtp extends AppCompatActivity implements OnResponseListener {
    private static SharePreferenceData sharePreferenceData;
    private static TeacherDetail teacherDetail;
    TextView tvTimer, tvResend;
    EditText no1, no2, no3, no4;
    Handler handler = new Handler();
    int counter = 59;
    String minutes = "03", seconds = "00", otp_first_digit, otp_second_digit, otp_third_digit, otp_fourth_digit, concat_otp;
    Runnable runnable;
    int second, minute = 0;
    boolean isResendDisabled = true;
    RequestServer requestServer;
    int REQ_SEND_OTP = 900, REQ_VERIFY_OTP = 899, REQ_REGISTER = 898, REQ_LOGIN = 897;
    Button btnVerifyOtp;
    String userType_data;
    DialogError dialogError;
    EditText etOtp;
    private Activity mActivity;
    private DBHelper dbHelper;
    private String fname, lname, email, mobile, password, user_type, dob, gender, from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_otp);
        sharePreferenceData = SharePreferenceData.getInstance();
        mActivity = VerifyOtp.this;
        dbHelper = new DBHelper(mActivity);
        dialogError = new DialogError();
        initView();
        getData();
        setRunnable();
        setTimer();
        onClickListener();
        onOtpTypeChange();

    }

    private void onOtpTypeChange() {
        no1.addTextChangedListener(new GenericTextWatcher(no1));
        no2.addTextChangedListener(new GenericTextWatcher(no2));
        no3.addTextChangedListener(new GenericTextWatcher(no3));
        no4.addTextChangedListener(new GenericTextWatcher(no4));

    }


    private void getData() {
        from = getIntent().getStringExtra("from");
        if (from.equalsIgnoreCase("register")) {
            fname = getIntent().getStringExtra("fname");
            lname = getIntent().getStringExtra("lname");
            mobile = getIntent().getStringExtra("mobile");
            email = getIntent().getStringExtra("email");
            password = getIntent().getStringExtra("password");
            user_type = getIntent().getStringExtra("user_type");
            gender = getIntent().getStringExtra("gender");
            dob = getIntent().getStringExtra("dob");
        } else {
            mobile = getIntent().getStringExtra("mobile");
        }
    }

    private void onClickListener() {
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResendDisabled) {
                    resendOtp();
                }
            }
        });

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOtp();
            }
        });

    }

    private void resetOtpView() {
        minute = 0;
        counter = 59;
        tvResend.setEnabled(false);
        setTimer();
        tvResend.setAlpha(.4f);
        tvTimer.setVisibility(View.VISIBLE);

    }

    private void initView() {
        tvTimer = findViewById(R.id.tvTimer);
        tvResend = findViewById(R.id.tvResend);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        etOtp = findViewById(R.id.etOtp);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);
        requestServer = new RequestServer(this, this);
    }

    private void setTimer() {
        handler.postDelayed(runnable, 800);
    }

    private void setRunnable() {

        runnable = new Runnable() {
            @Override
            public void run() {
                if (minute < 0) {
                    isResendDisabled = false;
                    tvResend.setEnabled(true);
                    tvResend.setAlpha(0.6f);
                    tvResend.setAlpha(0.6f);
                    tvResend.setAlpha(0.6f);
                    tvResend.setAlpha(0.6f);
                    tvResend.setAlpha(0.7f);
                    tvResend.setAlpha(0.8f);
                    tvResend.setAlpha(0.8f);
                    tvResend.setAlpha(0.8f);
                    tvResend.setAlpha(0.8f);
                    tvResend.setAlpha(0.8f);
                    tvResend.setAlpha(0.9f);
                    tvResend.setAlpha(0.9f);
                    tvResend.setAlpha(0.9f);
                    tvResend.setAlpha(0.9f);
                    tvResend.setAlpha(0.9f);
                    tvResend.setAlpha(1);
                    tvTimer.setVisibility(View.GONE);

                } else {
                    handler.postDelayed(runnable, 995);
                    if (counter == 0) {
                        minute = minute - 1;
                    }
                    tvTimer.setText(String.format("%02d", minute < 0 ? 0 : minute) + ":" + String.format("%02d", counter));
                    counter = counter - 1;
                    if (counter < 0) {
                        counter = 59;
                    }
                }

            }
        };

    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (reqCode == REQ_REGISTER) {
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    login();
                }
            } else if (reqCode == REQ_SEND_OTP) {
                resetOtpView();

            } else if (reqCode == REQ_VERIFY_OTP) {
                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                    if (from.equalsIgnoreCase("register")) {
                        registerUser();
                    } else {
                        login();
                    }

                } else
                    Toast.makeText(getApplicationContext(), "OTP not matched", Toast.LENGTH_SHORT).show();
            } else if (reqCode == REQ_LOGIN) {

                try {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        String userType = String.valueOf(jsonObject.getString("user_type"));
                        String user_token = String.valueOf(jsonObject.getString("user_token"));
                        sharePreferenceData.setToken(mActivity, user_token);

                        if (userType.equalsIgnoreCase("2")) {
                            Gson gson = new Gson();
                            Type category = new TypeToken<TeacherDetail>() {
                            }.getType();

                            TeacherDetail userDetail = gson.fromJson(jsonObject.getJSONObject("data").toString(), category);
                            SharePreferenceData.setUserType(getApplicationContext(), "teacher");
                            dbHelper.insertTeacherDetail(userDetail);
                            Data.setTeacherDetail(userDetail);
                            sharePreferenceData.setUserId(getApplicationContext(), String.valueOf(Data.getTeacherDetail().getId()));
                            if (from.equalsIgnoreCase("register")) {
                                Intent i = new Intent(VerifyOtp.this, AssignSubjectToTeacher.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                SharePreferenceData.setSubjectAdded(getApplicationContext(), true);
                                Intent i = new Intent(VerifyOtp.this, TeacherDashboard.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        } else if (userType.equalsIgnoreCase("1")) {
                            Gson gson = new Gson();
                            Type category = new TypeToken<StudentDetail>() {
                            }.getType();
                            StudentDetail userDetail = gson.fromJson(jsonObject.getJSONObject("data").toString(), category);
                            SharePreferenceData.setUserType(getApplicationContext(), "student");
                            dbHelper.insertStudentDetail(userDetail);
                            Data.setStudentDetail(userDetail);
                            sharePreferenceData.setUserId(getApplicationContext(), String.valueOf(Data.getStudentDetail().getId()));
                            Intent i = new Intent(VerifyOtp.this, StudentDashboard.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

                        } else {
                            Gson gson = new Gson();
                            Type category = new TypeToken<DirectorDetail>() {
                            }.getType();
                            DirectorDetail userDetail = gson.fromJson(jsonObject.getJSONObject("data").toString(), category);
                            SharePreferenceData.setUserType(getApplicationContext(), "director");
                            dbHelper.insertDirectorDetail(userDetail);
                            Data.setDirectorDetail(userDetail);
                            sharePreferenceData.setUserId(getApplicationContext(), String.valueOf(Data.getDirectorDetail().getDirectorId()));
                            if (from.equalsIgnoreCase("register")) {
                                Intent i = new Intent(VerifyOtp.this, AssignSubjectToTeacher.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                SharePreferenceData.setSubjectAdded(getApplicationContext(), true);
                                Intent i = new Intent(VerifyOtp.this, InstituteDashboard.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);

                            }

                        }
                        finish();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                finish();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }


    private void verifyOtp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = UrlManager.VERIFY_OTP;
                    otp_first_digit = no1.getText().toString();
                    otp_second_digit = no2.getText().toString();
                    otp_third_digit = no3.getText().toString();
                    otp_fourth_digit = no4.getText().toString();
                    //  concat_otp = otp_first_digit + otp_second_digit + otp_third_digit + otp_fourth_digit;
                    concat_otp = etOtp.getText().toString().trim();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("phone", mobile);
                    jsonObject.put("otp", concat_otp);
                    requestServer.sendStringPost(url, jsonObject, REQ_VERIFY_OTP, true);

                } catch (Exception ex) {
                }
            }
        }, 400);

    }

    private void registerUser() {
        if (validation()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("first_name", fname);
                jsonObject.put("last_name", lname);
                jsonObject.put("email", email);
                jsonObject.put("phone", mobile);
                if (user_type.equalsIgnoreCase("teacher")) {
                    jsonObject.put("user_type", "2");
                } else if (user_type.equalsIgnoreCase("student")) {
                    jsonObject.put("user_type", "1");
                } else {
                    jsonObject.put("user_type", "3");
                }
                requestServer.sendStringPost(UrlManager.REGISTRATION, jsonObject, REQ_REGISTER, true);
            } catch (Exception ex) {
            }
        }

    }


    private boolean validation() {
        boolean bool = false;
        try {

            if (etOtp.getText().toString().trim().equals("")) {
                Snackbar.make(etOtp, "Enter Your Batch Name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etOtp.getText().toString().trim().length() < 3) {
                Snackbar.make(etOtp, "Your Class Name Is Too Short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            }  else
                bool = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bool;
    }

    private void resendOtp() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", mobile);
            requestServer.sendStringPost(UrlManager.GET_NEW_USER_OTP, jsonObject, REQ_SEND_OTP, true);
        } catch (Exception ex) {
        }
    }

    private void login() {
        try {
            String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            sharePreferenceData.setDeviceId(mActivity, device_id);
            String token = FirebaseInstanceId.getInstance().getToken();
            sharePreferenceData.setFCMToken(mActivity, token);
            String url = UrlManager.Login;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", mobile);
            jsonObject.put("fcm_id", sharePreferenceData.getFCMToken(mActivity));
            jsonObject.put("device_type", "android");
            jsonObject.put("device_id", device_id);
            requestServer.sendStringPost(url, jsonObject, REQ_LOGIN, true);
        } catch (Exception ex) {
        }
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.no1:
                    if (text.length() > 1) {
                        no1.setText(String.valueOf(text.charAt(0)));
                        no2.setText(String.valueOf(text.charAt(1)));
                        no2.requestFocus();
                        no2.setSelection(no2.getText().length());
                    }
                    break;
                case R.id.no2:
                    if (text.length() > 1) {
                        no2.setText(String.valueOf(text.charAt(0)));
                        no3.setText(String.valueOf(text.charAt(1)));
                        no3.requestFocus();
                        no3.setSelection(no3.getText().length());
                    }
                    if (text.length() == 0) {
                        no1.requestFocus();
                        no1.setSelection(no1.getText().length());
                    }
                    break;
                case R.id.no3:
                    if (text.length() > 1) {
                        no3.setText(String.valueOf(text.charAt(0)));
                        no4.setText(String.valueOf(text.charAt(1)));
                        no4.requestFocus();
                        no4.setSelection(no4.getText().length());
                    }
                    if (text.length() == 0) {
                        no2.requestFocus();
                        no2.setSelection(no2.getText().length());
                    }
                    break;
                case R.id.no4:
                    if (text.length() == 0) {
                        no3.requestFocus();
                        no3.setSelection(no3.getText().length());
                    }
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    }
}
