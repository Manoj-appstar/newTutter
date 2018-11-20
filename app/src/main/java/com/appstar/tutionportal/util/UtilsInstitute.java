package com.appstar.tutionportal.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.appstar.tutionportal.Model.Branch;
import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.institute.activities.InstituteDashboard;
import com.appstar.tutionportal.institute.fragments.FragmentInstituteHome;
import com.appstar.tutionportal.student.extras.FragmentNames;

import java.util.List;
import java.util.Locale;

public class UtilsInstitute {

    private static boolean isShowLog = true;
    private static int currentScreen = 0;
    private static int currentTab = 1;
    private static int selectedCat = 0;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction = null;

    public static int getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(int screen) {
        currentScreen = screen;
    }

    public static int getCurrentTab() {
        return currentTab;
    }

    public static void setCurrentTab(int tab) {
        currentTab = tab;
    }

    public static int getSelectedClass() {
        return selectedCat;
    }

    public static void setSelectedClass(int cat) {
        selectedCat = cat;
    }


    public static void checkInternetConnection(final Activity context) {
        if (!isNetworkAvailable(context)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder
                    .setMessage("No internet connection on your device. Would you like to enable it?")
                    .setTitle("No Internet Connection")
                    .setCancelable(false)
                    .setPositiveButton(" Enable Internet ",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    Intent in = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                    context.startActivity(in);
                                }
                            });

            alertDialogBuilder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }

    public static Institute getInstituteId(List<Institute> list, String id) {
        for (Institute obj : list) {
            if (obj.getInstituteId().equals(id)) {
                return obj;
            }
        }
        return null;
    }

    public static Branch getBranchId(List<Branch> list, String id) {
        for (Branch obj : list) {
            if (obj.getBranchId().equals(id)) {
                return obj;
            }
        }
        return null;
    }

    public static void log(String key, String value) {
        if (isShowLog) {
            Log.e(key, value);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showMessage(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void hideKeypad(Context context, View view) {
        // Check if no view has focus:
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Log.e("Utilis", "My Current: " + addresses.toString());
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                if (returnedAddress.getMaxAddressLineIndex() > 0) {

                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                    }
                } else {
                    strReturnedAddress.append(returnedAddress.getAddressLine(0)).append("");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Utilis My Current", "" + strReturnedAddress.toString());
            } else {
                Log.w("Utilis My Current", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Utilis My Current", "Canont get Address!");
        }
        return strAdd;
    }

    public void openFragment(Activity context, String fragmentName, int fragmentId, Bundle bundle, boolean back) {

        fragmentTransaction = InstituteDashboard.fragmentManager.beginTransaction();

        if (back)
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        else
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

        try {
            switch (fragmentId) {

                case FragmentNames._INSTITUTE_HOME:
                    FragmentInstituteHome fragmentInstituteHome = new FragmentInstituteHome();
                    fragmentTransaction.replace(R.id.content, fragmentInstituteHome, fragmentName);
                    break;
            }

            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.e("FRAG EXC", "::::    " + e.getMessage());
        }

    }

    public void openActivity(Activity context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }
}
