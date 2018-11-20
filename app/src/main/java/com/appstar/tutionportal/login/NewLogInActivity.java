package com.appstar.tutionportal.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Dialog.DialogError;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.NetworkResponse;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.NetworkChangeReceiver;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.Validator;
import com.appstar.tutionportal.views.MultiDirectionSlidingDrawer;
import com.appstar.tutionportal.views.MyTextView;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

import java.util.List;

public class NewLogInActivity extends AppCompatActivity implements OnResponseListener, NetworkResponse {
    MultiDirectionSlidingDrawer mDrawer;
    TextView tvAction, tvTitle;
    LinearLayout llSignUp;
    RequestServer requestServer;
    boolean isOpened = true;
    boolean bool = false;
    AlertDialog alertDialog;
    Geocoder geocoder;
    List<Address> addresses;
    Activity mActivity;
    Utils utils;
    EditText etName_signup, etEmail_signup, etMobile_signup, etPassword_signup, etLastName_signup;
    ProgressUtil progressUtil;

    //SignUp
    TextView tvUserType_signup, tvDob_signup;
    MyTextView text_student_signup, text_teacher_signup;
    Button btnCreateAccount;
    int REQ_REGISTER = 188;
    int year, day, month;
    int value_student = 0, value_teacher = 0;
    ImageView imgStudent, imgTeacher;
    LinearLayout llUserDob, linearStudent, linearTeacher;
    RadioGroup radioGroup;
    CheckBox checkbox;
    String gender = "male", selectUserType, strCheckbox;
    String[] str = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    EditText etPassword, etMobile;
    String category;

    //Login
    CardView btnLogin;
    int REQ_TYPE_LOGIN = 678;
    TextView txtSkip;
    LinearLayout llCategory;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_new_signin);
        mActivity = this;
        utils = new Utils();
        progressUtil = new ProgressUtil();
        getData();
        initView();
        requestServer = new RequestServer(this, this);
        networkChangeReceiver = new NetworkChangeReceiver(mActivity, this);
        Utils.checkInternetConnection(this);
        initView();
        onClickListener();
        requestServer = new RequestServer(this, this);
        mDrawer.animateOpen();
        mDrawer.setOnDrawerCloseListener(new MultiDirectionSlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                setTextChange(false);
                isOpened = false;
            }
        });
        mDrawer.setOnDrawerOpenListener(new MultiDirectionSlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                setTextChange(true);
                isOpened = true;
            }
        });
    }

    private void getData() {
        category = getIntent().getStringExtra("for");
    }

    private void setTextChange(boolean isClosed) {
        if (isClosed) {
            llSignUp.setVisibility(View.GONE);
            tvTitle.setText("New in Tutter? ");
            tvAction.setText("Register Now");
        } else {
            llSignUp.setVisibility(View.VISIBLE);
            tvTitle.setText("Already registered? ");
            tvAction.setText("Log In");
        }
    }

    private void initView() {
        llSignUp = findViewById(R.id.llSignUp);
        tvAction = findViewById(R.id.tvAction);
        tvTitle = findViewById(R.id.tvTitle);
        mDrawer = (MultiDirectionSlidingDrawer) findViewById(R.id.drawer);

        EditText editor = new EditText(this);
        editor.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        //SignUp
        llCategory = findViewById(R.id.llCategory);
        llCategory.setVisibility(View.GONE);
        tvUserType_signup = findViewById(R.id.tvUserType);
        llUserDob = findViewById(R.id.llUserDob);
        tvDob_signup = findViewById(R.id.tvDob);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        etName_signup = findViewById(R.id.etName);
        etName_signup.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        etLastName_signup = findViewById(R.id.etLastName);
        etEmail_signup = findViewById(R.id.etEmail);
        etMobile_signup = findViewById(R.id.etMobile);
        etPassword_signup = findViewById(R.id.etPassword);
        radioGroup = findViewById(R.id.radioGroup);
        linearStudent = findViewById(R.id.linearStudent);
        linearTeacher = findViewById(R.id.linearTeacher);
        imgStudent = findViewById(R.id.imgStudent);
        imgTeacher = findViewById(R.id.imgTeacher);
        imgStudent.setVisibility(View.GONE);
        imgTeacher.setVisibility(View.GONE);
        text_student_signup = findViewById(R.id.text_student);
        checkbox = findViewById(R.id.checkbox);
        text_teacher_signup = findViewById(R.id.text_teacher);
        selectUserType = tvUserType_signup.getText().toString().trim();

        //Login

        etPassword = findViewById(R.id.etPassword);
        etMobile = findViewById(R.id.etMobilesignin);
        btnLogin = findViewById(R.id.btnLogin);
        txtSkip = findViewById(R.id.txtSkip);
    }

    private void onClickListener() {
        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpened) {
                    mDrawer.animateClose();
                } else
                    mDrawer.animateOpen();
            }
        });

        //SignUp
        findViewById(R.id.llUserType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupWindow popupWindow = popupDisplayGroup();
                popupWindow.showAsDropDown(view);
            }
        });

        linearStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value_student == 0) {
                    imgTeacher.setVisibility(View.GONE);
                    text_teacher_signup.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    imgStudent.setVisibility(View.VISIBLE);
                    text_student_signup.setTextColor(ContextCompat.getColor(mActivity, R.color.colorAccent));
                    tvUserType_signup.setText("student");
                    selectUserType = tvUserType_signup.getText().toString().trim();
                    value_student = 1;
                } else {
                    imgStudent.setVisibility(View.GONE);
                    value_student = 0;
                    text_student_signup.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    tvUserType_signup.setText("Select User Type");
                    selectUserType = tvUserType_signup.getText().toString().trim();
                }
            }
        });
        linearTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value_teacher == 0) {
                    imgStudent.setVisibility(View.GONE);
                    text_student_signup.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    imgTeacher.setVisibility(View.VISIBLE);
                    text_teacher_signup.setTextColor(ContextCompat.getColor(mActivity, R.color.colorAccent));
                    tvUserType_signup.setText("teacher");
                    selectUserType = tvUserType_signup.getText().toString().trim();
                    value_teacher = 1;
                } else {
                    imgTeacher.setVisibility(View.GONE);
                    text_teacher_signup.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    tvUserType_signup.setText("Select User Type");
                    selectUserType = tvUserType_signup.getText().toString().trim();
                    value_teacher = 0;
                }
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox.isChecked()) {
                    checkbox.setChecked(true);
                    strCheckbox = "true";
                } else {
                    checkbox.setChecked(false);
                    strCheckbox = "false";
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });

        //Login
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
                    DialogError.setMessage(mActivity, "Are you sure to send otp on this number +91 " + etMobile.getText().toString().trim(), "Cancel",
                            "Ok", false, new Runnable() {
                                @Override
                                public void run() {
                                    login();
                                }
                            });

                }
            }
        });
    }

    public PopupWindow popupDisplayGroup() {
        final PopupWindow popupWindow = new PopupWindow(getApplicationContext());
        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.dialog_user_type, null);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(4);
        }
        TextView tvStudent = view.findViewById(R.id.tvStudent);
        TextView tvTeacher = view.findViewById(R.id.tvTeacher);

        tvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvUserType_signup.setText("student");
                popupWindow.dismiss();
            }
        });

        tvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvUserType_signup.setText("teacher");
                popupWindow.dismiss();

            }
        });
        popupWindow.setClippingEnabled(true);

        return popupWindow;
    }

    private void callAPI() {

        if (validation()) {
            DialogError.setMessage(this, "Are you sure to send otp on this number +91 " + etMobile_signup.getText().toString().trim(), "Cancel",
                    "Ok", false, new Runnable() {
                        @Override
                        public void run() {
                            registerData();
                        }
                    });
        }
    }

    private void registerData() {
        //   progressUtil.showDialog(mActivity);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", etMobile_signup.getText().toString().trim());
            jsonObject.put("email", etEmail_signup.getText().toString().trim());
            requestServer.sendStringPost(UrlManager.GET_NEW_USER, jsonObject, REQ_REGISTER, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void login() {
        try {
            String url = UrlManager.GET_NEW_USER_OTP + "?phone=" + etMobile.getText().toString().trim();
            requestServer.getRequest(url, REQ_TYPE_LOGIN, true);
        } catch (Exception ex) {
        }
    }

    private boolean validation() {
        boolean bool = false;
        try {

            if (etName_signup.getText().toString().trim().equals("")) {

                Snackbar.make(etName_signup, "Enter your name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etName_signup.getText().toString().trim().length() < 3) {

                Snackbar.make(etName_signup, "Name is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (!checkbox.isChecked()) {

                Snackbar.make(etName_signup, "Please accept terms & conditions", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etLastName_signup.getText().toString().trim().equals("")) {

                Snackbar.make(etLastName_signup, "Enter your last name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etLastName_signup.getText().toString().trim().length() < 3) {

                Snackbar.make(etLastName_signup, "Last name is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etEmail_signup.getText().toString().trim().equals("")) {

                Snackbar.make(etEmail_signup, "Enter email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (!Validator.isValidEmail(etEmail_signup.getText().toString().trim())) {

                Snackbar.make(etEmail_signup, "Enter valid email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etMobile_signup.getText().toString().trim().equals("")) {

                Snackbar.make(etMobile_signup, "Enter mobile number", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etMobile_signup.getText().toString().trim().length() < 9) {

                Snackbar.make(etMobile_signup, "Enter valid mobile number", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } /*else if (selectUserType.equalsIgnoreCase("Select User Type")) {
                Snackbar.make(tvUserType_signup, "Select User Type", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (strCheckbox.equalsIgnoreCase("false")) {
                Snackbar.make(tvUserType_signup, "Please Accept Terms And Conditions", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } */else
                bool = true;

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return bool;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        // mOpenButton = (Button) findViewById(R.id.button_open);
        // mDrawer = (MultiDirectionSlidingDrawer) findViewById(R.id.drawer);
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        //  progressUtil.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (reqCode == REQ_REGISTER) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Intent intent = new Intent(NewLogInActivity.this, VerifyOtp.class);
                        intent.putExtra("from", "register");
                        intent.putExtra("mobile", etMobile_signup.getText().toString().trim());
                        intent.putExtra("email", etEmail_signup.getText().toString().trim());
                        intent.putExtra("fname", etName_signup.getText().toString().trim());
                        intent.putExtra("lname", etLastName_signup.getText().toString().trim());
                        intent.putExtra("password", etPassword_signup.getText().toString().trim());
                        intent.putExtra("gender", gender);
                        intent.putExtra("dob", String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
                        intent.putExtra("user_type", category);
                        startActivity(intent);
                    } else {
                        // DialogError.setMessage(mActivity, jsonObject.getString("message"));
                        DialogError.setMessage(mActivity, jsonObject.getString("message"));
                    }
                } else {
                    DialogError.setMessage(mActivity, "Error in saving");
                }
            }

            if (reqCode == REQ_TYPE_LOGIN) {
                jsonObject = jsonObject.getJSONObject("results");
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Intent intent = new Intent(NewLogInActivity.this, VerifyOtp.class);
                        intent.putExtra("from", "login");
                        intent.putExtra("mobile", etMobile.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    } else {
                        DialogError.setMessage(mActivity, jsonObject.getString("message"));
                    }
                } else {
                    DialogError.setMessage(mActivity, "Error to save");
                }
            }

            } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error in response", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        if (isConnected) {
            if (bool == true) {
                alertDialog.dismiss();
            }
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setCancelable(false);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.no_internet, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            alertDialog.getWindow().setLayout(width, height);
            alertDialog.show();
            bool = true;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

