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
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.activities.TeacherDashboard;
import com.appstar.tutionportal.teacher.activities.ViewPagerImage;
import com.appstar.tutionportal.teacher.fragments.EditProfile;
import com.appstar.tutionportal.teacher.fragments.FragmentEditClass;
import com.appstar.tutionportal.teacher.fragments.FragmentEditProfileMore;
import com.appstar.tutionportal.teacher.fragments.FragmentTeacherHome;
import com.appstar.tutionportal.teacher.fragments.FragmentViewImage;
import com.appstar.tutionportal.teacher.fragments.ProfileTeacherFragment;

import java.util.List;
import java.util.Locale;

public class Utils {
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

    public static void log(String key, String value) {
        if (isShowLog) {
            Log.e(key, value);
        }
    }

    public static void setEnableDisbaleView(final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        },1500);
    }

    public static void setEnableDisbaleView(final View view, long duration) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        },duration);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Institute getInstituteById(List<Institute> list, String id) {
        Institute institute = null;
        for (Institute obj : list) {
            if (obj.getInstituteId().equals(id)) {
                institute = obj;
                break;
            }
        }

        return institute;

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

        fragmentTransaction = TeacherDashboard.fragmentManager.beginTransaction();

        if (back)
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        else
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);

        try {
            switch (fragmentId) {

                case FragmentNames._TEACHER_HOME:
                    FragmentTeacherHome fragmentTeacherHome = new FragmentTeacherHome();
                    fragmentTransaction.replace(R.id.content, fragmentTeacherHome, fragmentName);
                    break;

                case FragmentNames._VIEW_IMAGES:
                    ViewPagerImage viewPagerImage = new ViewPagerImage();
                    viewPagerImage.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, viewPagerImage, fragmentName);
                    break;

                case FragmentNames._ADD_EDUCATION:

                    break;

               /* case FragmentNames._ADD_CLASS:
                    AddClasses addClasses = new AddClasses();
                    addClasses.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, addClasses, fragmentName);
                    break;*/


                case FragmentNames._EDIT_CLASS:

                    FragmentEditClass fragmentEditClass = new FragmentEditClass();
                    fragmentEditClass.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, fragmentEditClass, fragmentName);
                    break;

                case FragmentNames._EDIT_TEACHER_PROFILE:
                    EditProfile editProfile1 = new EditProfile();
                    fragmentTransaction.replace(R.id.content, editProfile1, fragmentName);
                    break;

                case FragmentNames._VIEW_FULL_IMAGE:
                    FragmentViewImage fragmentViewImage = new FragmentViewImage();
                    fragmentViewImage.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content, fragmentViewImage, fragmentName);
                    break;

                case FragmentNames._PROFILE_TEACHER:
                    ProfileTeacherFragment profileTeacherFragment = new ProfileTeacherFragment();
                    fragmentTransaction.replace(R.id.content, profileTeacherFragment, fragmentName);

                    break;

                case FragmentNames._EDIT_PROFILE_MORE:
                    FragmentEditProfileMore fragmentEditProfileMore = new FragmentEditProfileMore();
                    fragmentTransaction.replace(R.id.content, fragmentEditProfileMore, fragmentName);

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
