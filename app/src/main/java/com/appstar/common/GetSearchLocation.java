package com.appstar.common;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Dialog.DialogError;
import com.appstar.tutionportal.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class GetSearchLocation extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    public final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    int REQUEST_SELECT_PLACE = 1234;
    double latitude, longitude;
    SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;
    TextView tvSearch;
    ImageView imageView, imgEdit;
    EditText etAddress, etLocation, etLandMark;
    LinearLayout llCurrentLocation;
    ProgressBar progress;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Geocoder geocoder;
    int REQUEST_CHECK_SETTINGS = 187;
    boolean isCurrentLocationSelected;
    CardView cvAddLocation;
    String localAddress;
    private int KEY_BACK_DATA = 678;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.get_search_location);
                initView();
                getData();
                onClick();
            }
        }, 1500);
        getLocation();
    }

    private void initView() {
        geocoder = new Geocoder(this, Locale.getDefault());
        cvAddLocation = findViewById(R.id.cvAddLocation);
        progress = findViewById(R.id.progress);
        imgEdit = findViewById(R.id.imgEdit);
        llCurrentLocation = findViewById(R.id.llCurrentLocation);
        imageView = findViewById(R.id.imageView);
        tvSearch = findViewById(R.id.tvSearch);
        etAddress = findViewById(R.id.etAddress);
        etLocation = findViewById(R.id.etLocation);
        etLandMark = findViewById(R.id.etLandMark);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(GetSearchLocation.this);
    }

    private void getData() {
        KEY_BACK_DATA = getIntent().getIntExtra("key", KEY_BACK_DATA);
    }

    private void onClick() {
        llCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCurrentLocationSelected = true;
                progress.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(GetSearchLocation.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted

                        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            displayLocationSettingsRequest(getApplicationContext());
                        } else {
                            buildGoogleApiClient();
                            mGoogleMap.setMyLocationEnabled(true);
                        }

                    } else {
                        //Request Location Permission
                        checkLocationPermission();
                    }
                } else {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        displayLocationSettingsRequest(getApplicationContext());
                    }
                }
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });


        cvAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude <= 0) {
                    DialogError.setMessage(GetSearchLocation.this, "You have not added any location");
                } else {
                    com.appstar.common.model.Address address = new com.appstar.common.model.Address();
                    address.setFullAddress(tvSearch.getText().toString().trim());
                    address.setCity(etLocation.getText().toString().trim());
                    address.setAddress(etAddress.getText().toString().trim());
                    address.setLandmark(etLandMark.getText().toString().trim());
                    address.setLatitude(latitude);
                    address.setLongitude(longitude);
                    address.setLocalAddress(localAddress);
                    Intent intent = new Intent();
                    intent.putExtra("obj", address);
                    setResult(KEY_BACK_DATA, intent);
                    finish();
                }
            }
        });
    }

    private void getLocation() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder
                    (PlaceAutocomplete.MODE_FULLSCREEN)
                    .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                    .build(GetSearchLocation.this);
            startActivityForResult(intent, REQUEST_SELECT_PLACE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(GetSearchLocation.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
            mGoogleApiClient.connect();
        } else if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(10000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //    Log.i(TAG, "All location settings are satisfied.");
                        buildGoogleApiClient();
                        mGoogleMap.setMyLocationEnabled(true);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(GetSearchLocation.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }

    @Override
    public void onPlaceSelected(Place place) {
        Toast.makeText(getApplicationContext(), place.getAddress(), Toast.LENGTH_SHORT).show();
        latitude = place.getLatLng().latitude;
        longitude = place.getLatLng().longitude;
        isCurrentLocationSelected = true;
        setLocation();
        // tvSearch.setText(place.getAddress().toString());
    }

    private void setLocation() {
        setOnMap();
        setData();
    }

    @Override
    public void onError(Status status) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1);
        mLocationRequest.setFastestInterval(1);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        longitude = location.getLongitude();
        latitude = location.getLatitude();
        mLastLocation = location;
        setOnMap();
    }


    private void setOnMap() {
        if (isCurrentLocationSelected) {

            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            //Place current location marker
            //  Toast.makeText(getApplicationContext(),"Goted location",Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Your Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            setData();
            isCurrentLocationSelected = false;
        }
    }

    private void setData() {

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);
                }
            }, 1500);

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            tvSearch.setTypeface(Typeface.DEFAULT);
            if (!TextUtils.isEmpty(addresses.get(0).getSubLocality())) {
                localAddress = addresses.get(0).getSubLocality();
                localAddress += ", " + addresses.get(0).getLocality();
            } else {
                localAddress = addresses.get(0).getAddressLine(0);
                if (localAddress.contains(",")) {
                    int index = localAddress.indexOf(",");
                    if (index > -0) {
                        localAddress = localAddress.substring(index + 1, localAddress.length());
                    }
                }
            }
            tvSearch.setText(addresses.get(0).getAddressLine(0));
            etLocation.setText(city);
            etAddress.setText("");
            etLandMark.setText("");


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        buildGoogleApiClient();
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

