package com.example.TRS_VIEW.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.chronometer.ChronometerEngine;
import com.example.TRS_VIEW.notifications.ChronometerNotificationBuilder;

public class ChronometerService extends Service {

    private static final String TAG = ChronometerService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    private ChronometerEngine mChronometerEngine;

    /**
     *
     */
    public class LocalBinder extends Binder {
        public ChronometerService getService() {
            return ChronometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometerEngine = new ChronometerEngine();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChronometerEngine = null;
        Log.d(TAG, "onDestroy: ");
    }

    /* Client methods */
    public void playPauseChronometer() {
        mChronometerEngine.tooglePlayPause();
    }

    public void addLap() {
        mChronometerEngine.addLap();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resetChronometer() {
        mChronometerEngine.reset();
        stopForeground(STOP_FOREGROUND_REMOVE);
    }

    public String getFullTime() {
        return mChronometerEngine.getFullTime();
    }

    public String getMillisecondsTime() {
        return mChronometerEngine.getMillisecondsTime();
    }

    public boolean isRunning() {
        return mChronometerEngine.isRunning();
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateNotification() {
        if (mChronometerEngine.isStarted()) {
            startForeground(ChronometerNotificationBuilder.NOTIFICATION_ID, ChronometerNotificationBuilder.build(this, mChronometerEngine.getFullTime()));
        }
    }*/

}
