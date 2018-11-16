package com.appstar.tutionportal.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Model.ChatUser;
import com.appstar.tutionportal.Model.ChatUserStudent;
import com.appstar.tutionportal.Model.ClassDetail;
import com.appstar.tutionportal.Model.DirectorDetail;
import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.Model.MessageListener;
import com.appstar.tutionportal.Model.StudentDetail;
import com.appstar.tutionportal.Model.TeacherDetail;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Data {
    //http://hidi.org.in/hidi/account/update_status.php


    public static final String URL = "http://hidi.org.in/hidi/";
    //public static final String URL = "http://192.168.1.17:8081/hidi/";
    public static final String PLACE_API_KEY = "AIzaSyAlLkXcIWrsPHAfbpnS2_9ViMlLlF5UFSE";
    public static final String BASE_URL = Data.URL + "post/";
    public static final String SHOW_POST = BASE_URL + "showposts.php";
    public static final String SHOW_SINGLE_POST = BASE_URL + "showsinglepost.php";
    public static final String ENABLE_HIDE_COMMENT = BASE_URL + "enable_comment.php";
    public static final String GET_SAVED_POST = BASE_URL + "get_save_post.php";
    public static final String BASE_URL_ACCOUNT = Data.URL + "account/";
    public static final String UPDATE_LIKE = BASE_URL + "update.php";
    public static final String SAVE_POST = BASE_URL + "update.php";
    public static final String GET_NAME_LIST = BASE_URL_ACCOUNT + "secname.php";
    public static final String UPDATE_NAME = BASE_URL_ACCOUNT + "secname.php";
    public static final String UPDATE_POST_SAVE = BASE_URL + "savepost.php";
    public static final String REPORT = BASE_URL + "add_report.php";
    public static final String SETTINGS = BASE_URL + "setting.php";
    public static final String LOGOUT = BASE_URL + "Auth/logout.php";
    public static final String MY_POST = BASE_URL + "mypost.php";
    public static final String GET_USER_MESSAGE = URL + "message/getallone2onemsg.php";
    public static final String SEND_USER_MESSAGE = URL + "message/one2onemsg.php";
    public static final String GET_CHAT_USER_DETAIL = URL + "message/Check_usersexist.php";
    public static final String SET_CHAT_USER_DETAIL_NAME = URL + "message/firsttime_chatting.php";
    public static final String GET_ALL_LAST_MSG = URL + "message/getlastmessage.php";
    public static final String ENABLE_REAL_NAME = URL + "message/Enable_realname.php";
    public static final String JOIN_GROUP = URL + "group/Create_member.php";
    public static final String CREATE_GROUP = URL + "group/create_group.php";
    public static final String UPLOAD_GROUP_PIC = URL + "group/upload_icon.php";
    public static final String GET_LAST_GROUP_MSG = URL + "group/getgrouplastmessage.php";
    public static final String SEND_GROUP_MSG = URL + "group/group_message.php";
    public static final String GET_GROUP_VIRTUAL_NAME = URL + "group/getgroupvirtualname.php";
    public static final String GET_ALL_GROUP_MSG = URL + "group/getallgroup_msg.php";
    public static final String GET_ALL_GROUP_DETAIL = URL + "group/Admin_groups.php";
    public static final String ADD_TAGS = URL + "tags/addtags.php";
    public static final String GET_GROUP_DETAIL = URL + "group/group_details.php";
    public static final String REMOVE_USER_FROM_GROUP = URL + "group/Remove_id_group.php";
    public static final String UPDATE_GROUP_NAME = URL + "group/Update_groupname.php";
    public static final String DELETE_GROUP = URL + "group/Remove_group.php";
    public static final String CHECK_SESSION = URL + "Auth/checksession.php";
    public static final String UPDATE_STATUS = URL + "account/update_status.php";
    public static final String UPDATE_NOTIFICATION = URL + "account/notification_type.php";
    public static final String GET_ALL_NOTIFICATIONS = URL + "notification/shownotification.php";
    public static final String GET_REPORT = BASE_URL + "reports.php";


    public static final String MESSAGE_ENABLE = URL + "account/message_enable.php";
    //http://hidi.org.in/hidi/post/reports.php
    //http://hidi.org.in/hidi/group/getgrouplastmessage.php
    public static boolean isNightModeEnabled;
    public static boolean isThemeUpdated;
    public static int userId;
    public static int GET_POST_NAME = 12343;
    public static int GET_SELECTED_PATH = 12344;
    public static int GET_LOCATION = 12345;
    public static int MAX_POST_LIMIT = 20;
    //  public static OnGPSLocationOFF onGPSLocationOFF;
    public static boolean isNeedToRefreshPost;
    public static boolean needBack;
    public static String RECEIVER_MSG = "push_receive_msg";
    public static String RECEIVER_LOCATION = "receive_location";
    public static String ADD_NEW_USER = "added_new_user";
    public static String RECEIVER_SELECTED_NAME = "push_selected_name";
    public static DirectorDetail directorDetail;
    private static String chatUserId;
    private static MessageListener listener;
    private static TextView tvComments;
    private static List<Institute> instituteList = new ArrayList<>();
    private static List<ClassDetail> classList = new ArrayList<>();
    private static ChatUser chatUser;
    private static ChatUserStudent chatUserStudent;
    private static TeacherDetail teacherDetail;
    private static StudentDetail studentDetail;

    public static List<ClassDetail> getClassList() {
        return classList;
    }

    public static void setClassList(List<ClassDetail> classList) {
        Data.classList = classList;
    }

    public static List<Institute> getInstituteList() {
        return instituteList;
    }

    public static void setInstituteList(List<Institute> instituteList) {
        Data.instituteList = instituteList;
    }

    public static MessageListener getMessageListener() {
        return listener;
    }

    public static void setMessageListener(MessageListener listener) {
        Data.listener = listener;
    }

    public static String getChatUser_id() {
        return chatUserId;
    }

    public static void setChatUser_id(String chat_user_id) {
        Data.chatUserId = chat_user_id;
    }

    public static DirectorDetail getDirectorDetail() {
        return directorDetail;
    }

    public static void setDirectorDetail(DirectorDetail directorDetail) {
        Data.directorDetail = directorDetail;
    }

    public static TeacherDetail getTeacherDetail() {
        return teacherDetail;
    }

    public static void setTeacherDetail(TeacherDetail teacherDetail) {
        Data.teacherDetail = teacherDetail;
    }

    public static StudentDetail getStudentDetail() {
        return studentDetail;
    }

    public static void setStudentDetail(StudentDetail studentDetail) {
        Data.studentDetail = studentDetail;
    }

    public static ChatUser getChatUser() {
        return chatUser;
    }

    public static void setChatUser(ChatUser chatUser) {
        Data.chatUser = chatUser;
    }

    public static ChatUserStudent getChatUserStudent() {
        return chatUserStudent;
    }

    public static void setChatUserStudent(ChatUserStudent chatUserStudent) {
        Data.chatUserStudent = chatUserStudent;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void cancelNotification(Context ctx) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(Notification.NOTIFICATION_INT);
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }


    public static void disabledView(final View view, long time) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, time);
    }

    public static void disabledView(final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);

            }
        }, 1000);
    }

    public static TextView getTvComments() {
        return tvComments;
    }

    public static void setTvComments(TextView tvComments) {
        Data.tvComments = tvComments;
    }


    public static void copyText(String text, Context context) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        try {
            byte[] decodedByte = Base64.decode(input, 0);
            return BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
        } catch (Exception ex) {
            return null;
        }
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
}

