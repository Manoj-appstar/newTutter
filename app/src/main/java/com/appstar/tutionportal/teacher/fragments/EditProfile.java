package com.appstar.tutionportal.teacher.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appstar.tutionportal.Dialog.DialogPhone;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.database.DBHelper;
import com.appstar.tutionportal.student.extras.FilePath;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.SelectUserImage;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.Validator;
import com.appstar.tutionportal.volley.RequestServer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class EditProfile extends Fragment implements OnResponseListener {
    private static Utils utils;
    private static RequestServer requestServer;
    EditText etName, etEmail, etMobile;
    EditText tvDob;
    RadioGroup radioGroup;
    ImageView imgProfile;
    int REQ_UPDATE_DATA = 188;
    int year, day, month;
    LinearLayout llUserDob;
    ImageView change_photo, ivBack, ivMobile, ivMobileEdit;
    ContentResolver mContentResolver;
    String path1;
    String gender = "male";
    String[] str = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    int GET_SELECTED_RESULT = 12345;
    int UPDATE_PROFILE_IMAGE = 12346;
    TextView tvClassName, tvText;
    View dialogView;
    EditText etPhone;
    Button btnChangePhone;
    LinearLayout llVerifyOtp, llChangePhone;
    AlertDialog alertDialog;
    DBHelper dbHelper;
    ProgressBar progress;
    private SharePreferenceData sharePreferenceData;
    private Button btnCreateAccount;
    private Uri filePath;
    private Activity mActivity;
    private RadioButton radioMale, radioFeMale, radioOthers;
    private DialogPhone dialogPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editprofile, container, false);
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        dbHelper = new DBHelper(mActivity);
        utils = new Utils();
        requestServer = new RequestServer(getActivity(), this);
        dialogPhone = new DialogPhone();
        findViews(view);
        setData();
        onclickListener();
        return view;
    }

    private void findViews(View view) {
        ivMobileEdit = view.findViewById(R.id.ivMobileEdit);
        ivBack = view.findViewById(R.id.ivBack);
        tvClassName = view.findViewById(R.id.tvClassName);
        tvClassName.setText("Edit Profile");
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etMobile = view.findViewById(R.id.etMobile);
        change_photo = view.findViewById(R.id.change_photo);
        tvDob = view.findViewById(R.id.tvDob);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioMale = view.findViewById(R.id.radioMale);
        radioFeMale = view.findViewById(R.id.radioFeMale);
        radioOthers = view.findViewById(R.id.radioOthers);
        imgProfile = view.findViewById(R.id.imgProfile);
        progress = view.findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        // ivMobile = view.findViewById(R.id.ivMobile);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);
        if (TextUtils.isEmpty(Data.getTeacherDetail().getImage())) {
            Glide.with(mActivity).load(R.drawable.temp_profile).apply(RequestOptions.circleCropTransform()).into(imgProfile);
        } else {
            Glide.with(mActivity).load(Data.getTeacherDetail().getImage()).apply(RequestOptions.circleCropTransform()).into(imgProfile);
        }
    }

    private void setData() {
        etName.setText(Data.getTeacherDetail().getFirstName());
        etEmail.setText(Data.getTeacherDetail().getEmail());
        tvDob.setText(Data.getTeacherDetail().getDob());
        etMobile.setText(Data.getTeacherDetail().getPhone());
        if (!TextUtils.isEmpty(Data.getTeacherDetail().getGender())) {
            if (Data.getTeacherDetail().getGender().equalsIgnoreCase("Male")) {
                radioMale.setChecked(true);
                gender = "male";
            } else if (Data.getTeacherDetail().getGender().equalsIgnoreCase("Female")) {
                radioFeMale.setChecked(true);
                gender = "female";
            } else {
                radioOthers.setChecked(true);
                gender = "other";
            }
        }
    }

    private void onclickListener() {
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });
        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectUserImage.class);
                startActivityForResult(intent, GET_SELECTED_RESULT);
                //  openImagePicker();
            }
        });

        ivMobileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPhone.showDialog(mActivity, new Runnable() {
                    @Override
                    public void run() {
                        etMobile.setText(Data.getTeacherDetail().getPhone());
                    }
                });

            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "EditProfile");
                utils.openFragment(mActivity, FragmentNames.VIEW_FULL_IMAGE, FragmentNames._VIEW_FULL_IMAGE, bundle, false);

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                //  openImagePicker();
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

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        year = y;
                        month = m;
                        day = d;
                        c.set(year, month, day);
                        Date current = c.getTime();
                        int diff1 = new Date().compareTo(current);
                        tvDob.setText(String.valueOf(day) + "-" + str[m] + "-" + String.valueOf(year));
                    }
                }, year, month, day);
        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path1 = FilePath.getPath(mActivity, filePath);
        } else if (requestCode == GET_SELECTED_RESULT) {
            if (data != null) {
                path1 = data.getStringExtra("path");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", Data.getTeacherDetail().getId());

                if (SharePreferenceData.getUserType(mActivity).equalsIgnoreCase("student")) {
                    hashMap.put("user_type", "1");
                } else if (SharePreferenceData.getUserType(mActivity).equalsIgnoreCase("teacher")) {
                    hashMap.put("user_type", "2");
                } else {
                    hashMap.put("user_type", "3");
                }
                //  progress.setVisibility(View.VISIBLE);
                requestServer.uploadMultiDataWithImage(GET_SELECTED_RESULT, UrlManager.UPDATE_TEACHER_PROFILE_IMAGE, path1, hashMap, true);
            }
        }
    }

    private void callAPI() {
        if (validation()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", Data.getTeacherDetail().getId());
                jsonObject.put("first_name", etName.getText().toString());
                jsonObject.put("email", etEmail.getText().toString());
                jsonObject.put("dob", tvDob.getText().toString());
                jsonObject.put("gender", gender);

                if (SharePreferenceData.getUserType(mActivity).equalsIgnoreCase("student")) {
                    jsonObject.put("user_type", "1");
                } else if (SharePreferenceData.getUserType(mActivity).equalsIgnoreCase("teacher")) {
                    jsonObject.put("user_type", "2");
                } else {
                    jsonObject.put("user_type", "3");
                }

                requestServer.sendStringPostWithHeader(UrlManager.EDIT_TEACHER_PROFILE, jsonObject, REQ_UPDATE_DATA, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validation() {
        boolean bool = false;
        try {
            if (etName.getText().toString().trim().equals("")) {
                Snackbar.make(etName, "Name can't be Blank", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etName.getText().toString().trim().length() < 3) {
                Snackbar.make(etName, "Your  Name Is Too Short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etEmail.getText().toString().trim().equals("")) {
                Snackbar.make(etEmail, "Enter email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (!Validator.isValidEmail(etEmail.getText().toString().trim())) {
                Snackbar.make(etEmail, "Enter valid email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else
                bool = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bool;
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            response = response.replace("\r\n\r\n", "");
            JSONObject jsonObject = new JSONObject(response);
            if (reqCode == REQ_UPDATE_DATA) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Data.getTeacherDetail().setFirstName(etName.getText().toString());
                        Data.getTeacherDetail().setEmail(etEmail.getText().toString());
                        Data.getTeacherDetail().setDob(tvDob.getText().toString());
                        Data.getTeacherDetail().setGender(gender);
                        dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());
                        utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                    }
                }
            } else if (reqCode == GET_SELECTED_RESULT) {
                // progress.setVisibility(View.GONE);
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                      /*  Glide.with(mActivity).load(path1).apply(RequestOptions.circleCropTransform()).into(imgProfile);
                        Data.getTeacherDetail().setImage(path1);
                        dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());*/
                        JSONArray jo1 = jsonObject.getJSONArray("data");
                        if (jo1.length() > 0) {
                            // progressUtil.dismiss();
                            for (int i = 0; i < jo1.length(); i++) {
                                JSONObject c = jo1.getJSONObject(i);
                                Data.getTeacherDetail().setImage(c.getString("image_name"));
                              /*  Glide.with(mActivity).load(Data.getTeacherDetail().getImage()).apply(RequestOptions.circleCropTransform()).into(imgProfile);
                                Data.getTeacherDetail().setImage(Data.getTeacherDetail().getImage());
                                dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());*/
                            }
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(mActivity).load(path1).apply(RequestOptions.circleCropTransform()).into(imgProfile);
                                    Data.getTeacherDetail().setImage(path1);
                                    dbHelper.updateTeacherDetailWithoutId(Data.getTeacherDetail());
                                    //   imgProfile.setImageBitmap(bitmap);
                                }
                            });
                        }


                        // final  Bitmap bitmap = getBitmapFromPath(path);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                //imgProfile.setImageBitmap(bitmap);
                                // BitmapDrawable drawable = (BitmapDrawable) imgProfile.getDrawable();
                                //  String base64 = encodeToBase64(bitmap);

                                sharePreferenceData.setUserImage(mActivity, path1);
                                int a = 10;
                                int b = 11;
                                b = a;
                                a = b;

                            /* getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Glide.with(mActivity).load(sharePreferenceData.getUserImage(mActivity)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                                                @Override
                                                protected void setResource(Bitmap resource) {
                                                    RoundedBitmapDrawable circularBitmapDrawable =
                                                            RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
                                                    circularBitmapDrawable.setCircular(true);
                                                    imgProfile.setImageDrawable(circularBitmapDrawable);
                                                }
                                            });

                                        }
                                    });*/
                                   /* Glide.with(mActivity).load(sharePreferenceData.getUserImage(mActivity)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
                                        @Override
                                        protected void setResource(Bitmap resource) {
                                            RoundedBitmapDrawable circularBitmapDrawable =
                                                    RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
                                            circularBitmapDrawable.setCircular(true);
                                            imgProfile.setImageDrawable(circularBitmapDrawable);
                                        }
                                    });*/

                            }
                        }).start();
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    @Override
    public void onResume() {
        super.onResume();

        Utils.setCurrentScreen(FragmentNames._EDIT_TEACHER_PROFILE);
    }
}