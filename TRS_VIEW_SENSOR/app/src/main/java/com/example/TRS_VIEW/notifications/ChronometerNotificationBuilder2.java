package com.example.TRS_VIEW.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.App;
import com.example.TRS_VIEW.App2;
import com.example.TRS_VIEW.ArretActivity;
import com.example.TRS_VIEW.R;

public class ChronometerNotificationBuilder2 {

    public static final int NOTIFICATION_ID = 12887951;
    private static final String ACTION_PLAY_PAUSE = "com.carlosarroyoam.android.chronometer.ACTION.PLAY_PAUSE";
    private static final String ACTION_RESET = "com.carlosarroyoam.android.chronometer.ACTION.RESET";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification build(Context context, String notificationBody) {
        Intent intent = new Intent(context, ArretActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Intent playOrPauseIntent = new Intent();
        playOrPauseIntent.setAction(ACTION_PLAY_PAUSE);
        PendingIntent playOrPausePendingIntent = PendingIntent.getBroadcast(context, 0, playOrPauseIntent, 0);

        Intent resetIntent = new Intent();
        resetIntent.setAction(ACTION_RESET);
        PendingIntent resetPendingIntent = PendingIntent.getBroadcast(context, 0, resetIntent, 0);

        return new Notification.Builder(context, App2.CHRONOMETER_CHANNEL_ID)
//				.setContentTitle(getText(R.string.chronometer_notification_title))
                .setContentText(notificationBody)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(context.getResources().getColor(R.color.colorPrimary, null))
                .setContentIntent(pendingIntent)
                .build();
    }
}


