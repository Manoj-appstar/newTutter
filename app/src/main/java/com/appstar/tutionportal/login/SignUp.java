package com.appstar.tutionportal.login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.retrofit.ApiInterface;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.Validator;
import com.appstar.tutionportal.views.MyTextView;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONObject;

import java.util.Calendar;

public class SignUp extends AppCompatActivity implements OnResponseListener {
    TextView tvUserType, tvDob;
    MyTextView text_student, text_teacher;
    Button btnCreateAccount;
    RequestServer requestServer;
    Activity mActivity;
    int REQ_REGISTER = 188;
    EditText etName, etEmail, etMobile, etPassword, etLastName;
    int year, day, month;
    int value_student = 0, value_teacher = 0;
    ImageView imgStudent, imgTeacher;
    LinearLayout llUserDob, linearStudent, linearTeacher;
    RadioGroup radioGroup;
    CheckBox checkbox;
    String gender = "male", selectUserType, strCheckbox;
    String[] str = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private Utils utils;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mActivity = this;
        utils = new Utils();
        initView();
        onclickListener();
        requestServer = new RequestServer(this, this);
        Utils.checkInternetConnection(this);
    }

    private void initView() {
        tvUserType = findViewById(R.id.tvUserType);
        llUserDob = findViewById(R.id.llUserDob);
        tvDob = findViewById(R.id.tvDob);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        // etName.setText(StringUtils.capitalize((etName.getText().toString()).toLowerCase().trim()));
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        radioGroup = findViewById(R.id.radioGroup);
        linearStudent = findViewById(R.id.linearStudent);
        linearTeacher = findViewById(R.id.linearTeacher);
        imgStudent = findViewById(R.id.imgStudent);
        imgTeacher = findViewById(R.id.imgTeacher);
        imgStudent.setVisibility(View.GONE);
        imgTeacher.setVisibility(View.GONE);
        text_student = findViewById(R.id.text_student);
        checkbox = findViewById(R.id.checkbox);
        text_teacher = findViewById(R.id.text_teacher);
        selectUserType = tvUserType.getText().toString().trim();
    }

    private void onclickListener() {
        findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        findViewById(R.id.llUserType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    showDialog();
                PopupWindow popupWindow = popupDisplayGroup();
                popupWindow.showAsDropDown(view);
            }
        });
        linearStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value_student == 0) {
                    imgTeacher.setVisibility(View.GONE);
                    text_teacher.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    imgStudent.setVisibility(View.VISIBLE);
                    text_student.setTextColor(ContextCompat.getColor(mActivity, R.color.colorAccent));
                    tvUserType.setText("student");
                    selectUserType = tvUserType.getText().toString().trim();
                    value_student = 1;
                } else {
                    imgStudent.setVisibility(View.GONE);
                    value_student = 0;
                    text_student.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    tvUserType.setText("Select User Type");
                    selectUserType = tvUserType.getText().toString().trim();
                }
            }
        });
        linearTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value_teacher == 0) {
                    imgStudent.setVisibility(View.GONE);
                    text_student.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    imgTeacher.setVisibility(View.VISIBLE);
                    text_teacher.setTextColor(ContextCompat.getColor(mActivity, R.color.colorAccent));
                    tvUserType.setText("teacher");
                    selectUserType = tvUserType.getText().toString().trim();
                    value_teacher = 1;
                } else {
                    imgTeacher.setVisibility(View.GONE);
                    text_teacher.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color));
                    tvUserType.setText("Select User Type");
                    selectUserType = tvUserType.getText().toString().trim();
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

        llUserDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radioMale:
                        gender = "male";
                        break;
                    case R.id.radioFeMale:
                        gender = "female";
                        break;
                    case R.id.radioOthers:
                        gender = "other";
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        utils.openActivity(mActivity, LogInActivity.class);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void showDialog() {
        final Dialog
                dialog = new Dialog(getWindow().getContext());
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user_type);
        // TextView tvAddHidi = dialog.findViewById(R.id.tvAddPost);
        dialog.show();
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
                tvUserType.setText("student");
                popupWindow.dismiss();
            }
        });

        tvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvUserType.setText("teacher");
                popupWindow.dismiss();

            }
        });
        popupWindow.setClippingEnabled(true);

        return popupWindow;
    }

    private void callAPI() {

        if (validation()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("phone", etMobile.getText().toString().trim());
                requestServer.sendStringPost(UrlManager.GET_NEW_USER, jsonObject, REQ_REGISTER, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("status")) {
                if (jsonObject.getString("status") == "false") {
                    Intent intent = new Intent(SignUp.this, VerifyOtp.class);
                    intent.putExtra("from", "register");
                    intent.putExtra("mobile", etMobile.getText().toString().trim());
                    intent.putExtra("email", etEmail.getText().toString().trim());
                    intent.putExtra("fname", etName.getText().toString().trim());
                    intent.putExtra("lname", etLastName.getText().toString().trim());
                    intent.putExtra("password", etPassword.getText().toString().trim());
                    intent.putExtra("gender", gender);
                    intent.putExtra("dob", String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
                    intent.putExtra("user_type", selectUserType);
                    startActivity(intent);
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


    private boolean validation() {
        boolean bool = false;
        try {

            if (etName.getText().toString().trim().equals("")) {
                // etName.setError("Enter your name");
                Snackbar.make(etName, "Enter your name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etName.getText().toString().trim().length() < 3) {
                // etName.setError("Name is too short");
                Snackbar.make(etName, "Name is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etLastName.getText().toString().trim().equals("")) {
                // etName.setError("Enter your name");
                Snackbar.make(etLastName, "Enter your last name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etLastName.getText().toString().trim().length() < 3) {
                // etName.setError("Name is too short");
                Snackbar.make(etLastName, "Last name is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etEmail.getText().toString().trim().equals("")) {
                //   etEmail.setError("Enter email id");
                Snackbar.make(etEmail, "Enter email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (!Validator.isValidEmail(etEmail.getText().toString().trim())) {
                //etEmail.setError("Enter valid email id");
                Snackbar.make(etEmail, "Enter valid email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etMobile.getText().toString().trim().equals("")) {
                //  etPassword.setError("Enter password");
                Snackbar.make(etMobile, "Enter mobile number", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etMobile.getText().toString().trim().length() < 9) {
                //etPassword.setError("password is too short");
                Snackbar.make(etMobile, "Enter valid mobile number", Snackbar.LENGTH_SHORT).show();
                bool = false;
            }  /*else if (etPassword.getText().toString().trim().equals("")) {
                //  etPassword.setError("Enter password");
                Snackbar.make(etPassword, "Enter password", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etPassword.getText().toString().trim().length() < 3) {
                //etPassword.setError("password is too short");
                Snackbar.make(etPassword, "password is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            }*/ else if (selectUserType.equalsIgnoreCase("Select User Type")) {
                Snackbar.make(tvUserType, "Select User Type", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (strCheckbox.equalsIgnoreCase("false")) {
                Snackbar.make(tvUserType, "Please Accept Terms And Conditions", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else
                bool = true;

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return bool;
    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        year = y;
                        month = m;
                        day = d;
                        tvDob.setText(String.valueOf(day) + "-" + str[m] + "-" + String.valueOf(year));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
