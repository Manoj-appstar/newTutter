package com.appstar.tutionportal.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.volley.toolbox.Volley;
import com.appstar.tutionportal.student.extras.NetworkConnection;
import com.appstar.tutionportal.student.interfaces.NetworkResponse;

public class NetworkChangeReceiver extends BroadcastReceiver {
    NetworkResponse networkListener;
    Activity context;

    public NetworkChangeReceiver(Activity context, NetworkResponse listener) {
        this.networkListener = listener;
        this.context = context;
    }
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (NetworkConnection.isNetworkAvailable(context)) {
            networkListener.onConnectionChange(true);
        } else {
            networkListener.onConnectionChange(false);
        }
    }

}