package com.appstar.tutionportal.fcm;

import android.util.Log;

import com.appstar.tutionportal.util.SharePreferenceData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by asus on 12-04-2018.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharePreferenceData sharePreferenceData = new SharePreferenceData(getApplicationContext());
     //   sharePreferenceData.setFCMToken(context,refreshedToken);
    }
}
