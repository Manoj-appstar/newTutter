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

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.activity.StudentDashboard;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.fragment.FragmentEditProfile;
import com.appstar.tutionportal.student.fragment.FragmentListingSupport;
import com.appstar.tutionportal.student.fragment.FragmentQuestion;
import com.appstar.tutionportal.student.fragment.FragmentSettings;
import com.appstar.tutionportal.student.fragment.FragmentStudentHome;
import com.appstar.tutionportal.student.fragment.FragmentSupport;
import com.appstar.tutionportal.student.fragment.FragmentTeacherReview;
import com.appstar.tutionportal.student.fragment.FragmentViewTeacherInfo;
import com.appstar.tutionportal.student.fragment.TeacherProfile;
import com.appstar.tutionportal.teacher.activities.ViewPagerImage;

import java.util.List;
import java.util.Locale;

public class UtilsStudent {

    private static boolean isShowLog = true;
    private static int currentScreen = 0;
    private static int currentTab = 2;
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

        fragmentTransaction = StudentDashboard.fragmentManager.beginTransaction();

        if (back)
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        else
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

        try {
            switch (fragmentId) {


                case FragmentNames._STUDENT_HOME:
                    FragmentStudentHome fragmentStudentHome = new FragmentStudentHome();
                    fragmentTransaction.replace(R.id.content, fragmentStudentHome, fragmentName);

                    break;

                case FragmentNames._STUDENT_CLASS_INFO:
                    FragmentViewTeacherInfo fragmentViewTeacherInfo = new FragmentViewTeacherInfo();
                    fragmentTransaction.replace(R.id.content, fragmentViewTeacherInfo, fragmentName);

                    break;

                case FragmentNames._STUDENT_TEACHER_PROFILE:
                    TeacherProfile teacherProfile = new TeacherProfile();
                    fragmentTransaction.replace(R.id.content, teacherProfile, fragmentName);

                    break;

                case FragmentNames._STUDENT_TEACHER_REVIEW:
                    FragmentTeacherReview fragmentTeacherReview = new FragmentTeacherReview();
                    fragmentTransaction.replace(R.id.content, fragmentTeacherReview, fragmentName);

                    break;

                case FragmentNames._STUDENT_EDIT:
                    FragmentEditProfile fragmentEditProfile = new FragmentEditProfile();
                    fragmentTransaction.replace(R.id.content, fragmentEditProfile, fragmentName);

                    break;

                case FragmentNames._STUDENT_SETTING:
                    FragmentSettings fragmentSettings = new FragmentSettings();
                    fragmentTransaction.replace(R.id.content, fragmentSettings, fragmentName);

                    break;

                case FragmentNames._STUDENT_SUPPORT:
                    FragmentSupport fragmentSupport = new FragmentSupport();
                    fragmentTransaction.replace(R.id.content, fragmentSupport, fragmentName);

                    break;

                case FragmentNames._STUDENT_QUESTION:
                    FragmentQuestion fragmentQuestion = new FragmentQuestion();
                    fragmentQuestion.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, fragmentQuestion, fragmentName);

                    break;

                case FragmentNames._STUDENT_LISTING_SUPPORT:
                    FragmentListingSupport fragmentListingSupport = new FragmentListingSupport();
                    fragmentListingSupport.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, fragmentListingSupport, fragmentName);
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
