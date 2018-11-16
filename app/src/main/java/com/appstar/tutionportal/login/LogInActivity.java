package com.appstar.tutionportal.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.activity.StudentDashboard;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity implements OnResponseListener {
    EditText etPassword, etMobile;
    Button btnLogin;
    RequestServer requestServer;
    int REQ_TYPE_LOGIN = 678;
    TextView txtSkip;
    TextView SignUp;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        onShowClickListener();
        Utils.checkInternetConnection(this);
        requestServer = new RequestServer(this, this);
    }

    private void initView() {
        etPassword = findViewById(R.id.etPassword);
        etMobile = findViewById(R.id.etMobile);
        btnLogin = findViewById(R.id.btnLogin);
        txtSkip = findViewById(R.id.txtSkip);
        //  SignUp = findViewById(R.id.signUp);
    }

    private void onShowClickListener() {
        findViewById(R.id.imgShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                etPassword.setSelection(etPassword.getText().length());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMobile.setError(null);
                etPassword.setError(null);
                boolean bool = false;

                if (etMobile.getText().toString().trim().equals("")) {
                    Snackbar.make(etMobile, "Please Enter mobile number", Snackbar.LENGTH_SHORT).show();
                } else if (etMobile.getText().toString().length() < 9) {
                    Snackbar.make(etMobile, "Enter 10 digit mobile number", Snackbar.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LogInActivity.this, SignUp.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });
        findViewById(R.id.txtSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, StudentDashboard.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });

    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            jsonObject = jsonObject.getJSONObject("results");
            if (jsonObject.has("status")) {
                if (jsonObject.getString("status") == "true") {
                    Intent intent = new Intent(LogInActivity.this, VerifyOtp.class);
                    intent.putExtra("from", "login");
                    intent.putExtra("mobile", etMobile.getText().toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error to save", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error in response", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {
    }

    private void login() {
        try {
            String url = UrlManager.GET_NEW_USER_OTP + "?phone=" + etMobile.getText().toString().trim();
            requestServer.getRequest(url, REQ_TYPE_LOGIN, true);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
