package com.example.TRS_VIEW;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App2 extends Application {

    public static final String CHRONOMETER_CHANNEL_ID = "Chronometer Service Channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createChronometerNotificationChannel();
    }

    private void createChronometerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.chronometer_channel_name);
            String description = getString(R.string.chronometer_channel_description);
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(CHRONOMETER_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
