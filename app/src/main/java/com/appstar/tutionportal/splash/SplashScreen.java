package com.appstar.tutionportal.splash;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.appstar.common.AssignSubjectToTeacher;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.institute.activities.InstituteDashboard;
import com.appstar.tutionportal.login.ChooseScreen;
import com.appstar.tutionportal.student.activity.StudentDashboard;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.TeacherDashboard;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity implements OnResponseListener {
    private static SharePreferenceData preferenceData;
    RequestServer requestServer;
    int REQ_GET_DETAIL;
    private Activity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private Switch aSwitch;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shap);
        dbHelper = new DBHelper(getApplicationContext());
        requestServer = new RequestServer(this, this);
        preferenceData = SharePreferenceData.getInstance();
        mActivity = this;
        StartAnimations();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (preferenceData.getUserId1()!=0) {
                String user=preferenceData.getUserType(getApplicationContext());

                if (!TextUtils.isEmpty(preferenceData.getUserId(getApplicationContext()))) {
                    if (preferenceData.getUserType(getApplicationContext()).equals("student")) {
                            Data.setStudentDetail(dbHelper.getStudentDetail());
                            startActivity(new Intent(getApplicationContext(), StudentDashboard.class));


                    } else if (preferenceData.getUserType(getApplicationContext()).equals("teacher")) {
                        if (SharePreferenceData.isSubjectAdded(getApplicationContext())) {
                            Data.setTeacherDetail(dbHelper.getTeacherDetail());
                            startActivity(new Intent(getApplicationContext(), TeacherDashboard.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), AssignSubjectToTeacher.class));
                            finish();
                        }
                    } else {
                        if (SharePreferenceData.isSubjectAdded(getApplicationContext())) {
                            Data.setDirectorDetail(dbHelper.getDirectorDetail());
                            startActivity(new Intent(getApplicationContext(), InstituteDashboard.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), AssignSubjectToTeacher.class));
                            finish();
                        }
                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), ChooseScreen.class));
                }
                finish();
            }
        }, 100);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = findViewById(R.id.ic_logo);
        TextView text = findViewById(R.id.title);
        iv.clearAnimation();
        iv.startAnimation(anim);
        text.clearAnimation();
        text.startAnimation(anim);
    }


    private void getData() {
        Utils.checkInternetConnection(this);
        try {
            String url = UrlManager.BASE_URL + "api/check_session.php";
            String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", preferenceData.getUserId(getApplicationContext()));
            jsonObject.put("user_type", preferenceData.getUserType(getApplicationContext()));
            jsonObject.put("device_id", device_id);
            requestServer.sendStringPost(url, jsonObject, REQ_GET_DETAIL, false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("status")) {
                if (jsonObject.getInt("status") == 200) {
                    startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                } else {
                    showErrorDialog("Currently you have logged in another devices.Please retry to login.", false);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    private void showErrorDialog(String msg, final boolean isLogut) {
        final Dialog
                dialog = new Dialog(SplashScreen.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       /* dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;*/
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.common_error_dilog);
        final TextView tvOk = dialog.findViewById(R.id.tvOk);
        final TextView tvContent = dialog.findViewById(R.id.tvContent);
        final TextView llData = dialog.findViewById(R.id.tvOk);
        tvContent.setText(msg);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogut) {
                } else
                    finish();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 400);
    }
}
