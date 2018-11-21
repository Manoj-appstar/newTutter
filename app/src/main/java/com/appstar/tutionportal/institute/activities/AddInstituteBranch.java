package com.appstar.tutionportal.institute.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.appstar.common.GetSearchLocation;
import com.appstar.tutionportal.Dialog.DialogError;
import com.appstar.tutionportal.Model.Branch;
import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.util.UtilsInstitute;
import com.appstar.tutionportal.util.Validator;
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

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddInstituteBranch extends AppCompatActivity implements OnResponseListener {
    EditText etBranchName, etPhone, etEmail, etTimeTo, etTimeFrom, etInstituteName;
    TextView etAddress;
    CardView cvCancel, cvAdd;
    int REQ_ADD_BRANCH = 198;
    Double latitude, longitude;
    String institute_id, institute_name;
    int select_Location = 159;
    com.appstar.common.model.Address address;
    private Activity mActivity;
    private RequestServer requestServer;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_branch);
        mActivity = AddInstituteBranch.this;
        getData();
        requestServer = new RequestServer(this, this);
        findView();
        onClick();
        setUpGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        checkLocationSettings();
    }

    private void getData() {
        institute_id = getIntent().getStringExtra("institute_id");
        institute_name = getIntent().getStringExtra("institute_name");
    }

    private void findView() {
        etInstituteName = findViewById(R.id.etInstituteName);
        etInstituteName.setText(institute_name);
        etBranchName = findViewById(R.id.etBranchName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etTimeTo = findViewById(R.id.etTimeTo);
        etTimeFrom = findViewById(R.id.etTimeFrom);
        cvCancel = findViewById(R.id.cvCancel);
        cvAdd = findViewById(R.id.cvAdd);

    }

    private void onClick() {
        cvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         /*       Intent intent = new Intent(AddInstituteBranch.this, InstituteDashboard.class);
                startActivity(intent);*/
                finish();
            }
        });
        cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });
        etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GetSearchLocation.class);
                intent.putExtra("key", select_Location);
                intent.putExtra("from", "branch");
                startActivityForResult(intent, select_Location);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {

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

        else if (requestCode == select_Location) {
            if (data != null) {
                address = (com.appstar.common.model.Address) data.getSerializableExtra("obj");
                etAddress.setText(address.getFullAddress());
            }
        }
    }

    private void callAPI() {
        if (validation()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", etBranchName.getText().toString().trim());
                jsonObject.put("address", etAddress.getText().toString().trim());
                jsonObject.put("phone_no", etPhone.getText().toString().trim());
                jsonObject.put("institute_id", institute_id);
                jsonObject.put("email_id", etEmail.getText().toString().trim());
                jsonObject.put("latitude", address.getLatitude());
                jsonObject.put("longitude", address.getLongitude());
                jsonObject.put("city", address.getCity());
                jsonObject.put("house_no", address.getAddress());
                jsonObject.put("landmark", address.getLandmark());
                jsonObject.put("timing_to", "6:00 PM");
                jsonObject.put("timing_from", "10:00 AM");
                jsonObject.put("status", "1");
                requestServer.sendStringPostWithHeader(UrlManager.ADD_BRANCH, jsonObject, REQ_ADD_BRANCH, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validation() {
        boolean bool = false;
        try {
            if (etBranchName.getText().toString().trim().equals("")) {

                Snackbar.make(etBranchName, "Enter Branch Name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etBranchName.getText().toString().trim().length() < 3) {

                Snackbar.make(etBranchName, "Enter Branch Name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etAddress.getText().toString().trim().equals("")) {

                Snackbar.make(etAddress, "Enter your Location", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etAddress.getText().toString().trim().length() < 2) {

                Snackbar.make(etAddress, "Location  is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etEmail.getText().toString().trim().equals("")) {

                Snackbar.make(etEmail, "Enter email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (!Validator.isValidEmail(etEmail.getText().toString().trim())) {

                Snackbar.make(etEmail, "Enter valid email id", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etPhone.getText().toString().trim().equals("")) {

                Snackbar.make(etPhone, "Enter mobile number", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etPhone.getText().toString().trim().length() < 9) {

                Snackbar.make(etPhone, "Enter valid mobile number", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else
                bool = true;

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return bool;
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
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            etAddress.setText(getAddressFromLatLng(location));

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


            return address;
        } catch (Exception e) {
            Utils.log("ADDR PARSE EXC", "::::   " + e.getMessage());
        }
        return "No Address Found";
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (reqCode == REQ_ADD_BRANCH) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        Type category = new TypeToken<Branch>() {
                        }.getType();
                        Branch branch = gson.fromJson(jsonObject.getJSONObject("data").toString(), category);
                        Institute institute = UtilsInstitute.getInstituteId(Data.getInstituteList(), institute_id);
                        if (institute.getBranches() == null) {
                            List list = new ArrayList();
                            list.add(branch);
                            institute.setBranches(list);
                        } else {
                            institute.getBranches().add(branch);
                        }

                        Intent intent = new Intent(mActivity, AddClasses.class);
                        branch.setInstituteName(institute_name);
                        branch.setInstituteId(institute_id);
                        intent.putExtra("from", "institute");
                        intent.putExtra("class", "addClass");
                        intent.putExtra("obj", branch);
                        startActivity(intent);

                    } else {
                        DialogError.setMessage(mActivity, jsonObject.getString("message"));

                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
