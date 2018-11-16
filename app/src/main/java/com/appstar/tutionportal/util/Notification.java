package com.appstar.tutionportal.util;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.ChatWindow;

public class Notification {
    Activity activity;
    public static final int NOTIFICATION_INT = 23456;

    public Notification(Activity activity) {
        this.activity = activity;
    }

    public void sendNotification(String messageBody, String userName) {
      Intent intent = new Intent(activity, ChatWindow.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = activity.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(activity, channelId)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(userName)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_INT /* ID of notification */, notificationBuilder.build());
    }
}
