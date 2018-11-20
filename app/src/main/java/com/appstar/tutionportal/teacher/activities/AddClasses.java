package com.appstar.tutionportal.teacher.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appstar.avatar.TinderRecyclerView;
import com.appstar.common.BindData;
import com.appstar.common.GetSearchLocation;
import com.appstar.customgallery.GalleryFolder;
import com.appstar.customgallery.ImageGallery;
import com.appstar.customgallery.MediaChooserConstants;
import com.appstar.tutionportal.Dialog.DialogAddSubject;
import com.appstar.tutionportal.Model.Branch;
import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.ClassImage;
import com.appstar.tutionportal.Model.IFinishDrag;
import com.appstar.tutionportal.Model.Subject;
import com.appstar.tutionportal.Model.SubjectList;
import com.appstar.tutionportal.Model.SubjectTeacherList;
import com.appstar.tutionportal.Model.TeacherDetail;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.institute.adapter.SearchTeacherAdapter;
import com.appstar.tutionportal.list.GetClassList;
import com.appstar.tutionportal.student.extras.FilePath;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.adapter.AddSubjectAdapter;
import com.appstar.tutionportal.teacher.adapter.AvatarAdapter;
import com.appstar.tutionportal.teacher.adapter.GetClassAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.ProgressUtil;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.views.MyTextView;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddClasses extends AppCompatActivity implements View.OnClickListener, OnResponseListener {
    private final static int PLACE_PICKER_REQUEST = 999;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public String choosedTimeFrom, choosedTimeTo;
    public TextView aLocation, aTimeTo, aTimeFrom, aDate, tvEndDate, tvLocation, tvAddClass, tvAddYourClass, tvClassName,
            tvInstituteNmae, tvBranchName, tvTeacherAssigned, tvAddMoreSub, tvSubject;
    public EditText etPrice, etClassPhone, tvDays, batchName, aLimit;
    public String lat, longt, strCity, strLandmark, strHouse_no, class_selected_id, subject_selected_id, endDate, startingDate, check_day;
    public AutoCompleteTextView aClass, aSubject;
    public TinderRecyclerView recyclerView;
    public List<String> listData = new ArrayList<>();
    public RecyclerView rvAddSubject;
    BindData bindData;
    boolean progress_bar_visibility;
    LinearLayout llAddInstitute;
    String path1;
    int i;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    AvatarAdapter adapter;
    List<String> image = new ArrayList<String>();
    LinearLayoutManager linearLayoutManager;
    RequestServer requestServer;
    SearchTeacherAdapter searchTeacherAdapter;
    int class_image_count = 0;
    Double latitude, longitude;
    String scheduledTime = "";
    String Subject_id_list, subject_name_list;
    int year, day, month;
    String[] str = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    int REQ_ADD_CLASS = 188;
    int REQ_GET_CLASS = 189;
    int REQ_SUBJECT = 198;
    int UPLOAD_CLASS_IMAGE = 158;
    String batch_id;
    int SELECT_PICTURE = 899;
    List<String> listData_data = new ArrayList<>();
    ArrayList<String> listImage = new ArrayList<>();
    int selectedPosition = 0;
    CardView cvAddClass;
    ImageView ivBack;
    Dialog dialog;
    String from, clas, class_id;
    int select_Location = 159;
    AutoCompleteTextView addSubject;
    List<Subject> subjectList = new ArrayList<>();
    List<Subject> selectedSubjectListName = new ArrayList<>();
    LinearLayoutManager llGetClass;
    String branch_id, branch_name, institute_id, institute_name, teacher_id_list, branch_address, branch_latitude, branch_longitude;
    int REQ_TEACHER_LIST = 108;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar progressBar;
    FrameLayout frameLayout;
    Branch branchInfo;
    com.appstar.common.model.Address address;
    List<List<ClassImage>> imageList;
    private GetClassAdapter getClassAdapter;
    private ArrayList<SubjectTeacherList> subjectTeacherLists = new ArrayList<>();
    private SharePreferenceData sharePreferenceData;
    //   private TeacherDetail teacherDetail;
    private Utils utils;
    private Uri filePath;
    private DatePickerDialog datePickerDialog;
    private List<String> listItemsList = new ArrayList<>();
    private List<String> ListSubject = new ArrayList<>();
    private Activity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private Switch aSwitch;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private List<TeacherDetail> teacherDetails = new ArrayList<>();
    private List<ClassDetail> classImage = new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationClient;
    private MyTextView tvLayoutName;
    private MyTextView btnAddClass;
    private ProgressUtil progressUtil;
    private HorizontalScrollView horizontalScrollview;
    private LinearLayoutManager llmTeacherclass;
    private AutoCompleteTextView addTeacherAssigned;
    private DialogAddSubject dialogAddSubject;
    private ClassDetail classDetail;
    private ArrayList<Object> getClassLists = new ArrayList<>();
    private ArrayList<Object> teacherLists = new ArrayList<>();
    private ArrayList<Object> getSubjectLists = new ArrayList<>();
    private ProgressBar progressBar_result;
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object obj = getClassLists.get(i);
                    if (obj instanceof GetClassList) {
                        aClass.setText(((GetClassList) obj).getClass_name());
                        class_selected_id = (((GetClassList) obj).getClass_id());
                    } else {
                        aSubject.setText(((SubjectList) obj).getSubjectName());
                        subject_selected_id = (((SubjectList) obj).getSubjectId());
                    }

                }
            };
    private AdapterView.OnItemClickListener onItemSubjectClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object obj = getSubjectLists.get(i);
                    if (obj instanceof GetClassList) {
                        aClass.setText(((GetClassList) obj).getClass_name());
                        subject_selected_id = (((GetClassList) obj).getClass_id());
                    } else {
                        aSubject.setText(((SubjectList) obj).getSubjectName());
                        subject_selected_id = (((SubjectList) obj).getSubjectId());
                    }

                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_classes);

        mActivity = AddClasses.this;
        bindData = new BindData();
        utils = new Utils();
        requestServer = new RequestServer(mActivity, this);
        progressUtil = new ProgressUtil();
        dialogAddSubject = new DialogAddSubject();
        getClassData();
        getSubjectData();
        findViews();

        sharePreferenceData = new SharePreferenceData();
        setUpGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();
        Utils.checkInternetConnection(mActivity);
        getData();
    }

    private void getData() {
        clas = getIntent().getStringExtra("class");
        if (clas.equalsIgnoreCase("addClass")) {
            tvAddClass.setText("Add class");
            from = getIntent().getStringExtra("from");
            if (from.equalsIgnoreCase("institute")) {
                branchInfo = (Branch) getIntent().getSerializableExtra("obj");
                showHideMoreSubject();
            } else {
            }
        } else {
            from = getIntent().getStringExtra("editFrom");
            tvAddClass.setText("Edit class");
            class_id = getIntent().getStringExtra("class_id");
            if (from.equalsIgnoreCase("institute")) {
                if (Data.getClassList().size() > 0) {
                    showHideMoreSubject();
                    ClassDetail classDetail = getFilterClassByBranchId();
                    bindData(classDetail);
                }
            } else {
                if (Data.getClassList().size() > 0) {
                    ClassDetail classDetail = getFilterClassByBranchId();
                    bindData(classDetail);
                }
            }

        }

        showHideViews();
    }

    private ClassDetail getFilterClassByBranchId() {
        ClassDetail classDetail = null;
        for (ClassDetail obj : Data.getClassList()) {
            if (obj.getId().equals(class_id)) {
                classDetail = obj;
            }
        }
        return classDetail;
    }

    private void bindData(final ClassDetail classDetail) {
        bindData.bindAllData(mActivity, this, classDetail);
        //  imageList = Arrays.asList(classDetail.getClassImage());
        listData.clear();
        try {
            for (int i = 0; i < classDetail.getClassImage().size(); i++) {
                listData.add(classDetail.getClassImage().get(i).getImageUrl());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            for (int i = 0; i < classDetail.getSubjectname().size(); i++) {
                if (subject_name_list == null) {
                    subject_name_list = classDetail.getSubjectname().get(i).getSubject() + ",";
                } else {
                    subject_name_list += classDetail.getSubjectname().get(i).getSubject() + ",";

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (subject_name_list.endsWith(",")) {
            subject_name_list = subject_name_list.substring(0, subject_name_list.length() - 1);
            aSubject.setText(subject_name_list);
        }
        setRecycleView();
    }

    private void showHideViews() {
        if (from.equalsIgnoreCase("Individual")) {
            rvAddSubject.setVisibility(View.GONE);
            tvAddMoreSub.setVisibility(View.GONE);
            tvSubject.setVisibility(View.VISIBLE);
            aSubject.setVisibility(View.VISIBLE);
            tvLocation.setVisibility(View.VISIBLE);
            aLocation.setVisibility(View.VISIBLE);
            tvTeacherAssigned.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
            tvAddYourClass.setVisibility(View.VISIBLE);
            llAddInstitute.setVisibility(View.GONE);
        } else {
            //tvSubject.setVisibility(View.GONE);
            aSubject.setVisibility(View.GONE);
            tvLocation.setVisibility(View.GONE);
            aLocation.setVisibility(View.GONE);
            tvAddYourClass.setVisibility(View.GONE);
            tvTeacherAssigned.setVisibility(View.VISIBLE);
            llAddInstitute.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
            if (subjectList.size() >= 6) {
                tvAddMoreSub.setVisibility(View.GONE);
            }
        }

        if (from.equalsIgnoreCase("individual")) {
            tvInstituteNmae.setText("Add Class");
        } else {
            tvInstituteNmae.setText(branchInfo.getInstituteName());
            tvBranchName.setText(branchInfo.getName());
        }
    }

    private void findViews() {

        batchName = findViewById(R.id.BatchName);
        addTeacherAssigned = findViewById(R.id.addTeacherAssigned);
        tvTeacherAssigned = findViewById(R.id.tvTeacherAssigned);
        tvAddYourClass = findViewById(R.id.tvAddYourClass);
        llAddInstitute = findViewById(R.id.llAddClass);
        frameLayout = findViewById(R.id.frameLayout);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        addTeacherAssigned.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString();
                if (value.length() > 3) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean digitsOnly = TextUtils.isDigitsOnly(addTeacherAssigned.getText().toString().trim());
                            if (addTeacherAssigned.getText().toString().contains("@")) {
                                getTeacherList("email");
                            } else if (digitsOnly) {
                                getTeacherList("phone");
                            } else {
                                getTeacherList("first_name");
                            }
                        }
                    }, 400);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvInstituteNmae = findViewById(R.id.tvInstituteNmae);
        tvBranchName = findViewById(R.id.tvBranchName);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvDays = findViewById(R.id.tvDays);
        aSwitch = findViewById(R.id.switch1);

        recyclerView = findViewById(R.id.recyclerView);
        horizontalScrollview = findViewById(R.id.horizontalScrollview);
        aClass = findViewById(R.id.AddClass);
        aSubject = findViewById(R.id.AddSubject);
        aTimeTo = findViewById(R.id.AddTimeTo);
        aTimeFrom = findViewById(R.id.AddTimeFrom);
        aLimit = findViewById(R.id.AddLimit);
        aDate = findViewById(R.id.AddDate);
        etPrice = findViewById(R.id.etPrice);

        tvAddMoreSub = findViewById(R.id.tvAddMoreSub);
        tvSubject = findViewById(R.id.tvSubject);

        rvAddSubject = findViewById(R.id.rvAddSubject);
        etClassPhone = findViewById(R.id.etClassPhone);
        llmTeacherclass = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvAddSubject.setLayoutManager(llmTeacherclass);
        cvAddClass = findViewById(R.id.cvAddClass);
        tvAddClass = findViewById(R.id.tvAddClass);
        progressBar_result = findViewById(R.id.progress_data);
        progressBar_result.setVisibility(View.GONE);


        cvAddClass.setOnClickListener(this);
        tvAddClass.setOnClickListener(this);


        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        tvAddMoreSub.setOnClickListener(this);
        aTimeTo.setOnClickListener(this);
        aDate.setOnClickListener(this);
        aTimeFrom.setOnClickListener(this);
        aLocation = findViewById(R.id.AddLocation);
        tvLocation = findViewById(R.id.tvLocation);
        aLocation.setOnClickListener(this);

        tvDays.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);

        setRecycleView();
    }


    private void getTeacherList(String column) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            String url = UrlManager.GET_TEACHER_DATA;
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", addTeacherAssigned.getText().toString());
            //jsonObject.put("subject_id", Subject_id_list);
            jsonObject.put("column", column);
            requestServer.sendStringPostWithHeader(url, jsonObject, REQ_TEACHER_LIST, false);

        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddTimeTo:
                Calendar mCurrentTimeTO = Calendar.getInstance();
                int hour = mCurrentTimeTO.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTimeTO.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
                    int callCount = 0;   //To track number of calls to onTimeSet()

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (callCount == 0) {
                            String choosedHour = "";
                            String choosedMinute = "";
                            String choosedTimeZone = "";
                            scheduledTime = selectedHour + ":" + selectedMinute;

                            if (selectedHour > 12) {
                                choosedTimeZone = "PM";
                                selectedHour = selectedHour - 12;
                                if (selectedHour < 10) {
                                    choosedHour = "0" + selectedHour;
                                } else {
                                    choosedHour = "" + selectedHour;
                                }
                            } else {
                                choosedTimeZone = "AM";
                                if (selectedHour < 10) {
                                    choosedHour = "0" + selectedHour;
                                } else {
                                    choosedHour = "" + selectedHour;
                                }
                            }

                            if (selectedMinute < 10) {
                                choosedMinute = "0" + selectedMinute;
                            } else {
                                choosedMinute = "" + selectedMinute;
                            }
                            choosedTimeTo = choosedHour + ":" + choosedMinute + " " + choosedTimeZone;
                            aTimeTo.setText(choosedTimeTo);
                        }
                        callCount++;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Starting Time");
                mTimePicker.show();

                break;
            case R.id.AddTimeFrom:
                Calendar mCurrentTimeFrom = Calendar.getInstance();
                int hourFrom = mCurrentTimeFrom.get(Calendar.HOUR_OF_DAY);
                int minuteFrom = mCurrentTimeFrom.get(Calendar.MINUTE);
                TimePickerDialog mTimePickerFrom;
                mTimePickerFrom = new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
                    int callCount = 0;   //To track number of calls to onTimeSet()

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (callCount == 0) {
                            String choosedHourFrom = "";
                            String choosedMinuteFrom = "";
                            String choosedTimeZoneFrom = "";
                            scheduledTime = selectedHour + ":" + selectedMinute;

                            if (selectedHour > 12) {
                                choosedTimeZoneFrom = "PM";
                                selectedHour = selectedHour - 12;
                                if (selectedHour < 10) {
                                    choosedHourFrom = "0" + selectedHour;
                                } else {
                                    choosedHourFrom = "" + selectedHour;
                                }
                            } else {
                                choosedTimeZoneFrom = "AM";
                                if (selectedHour < 10) {
                                    choosedHourFrom = "0" + selectedHour;
                                } else {
                                    choosedHourFrom = "" + selectedHour;
                                }
                            }
                            if (selectedMinute < 10) {
                                choosedMinuteFrom = "0" + selectedMinute;
                            } else {
                                choosedMinuteFrom = "" + selectedMinute;
                            }
                            choosedTimeFrom = choosedHourFrom + ":" + choosedMinuteFrom + " " + choosedTimeZoneFrom;
                            if (!choosedTimeFrom.equals(aTimeTo.getText().toString())) {
                                aTimeFrom.setText(choosedTimeFrom);

                            } else {
                                Snackbar.make(aTimeFrom, "Please Select Different Time", Snackbar.LENGTH_SHORT).show();
                                //   Toast.makeText(getApplicationContext(), "please select different time", Toast.LENGTH_LONG);
                            }
                        }
                        callCount++;
                    }
                }, hourFrom, minuteFrom, false);//Yes 24 hour time
                mTimePickerFrom.setTitle("Select Closing Time");
                mTimePickerFrom.show();
                break;
            case R.id.AddDate:
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
                                aDate.setText(String.valueOf(day) + "-" + str[m] + "-" + String.valueOf(year));
                            }
                        }, year, month, day);
                startingDate = year + "-" + month + "-" + day;
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.linear_addImage:
                openImagePicker();
                break;

            case R.id.tvAddMoreSub:
                ArrayList<Object> subjectList = new ArrayList<>();
                subjectList.addAll(getSubjectLists);
                dialogAddSubject.showDialog(mActivity, this, subjectList);
                break;

            case R.id.tvEndDate:
                final Calendar cc = Calendar.getInstance();
                year = cc.get(Calendar.YEAR);
                month = cc.get(Calendar.MONTH);
                day = cc.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog nDatePicker = new DatePickerDialog(mActivity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                year = y;
                                month = m;
                                day = d;
                                tvEndDate.setText(String.valueOf(day) + "-" + str[m] + "-" + String.valueOf(year));
                            }
                        }, year, month, day);
                endDate = year + "-" + month + "-" + day;

                nDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                nDatePicker.show();
                break;
            case R.id.iv_add_image:
                openImagePicker();
                break;
            case R.id.btnAddClass:
                callAPI();
                break;
            case R.id.cvAddClass:
                callAPI();
                break;
            case R.id.tvAddClass:
                if (clas.equalsIgnoreCase("addClass"))
                    callAPI();
                else
                    editApi();
                break;
            case R.id.tvDays:
                showNameDialog();
                break;
            case R.id.ivBack:
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                break;
            case R.id.AddLocation:
                Intent intent = new Intent(this, GetSearchLocation.class);
                intent.putExtra("key", select_Location);
                startActivityForResult(intent, select_Location);

        }
    }

    private void getClassData() {
        try {
            String url = UrlManager.GET_CLASS;
            requestServer.getRequest(url, REQ_GET_CLASS, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getSubjectData() {
        try {
            String url = UrlManager.GET_SUBJECT;
            requestServer.getRequest(url, REQ_SUBJECT, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callAPI() {
        if (validation()) {
            try {
                JSONObject jsonObject = new JSONObject();
                if (check_day.endsWith(",")) {
                    check_day = check_day.substring(0, check_day.length() - 1);
                }
                if (from.equalsIgnoreCase("individual")) {
                    jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
                    jsonObject.put("address", aLocation.getText().toString().trim());
                    jsonObject.put("house_no", strHouse_no);
                    jsonObject.put("city", strCity);
                    jsonObject.put("landmark", strLandmark);
                    jsonObject.put("latitude", lat);
                    jsonObject.put("longitude", longt);
                    jsonObject.put("subject_id", subject_selected_id);
                    jsonObject.put("student_limit", aLimit.getText().toString().trim());
                    jsonObject.put("category_type", "0");
                    jsonObject.put("institute_id", "0");
                    jsonObject.put("branch_id", "0");
                } else {

              /*  if (teacher_id_list.endsWith(",")) {
                    teacher_id_list = teacher_id_list.substring(0, teacher_id_list.length() - 1);
                }*/

                    if (Subject_id_list.endsWith(",")) {
                        Subject_id_list = Subject_id_list.substring(0, Subject_id_list.length() - 1);
                    }
                    jsonObject.put("teacher_id", "1");
                    jsonObject.put("address", branchInfo.getAddress());
                    jsonObject.put("latitude", branchInfo.getLatitude());
                    jsonObject.put("longitude", branchInfo.getLongitude());
                    jsonObject.put("house_no", branchInfo.getHouseNo());
                    jsonObject.put("city", branchInfo.getCity());
                    jsonObject.put("landmark", branchInfo.getLandmark());
                    jsonObject.put("subject_id", Subject_id_list);
                    jsonObject.put("institute_id", branchInfo.getInstituteId());
                    jsonObject.put("branch_id", branchInfo.getBranchId());
                    jsonObject.put("category_type", "1");
                }

                jsonObject.put("class_id", class_selected_id);
                jsonObject.put("timing_to", aTimeFrom.getText().toString().trim());
                jsonObject.put("timing_from", aTimeTo.getText().toString().trim());
                jsonObject.put("starting_date", startingDate);
                jsonObject.put("end_date", endDate);
                jsonObject.put("phone", etClassPhone.getText().toString().trim());
                jsonObject.put("week_days", check_day);
                jsonObject.put("price", etPrice.getText().toString().trim());
                jsonObject.put("student_limit", aLimit.getText().toString());
                jsonObject.put("batch_name", batchName.getText().toString());
                tvAddClass.setText("Adding class...");
                progressBar_result.setVisibility(View.VISIBLE);
                progress_bar_visibility = true;
                requestServer.sendStringPostWithHeader(UrlManager.ADD_CLASS, jsonObject, REQ_ADD_CLASS, false);

                //  }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void editApi() {
        if (validation()) {
            try {
                JSONObject jsonObject = new JSONObject();
                if (check_day.endsWith(",")) {
                    check_day = check_day.substring(0, check_day.length() - 1);
                }
                if (from.equalsIgnoreCase("individual")) {
                    jsonObject.put("teacher_id", Data.getTeacherDetail().getId());
                    jsonObject.put("batch_id", class_id);
                    jsonObject.put("address", aLocation.getText().toString().trim());
                    jsonObject.put("house_no", strHouse_no);
                    jsonObject.put("city", strCity);
                    jsonObject.put("landmark", strLandmark);
                    jsonObject.put("latitude", lat);
                    jsonObject.put("longitude", longt);
                    jsonObject.put("subject_id", subject_selected_id);
                    jsonObject.put("student_limit", aLimit.getText().toString().trim());
                    jsonObject.put("category_type", "0");
                    jsonObject.put("institute_id", "0");
                    jsonObject.put("branch_id", "0");
                } else {

              /*  if (teacher_id_list.endsWith(",")) {
                    teacher_id_list = teacher_id_list.substring(0, teacher_id_list.length() - 1);
                }*/

                    if (Subject_id_list.endsWith(",")) {
                        Subject_id_list = Subject_id_list.substring(0, Subject_id_list.length() - 1);
                    }
                    jsonObject.put("teacher_id", "1");
                    jsonObject.put("batch_id", class_id);
                    jsonObject.put("address", branchInfo.getAddress());
                    jsonObject.put("latitude", branchInfo.getLatitude());
                    jsonObject.put("subject_id", Subject_id_list);
                    jsonObject.put("longitude", branchInfo.getLongitude());
                    jsonObject.put("institute_id", branchInfo.getInstituteId());
                    jsonObject.put("branch_id", branchInfo.getBranchId());
                    jsonObject.put("category_type", "1");
                }

                jsonObject.put("class_id", class_selected_id);
                jsonObject.put("timing_to", aTimeFrom.getText().toString().trim());
                jsonObject.put("timing_from", aTimeTo.getText().toString().trim());
                jsonObject.put("starting_date", startingDate);
                jsonObject.put("end_date", endDate);
                jsonObject.put("phone", etClassPhone.getText().toString().trim());
                jsonObject.put("week_days", check_day);
                jsonObject.put("price", etPrice.getText().toString().trim());
                jsonObject.put("student_limit", aLimit.getText().toString());
                jsonObject.put("batch_name", batchName.getText().toString());
                tvAddClass.setText("Adding class...");
                progressBar_result.setVisibility(View.VISIBLE);
                progress_bar_visibility = true;
                requestServer.sendStringPostWithHeader(UrlManager.ADD_CLASS, jsonObject, REQ_ADD_CLASS, false);

                //  }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validation() {
        boolean bool = false;
        try {
            if (from.equalsIgnoreCase("individual")) {
                if (subject_selected_id.trim().equals("")) {
                    Snackbar.make(aSubject, "Enter Valid Subject Name Otherwise Register Your Class", Snackbar.LENGTH_SHORT).show();
                    bool = false;
                } else if (aLocation.getText().toString().trim().equals("")) {
                    Snackbar.make(aClass, "Please select location", Snackbar.LENGTH_SHORT).show();
                    bool = false;
                }
            } else {
                if (Subject_id_list.trim().equals("")) {
                    Snackbar.make(aSubject, "Enter Valid Subject Name Otherwise Register Your Class", Snackbar.LENGTH_SHORT).show();
                    bool = false;
                }
            }
            if (batchName.getText().toString().trim().equals("")) {
                Snackbar.make(batchName, "Enter Your Batch Name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (batchName.getText().toString().trim().length() < 3) {
                Snackbar.make(batchName, "Your Class Name Is Too Short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (class_selected_id.equals("")) {
                Snackbar.make(aClass, "Enter Valid Class Name Otherwise Register Your Class", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (aClass.getText().toString().trim().length() < 3) {
                Snackbar.make(aClass, "Enter Valid Class Name Otherwise Register Your Class", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (aTimeFrom.getText().toString().trim().equals("")) {
                Snackbar.make(aTimeFrom, "Enter Starting Time", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (aTimeTo.getText().toString().trim().equals("")) {
                Snackbar.make(aTimeTo, "Enter Closing Time", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (aDate.getText().toString().trim().equals("")) {
                Snackbar.make(aDate, "Enter Starting Date", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (tvEndDate.getText().toString().trim().equals("")) {
                Snackbar.make(aDate, "Enter End Date", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (aLimit.getText().toString().trim().equals("")) {
                Snackbar.make(aLimit, "Enter Student Limit ", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etClassPhone.getText().toString().trim().equals("")) {
                Snackbar.make(etClassPhone, "Enter Student Limit ", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etClassPhone.getText().toString().length() < 9) {
                Snackbar.make(etClassPhone, "Enter 10 digit mobile number", Snackbar.LENGTH_SHORT).show();
            } else if (etPrice.getText().toString().trim().equals("")) {
                Snackbar.make(etPrice, "Enter price", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (batchName.getText().toString().trim().equals("")) {
                Snackbar.make(aLimit, "Enter batch name ", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (TextUtils.isEmpty(listData.get(0))) {
                Snackbar.make(aClass, "Please select an image", Snackbar.LENGTH_SHORT).show();
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
            JSONObject jsonObject = new JSONObject(response);
            if (reqCode == REQ_ADD_CLASS) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        jsonObject = jsonObject.getJSONObject("data");
                        batch_id = jsonObject.getString("batch_id");
                        tvAddClass.setText("Uploading Images...");
                        final HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("batch_id", batch_id);


                        requestServer.uploadClassImage(UPLOAD_CLASS_IMAGE, UrlManager.ADD_CLASS_IMAGE, listData, hashMap, false);

                      /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                requestServer.uploadClassImage(UPLOAD_CLASS_IMAGE, UrlManager.ADD_CLASS_IMAGE, listData, hashMap, false);
                            }
                        });*/
                        //   finish();
                    } else {
                        Toast.makeText(mActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mActivity, "Error to save", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == REQ_GET_CLASS) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jo1 = jsonObject.getJSONArray("data");
                        if (jo1.length() > 0) {
                            for (int i = 0; i < jo1.length(); i++) {
                                JSONObject c = jo1.getJSONObject(i);
                                GetClassList getClassList = new GetClassList();
                                getClassList.setClass_id(c.getString("class_id"));
                                getClassList.setClass_name(c.getString("class_name"));
                                getClassLists.add(getClassList);
                                Log.e("CLASS_LIST", getClassLists.toString());
                            }
                            getClassAdapter = new GetClassAdapter(mActivity, R.layout.listview_item, getClassLists, true);
                            aClass.setThreshold(1);
                            aClass.setAdapter(getClassAdapter);
                            aClass.setSelection(aClass.getText().toString().trim().length());
                            aClass.setOnItemClickListener(onItemClickListener);
                        }
                    }
                }
            } else if (reqCode == REQ_SUBJECT) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jo1 = jsonObject.getJSONArray("data");
                        if (jo1.length() > 0) {
                            for (int i = 0; i < jo1.length(); i++) {
                                JSONObject c = jo1.getJSONObject(i);
                                SubjectList subjectList = new SubjectList();
                                subjectList.setSubjectId(c.getString("subject_id"));
                                subjectList.setSubjectName(c.getString("subject_name"));
                                getSubjectLists.add(subjectList);
                            }
                            getClassAdapter = new GetClassAdapter(mActivity, R.layout.listview_item, getSubjectLists, false);
                            aSubject.setThreshold(1);
                            aSubject.setAdapter(getClassAdapter);
                            aSubject.setOnItemClickListener(onItemSubjectClickListener);
                        }
                    }
                }
            } else if (reqCode == REQ_TEACHER_LIST) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jo1 = jsonObject.getJSONArray("data");
                        progressBar.setVisibility(View.GONE);
                        teacherDetails.clear();
                        if (jo1.length() > 0) {
                            for (int i = 0; i < jo1.length(); i++) {
                                Gson gson = new Gson();
                                Type category = new TypeToken<TeacherDetail>() {
                                }.getType();
                                TeacherDetail teacherList = gson.fromJson(jo1.getJSONObject(i).toString(), category);
                                //  teacherDetails.add(teacherList);
                                teacherLists.add(teacherList);
                            }
                            // Log.e("teacher_details",teacherDetails.size());
                            SearchTeacherAdapter searchTeacherAdapter = new SearchTeacherAdapter(mActivity, R.layout.search_teacher_item, teacherLists);
                            addTeacherAssigned.setAdapter(searchTeacherAdapter);
                        } else {
                            Snackbar.make(addTeacherAssigned, "No Teacher Exist", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            } else if (reqCode == UPLOAD_CLASS_IMAGE) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        List<ClassImage> classImages = new ArrayList<>();

                        if (jsonArray.length() > 0)
                            for (i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                ClassImage classImage = new ClassImage();
                                classImage.setImageUrl(jsonObject.getString("image"));
                                classImage.setImageId(jsonObject.getString("id"));
                                classImages.add(classImage);
                            }

                        addClassDetail(batch_id, classImages);
                    /*    progressBar_result.setVisibility(View.GONE);
                        progress_bar_visibility = false;
                        finish();*/
                        //   tvAddClass.setText("Adding Image");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar_result.setVisibility(View.GONE);
                                progress_bar_visibility = false;
                                finish();
                            }
                        });

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity, "Error in response", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    private void addClassDetail(String batch_id, List<ClassImage> classImages) {
        classDetail = new ClassDetail();
        classDetail.setId(batch_id);
        classDetail.setBatchName(batchName.getText().toString().trim());
        classDetail.setClassName(aClass.getText().toString().trim());
        classDetail.setClassId(class_selected_id);
        classDetail.setStudentLimit(aLimit.getText().toString().trim());
        classDetail.setPrice(etPrice.getText().toString().trim());
        classDetail.setPhone(etClassPhone.getText().toString().trim());
        classDetail.setStartingDate(aDate.getText().toString().trim());
        classDetail.setEndDate(tvEndDate.getText().toString().trim());
        classDetail.setTimingFrom(aTimeTo.getText().toString().trim());
        classDetail.setTimingTo(aTimeFrom.getText().toString().trim());
        classDetail.setWeekDays(check_day);
        classDetail.setClassImage(classImages);

        if (from.equalsIgnoreCase("individual")) {
            classDetail.setAddress(address.getFullAddress());
            classDetail.setLatitude(address.getLatitude());
            classDetail.setLongitude(address.getLongitude());
            classDetail.setCity(address.getCity());
            classDetail.setHouseNo(address.getAddress());
            classDetail.setLandmark(address.getLandmark());
            classDetail.setBranchId("0");
            classDetail.setBranchName("0");
            classDetail.setInstituteId("0");
            classDetail.setCategoryType("0");
            classDetail.setSubjectId(subject_selected_id);
            classDetail.setSubjectname(subjectList);
            classDetail.setTeacherId(Data.getTeacherDetail().getId());
        } else {
            classDetail.setAddress(branchInfo.getAddress());
            classDetail.setLatitude(Double.parseDouble(branchInfo.getLatitude()));
            classDetail.setLongitude(Double.parseDouble(branchInfo.getLongitude()));
            classDetail.setCity(branchInfo.getCity());
            classDetail.setHouseNo(branchInfo.getHouseNo());
            classDetail.setLandmark(branchInfo.getLandmark());
            classDetail.setBranchId(branchInfo.getBranchId());
            classDetail.setInstituteId(branchInfo.getInstituteId());
            classDetail.setSubjectId(Subject_id_list);
            classDetail.setSubjectname(subjectList);
            classDetail.setCategoryType("1");
            classDetail.setTeacherId("1");
        }
        Data.getClassList().add(classDetail);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    private void setUpGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        // mLocationRequest.setInterval(10000);
        //mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Utils.log("LOCATION 1", "All location settings are satisfied.");
                        getLastKnownLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Utils.log("LOCATION 2", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(mActivity, 9111);
                        } catch (IntentSender.SendIntentException e) {
                            Utils.log("LOCATION 3", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Utils.log("LOCATION 4", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private void getLastKnownLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                //   updateLocation();
            } else {
                updateLocation();
            }
        } else {
            updateLocation();
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;

    }

    private void updateLocation() {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

//                          txtAddress.setText("Latitude : " + location.getLatitude() + " , Longitude : " + location.getLongitude());
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            aLocation.setText(getAddressFromLatLng(location));

                        }
                    }
                });
    }

    private String getAddressFromLatLng(Location location) {

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(mActivity, Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            Utils.log("LOCATION 1", address);

//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            return address;
        } catch (Exception e) {
            Utils.log("ADDR PARSE EXC", "::::   " + e.getMessage());
        }
        return "No Address Found";
    }

    private void openImagePicker() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                goToImageIntent();
            }
        } else {
            goToImageIntent();
        }

    }

    public void goToImageIntent() {
        // utils.log("intent", "intent");
//        isPermissionGivenAlready = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path1 = FilePath.getPath(mActivity, filePath);
            listData.set(selectedPosition, path1);
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }

      /*  if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mActivity, data);
                //   aLocation.setText(place.getName());
                //  Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(mActivity, data);
                // TODO: Handle the error.
                //  Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }*/
        else if (requestCode == MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE) {
            if (data != null) {
                try {
                    listImage = data.getStringArrayListExtra("paths");
                    setImageAdapter(listImage);
                    // String position1 = data.getStringExtra("position");
                    Log.d("listimage", listImage.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (requestCode == select_Location) {
            if (data != null) {
                address = (com.appstar.common.model.Address) data.getSerializableExtra("obj");
                Log.d("addresslatitude", address.getAddress());
                aLocation.setText(address.getFullAddress());
                lat = String.valueOf(address.getLatitude());
                longt = String.valueOf(address.getLongitude());
                strCity = address.getCity();
                strHouse_no = address.getAddress();
                strLandmark = address.getLandmark();

            }

        }
    }


    private void setImageAdapter(List<String> list) {
        //   Toast.makeText(mActivity, String.valueOf(selectedPosition), Toast.LENGTH_SHORT).show();
        for (String str : list) {
            for (int i = selectedPosition; i < 6; i++) {
                String image = listData.get(i);
                if (TextUtils.isEmpty(image)) {
                    listData.set(i, str);
                    break;
                }
                Log.d("counter_image", String.valueOf(class_image_count));
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        Utils.setCurrentScreen(FragmentNames._ADD_CLASS);
    }

    private void setRecycleView() {
        int size = listData.size();
        //  int count = 6 - size;

        for (int i = 0; i < 6 - size; i++) {
            listData.add("");
        }


        recyclerView.setMoveNext(true);
        adapter = new AvatarAdapter(this, mActivity, listData);
        recyclerView.initRecyclerView(mActivity, adapter);

        adapter.setiFinishDrag(new IFinishDrag() {
            @Override
            public void updateListData(ArrayList<String> listData) {
                for (int i = 0; i < listData.size(); i++) {
                    Log.e("TAG", "" + i + "/" + listData.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void openImageChooser(int postion) {
        int position = postion;
        selectedPosition = position;
        int counter = 0;
        for (String string : listData) {
            if (TextUtils.isEmpty(string)) {
                counter++;
            }
        }

        ImageGallery.MAX_IMAGE_LIMIT = counter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                Intent intent = new Intent(mActivity, GalleryFolder.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE);
            }
        } else {
            startActivityForResult(new Intent(mActivity, GalleryFolder.class), MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE);
        }
    }

    private void showNameDialog() {

        dialog = new Dialog(mActivity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.days_dialog);
        final CheckBox chkAll = dialog.findViewById(R.id.chkAll);
        final CheckBox chkMonday = dialog.findViewById(R.id.chkMonday);
        final CheckBox chkTuesday = dialog.findViewById(R.id.chkTuesday);
        final CheckBox chkWednesday = dialog.findViewById(R.id.chkWednesday);
        final CheckBox chkThursday = dialog.findViewById(R.id.chkThursday);
        final CheckBox chkFriday = dialog.findViewById(R.id.chkFriday);
        final CheckBox chkSaturday = dialog.findViewById(R.id.chkSaturday);
        final CheckBox chkSunday = dialog.findViewById(R.id.chkSunday);

        CardView cardViewOk = dialog.findViewById(R.id.cardViewOk);

        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                chkMonday.setChecked(b);
                chkTuesday.setChecked(b);
                chkWednesday.setChecked(b);
                chkThursday.setChecked(b);
                chkFriday.setChecked(b);
                chkSaturday.setChecked(b);
                chkSunday.setChecked(b);

            }
        });

        cardViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "";
                if (chkAll.isChecked()) {
                    text = "Monday,  Tuesday,  Wednesday,  Thursday,  Friday, Saturday, Sunday  ";
                    check_day = "1,2,3,4,5,6,7 ";
                } else {
                    if (chkMonday.isChecked()) {
                        text += "Monday, ";
                        if (check_day == null) {
                            check_day = "1,";
                        } else {
                            check_day += "1,";
                        }
                    }
                    if (chkTuesday.isChecked()) {
                        text += "Tuesday, ";
                        if (check_day == null) {
                            check_day = "2,";
                        } else {
                            check_day += "2,";
                        }
                    }
                    if (chkWednesday.isChecked()) {
                        text += "Wednesday, ";
                        if (check_day == null) {
                            check_day = "3,";
                        } else {
                            check_day += "3,";
                        }
                    }
                    if (chkThursday.isChecked()) {
                        text += "Thursday, ";
                        if (check_day == null) {
                            check_day = "4,";
                        } else {
                            check_day += "4,";
                        }
                    }
                    if (chkFriday.isChecked()) {
                        text += "Friday, ";
                        if (check_day == null) {
                            check_day = "5,";
                        } else {
                            check_day += "5,";
                        }
                    }
                    if (chkSaturday.isChecked()) {
                        text += "Saturday, ";
                        if (check_day == null) {
                            check_day = "6,";
                        } else {
                            check_day += "6,";
                        }
                    }
                    if (chkSunday.isChecked()) {
                        text += "Sunday, ";
                        if (check_day == null) {
                            check_day = "7,";
                        } else {
                            check_day += "7,";
                        }
                    }
                }
                if (text.length() > 2)
                    text = text.substring(0, text.length() - 2);
                tvDays.setText(text);
                dialog.dismiss();
            }
        });


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 400);
    }

    public void addSubjectList(Subject subject) {
        boolean bool = false;
        for (Subject obj : subjectList) {
            if (obj.getSubject().equalsIgnoreCase(subject.getSubject())) {
                bool = true;
                break;
            }
        }
        if (!bool) {
            subjectList.add(subject);
            if (Subject_id_list == null) {
                Subject_id_list = subject.getSubjectId() + ",";
                subject_name_list = subject.getSubject() + ",";
            } else {
                Subject_id_list += subject.getSubjectId() + ",";
                subject_name_list += subject.getSubject() + ",";
            }

            //   selectedSubjectListName.add(subject_name_list);

            AddSubjectAdapter adapter = new AddSubjectAdapter(mActivity, subjectList, this);
            rvAddSubject.setAdapter(adapter);
            showHideMoreSubject();
        } else
            Toast.makeText(mActivity, "Subject already added.", Toast.LENGTH_SHORT).show();
    }

    public void showHideMoreSubject() {
        if (subjectList.size() >= 6) {
            tvAddMoreSub.setVisibility(View.GONE);
        } else if (subjectList.size() < 1) {
            tvAddMoreSub.setText("Add Subjet");
            tvAddMoreSub.setVisibility(View.VISIBLE);
        } else
            tvAddMoreSub.setText("Add More Subjet");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setOnFinish();
        return;
    }


    private void setOnFinish() {
        if (!progress_bar_visibility) {
            finish();
        } else
            Toast.makeText(getApplicationContext(), "You can't back right now", Toast.LENGTH_SHORT).show();
    }
    /* private List getSubjectIds(List<String>listSubject){
        List<String>sub = new ArrayList<>();
        return sub;
    } */
}