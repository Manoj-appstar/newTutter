package com.appstar.tutionportal.student.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.adapter.AddImageAdapter;
import com.appstar.tutionportal.student.extras.FilePath;
import com.appstar.tutionportal.util.Utils;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AddClass extends Fragment {
    public static LinearLayout linearLayout;
    public static RecyclerView mRecyclerView;
    public String choosedTimeFrom, choosedTimeTo;
    public ImageView add_image;
    RequestServer requestServer;
    int value;
    Double latitude, longitude;
    String scheduledTime = "";
    TextView aTimeTo, aTimeFrom, aDate, aLocation, aAdd;
    int year, day, month;
    String[] str = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String path1;
    int i = 0;
    AddImageAdapter address_adapter;
    List<String> image = new ArrayList<String>();
    LinearLayoutManager linearLayoutManager;
    private Utils utils;
    private Activity mActivity;
    private Switch aSwitch;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private EditText aLimit, aClass, aSubject;
    private Uri filePath;
    private List<String> listItemsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_classes, container, false);
        mActivity = getActivity();
        findViews(view);
        setUpGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();
        // setUpPager();
        return view;
    }

    private void findViews(View view) {
        aSwitch = view.findViewById(R.id.switch1);
        aSwitch.setChecked(true);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    aSwitch.setChecked(true);
                    //  vibrationOnOff = true;
                } else {
                    aSwitch.setChecked(false);
                    //    vibrationOnOff = false;
                }

            }
        });
        aClass = view.findViewById(R.id.AddClass);
        aSubject = view.findViewById(R.id.AddSubject);
        aTimeTo = view.findViewById(R.id.AddTimeTo);
        aTimeTo.setOnClickListener(new OnClick());
        aTimeFrom = view.findViewById(R.id.AddTimeFrom);
        aTimeFrom.setOnClickListener(new OnClick());
        aLimit = view.findViewById(R.id.AddLimit);
        aDate = view.findViewById(R.id.AddDate);

        add_image = view.findViewById(R.id.iv_add_image);
        aDate.setOnClickListener(new OnClick());
        aLocation = view.findViewById(R.id.AddLocation);
        linearLayout = view.findViewById(R.id.linear_addImage);
        add_image = view.findViewById(R.id.iv_add_image);
        mRecyclerView = view.findViewById(R.id.dynamic_create_add_image_recycle);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
                utils.log("latitude", "GhazIabad");
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            } else {
                utils.log("latitude", "else");
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
//                            txtAddress.setText("Latitude : " + location.getLatitude() + " , Longitude : " + location.getLongitude());
                            latitude = location.getLatitude();
                            utils.log("latitude", latitude.toString());
                            longitude = location.getLongitude();
                            aLocation.setText(getAddressFromLatLng(location));
                        } else {
                            utils.log("latitude", "location blank");

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
//        isPermissionGivenAlready = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path1 = FilePath.getPath(mActivity, filePath);
            listItemsList.add(path1);
            address_adapter = new AddImageAdapter(mActivity, listItemsList);
            //mRecyclerView.smoothScrollToPosition(listItemsList.size());
            mRecyclerView.setAdapter(address_adapter);
            address_adapter.notifyDataSetChanged();
            linearLayoutManager.scrollToPositionWithOffset(listItemsList.size(), -1);
            //mRecyclerView.smoothScrollToPosition(listItemsList.size());
            onRemoveImage();
        }
    }

    public void onRemoveImage() {
        //  add_image.setText(listItemsList.size() <= 0 ? "Add Image" : "Add More Image");

    }

    class OnClick implements View.OnClickListener {

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
                    mTimePicker.setTitle("Select To Time");
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
                                    Toast.makeText(getActivity(), "please select different time", Toast.LENGTH_LONG);
                                    }
                            }
                            callCount++;
                        }
                    }, hourFrom, minuteFrom, false);//Yes 24 hour time
                    mTimePickerFrom.setTitle("Select From Time");
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
                    datePickerDialog.show();
                    break;
                case R.id.linear_addImage:
                    openImagePicker();
                    break;
                case R.id.iv_add_image:
                    openImagePicker();
            }
        }
    }


}