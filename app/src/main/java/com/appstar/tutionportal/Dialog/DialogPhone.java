package com.appstar.tutionportal.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

public class DialogPhone implements OnResponseListener {
    View dialogView;
    EditText etPhone;
    Button btnChangePhone;
    LinearLayout llVerifyOtp, llChangePhone;
    TextView tvText;
    Dialog dialog;
    int REQ_CLASS = 198;
    RequestServer requestServer;
    SharePreferenceData sharePreferenceData;
    TextView tvResend, tvTimer;
    int second, minute = 0;
    int counter = 59;
    Runnable runnable, successRunnable;
    boolean isResendDisabled = true;
    Handler handler = new Handler();
    int value = 1;
    int REQ_UPDATE_MOBILE = 198;
    int REQ_UPDATE_OTP = 199;
    EditText etOtp;
    boolean bool_phone;
    DBHelper dbHelper;
    String user_type;
    private Activity mActivity;
    private AlertDialog alertDialog;

    public void showDialog(Activity context, Runnable runnable) {

// ...Irrelevant code for customizing the buttons and title
        try {
            successRunnable = runnable;
            mActivity = context;
            requestServer = new RequestServer(context, this);
            sharePreferenceData = new SharePreferenceData();
            dbHelper = new DBHelper(context);

            dialog = new Dialog(context);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimation;
// Include dialog.xml file
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_chagephone);
            if (SharePreferenceData.getUserType(mActivity).equalsIgnoreCase("student")) {
                user_type = "1";
            } else if (SharePreferenceData.getUserType(mActivity).equalsIgnoreCase("teacher")) {
                user_type = "2";
            } else {
                user_type = "3";
            }
            etPhone = dialog.findViewById(R.id.etPhone);
            tvText = dialog.findViewById(R.id.tvText);
            tvTimer = dialog.findViewById(R.id.tvTimer);
            tvResend = dialog.findViewById(R.id.tvResend);
            etOtp = dialog.findViewById(R.id.etOtp);
            tvTimer.setVisibility(View.GONE);
            tvResend.setVisibility(View.GONE);
            tvText.setText("Your Phone Number");
            btnChangePhone = dialog.findViewById(R.id.btnChangePhone);
            btnChangePhone.setText("Change Phone No.");
            llChangePhone = dialog.findViewById(R.id.llChangePhone);
            //  llChangePhone.setVisibility(View.VISIBLE);
            llVerifyOtp = dialog.findViewById(R.id.llVerifyOtp);
            llVerifyOtp.setVisibility(View.GONE);

            btnChangePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changePhoneNo();
                }
            });
            dialog.setCancelable(false);

            if (!dialog.isShowing())
                dialog.show();
            dialog.setCancelable(true);
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }

    private void changePhoneNo() {
        if (bool_phone) {
            callAPIOTP();
        } else {
            callAPIMobile();
        }
    }

    private void callAPIMobile() {
        if (validation()) {
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("phone", etPhone.getText().toString().trim());
                requestServer.sendStringPostWithHeader(UrlManager.UPDATE_TEACHER_PHONE, jsonObject, REQ_UPDATE_MOBILE, true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void callAPIOTP() {
        if (validation()) {
            try {
                String url = UrlManager.UPDATE_TEACHER_PHONE_OTP + "?phone=" +
                        etPhone.getText().toString().trim() + "&otp=" + etOtp.getText().toString().trim() + "&id=" +
                        sharePreferenceData.getUserId(mActivity) + "&user_type=" + user_type;
                requestServer.getRequest(url, REQ_UPDATE_OTP, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validation() {
        boolean bool = false;
        try {
            if (bool_phone) {
                if (etOtp.getText().toString().trim().equals("")) {
                    Snackbar.make(etPhone, "OTP can't be Blank", Snackbar.LENGTH_SHORT).show();
                    bool = false;
                } else
                    bool = true;
            } else if (!bool_phone) {
                if (etPhone.getText().toString().trim().equals("")) {
                    Snackbar.make(etPhone, "Mobile no. can't be Blank", Snackbar.LENGTH_SHORT).show();
                    bool = false;
                } else if (etPhone.getText().toString().trim().length() < 9) {
                    Snackbar.make(etPhone, "Enter valid mobile number", Snackbar.LENGTH_SHORT).show();
                    bool = false;
                } else
                    bool = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bool;
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                llChangePhone.setVisibility(View.VISIBLE);
                llVerifyOtp.setVisibility(View.GONE);
                tvText.setText("Your Phone Number");
                btnChangePhone.setText("Change Phone No.");
                tvTimer.setVisibility(View.GONE);
                tvResend.setVisibility(View.GONE);
                dialog.dismiss();
            }
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }

    private void resetOtpView() {
        minute = 0;
        counter = 59;
        tvResend.setEnabled(false);
        setTimer();
        tvResend.setAlpha(.4f);
        tvTimer.setVisibility(View.VISIBLE);

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
            if (reqCode == REQ_UPDATE_MOBILE) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        setRunnable();
                        setTimer();
                        llVerifyOtp.setVisibility(View.VISIBLE);
                        tvText.setText("Verify Your Phone Number");
                        btnChangePhone.setText("Verify Otp");
                        llChangePhone.setVisibility(View.GONE);
                        tvTimer.setVisibility(View.VISIBLE);
                        tvResend.setVisibility(View.VISIBLE);
                        bool_phone = true;
                    }
                } else {
                    DialogError.setMessage(mActivity, jsonObject.getString("message"));
                }
            } else {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Data.getTeacherDetail().setPhone(etPhone.getText().toString().trim());
                        dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());
                        successRunnable.run();
                        dialog.dismiss();
                    }
                } else {
                    DialogError.setMessage(mActivity, jsonObject.getString("message"));
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

}
