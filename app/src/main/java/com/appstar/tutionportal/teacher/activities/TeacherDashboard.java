package com.appstar.tutionportal.teacher.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.interfaces.NetworkResponse;
import com.appstar.tutionportal.util.NetworkChangeReceiver;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
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

import java.util.List;
import java.util.Locale;

public class TeacherDashboard extends AppCompatActivity implements NetworkResponse {
    public static FragmentManager fragmentManager;
    private static Utils utils;
    private static SharePreferenceData sharePreferenceData;
    boolean doubleBackToExitPressedOnce = false;
    Double latitude, longitude;
    AlertDialog alertDialog;
    boolean bool = false;
    private NetworkChangeReceiver networkChangeReceiver;
    private Activity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_dashboard);
        mActivity = this;
        fragmentManager = getSupportFragmentManager();
        utils = new Utils();
        sharePreferenceData = new SharePreferenceData();
        //requestServer = new RequestServer(mActivity, this);
        networkChangeReceiver = new NetworkChangeReceiver(mActivity, this);
        setUpGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();

        // viewClassFragment = new ViewClassFragment();
        /*Intent intent = new Intent();
        networkChangeReceiver.onReceive(mActivity, intent);*/

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(networkChangeReceiver, filter);
        utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, false);
    }

    @Override
    public void onBackPressed() {
        switch (Utils.getCurrentScreen()) {

            case FragmentNames._TEACHER_HOME:
                onBack();
                break;

            case FragmentNames._EDIT_TEACHER_PROFILE:
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                break;

            case FragmentNames._ADD_CLASS:
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                break;

            case FragmentNames._EDIT_PROFILE_MORE:
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                break;

            case FragmentNames._EDIT_CLASS:
                utils.openFragment(mActivity, FragmentNames.TEACHER_HOME, FragmentNames._TEACHER_HOME, null, true);
                break;
        }
    }


    private void onBack() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    private void setUpGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
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
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 200);

                        requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
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
                            // txtAddress.setText("Latitude : " + location.getLatitude() + " , Longitude : " + location.getLongitude());
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            sharePreferenceData.setLatitude(mActivity, latitude.toString());
                            sharePreferenceData.setLongitude(mActivity, longitude.toString());
                            // aLocation.setText(getAddressFromLatLng(location));
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
            sharePreferenceData.setLocation(mActivity, address);
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

    @Override
    public void onConnectionChange(boolean isConnected) {
        if (isConnected) {
            if (bool == true) {
                alertDialog.dismiss();
                //viewClassFragment.callAPI();
               /* List<Fragment> fragList =
               ger.getFragments();
                for (int i = 0; i < fragList.size(); i++){
                    if (fragList.get(i) instanceof ViewClassFragment){
                        ((ViewClassFragment) fragList.get(i)).callAPI();
                    } else if (fragList.get(i) instanceof ProfileTeacherFragment){
                        ((ViewClassFragment) fragList.get(i)).callAPI();
                    }
                }*/
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
}
