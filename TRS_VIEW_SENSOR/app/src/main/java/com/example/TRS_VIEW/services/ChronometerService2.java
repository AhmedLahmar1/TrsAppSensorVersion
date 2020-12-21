package com.example.TRS_VIEW.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.chronometer.ChronometerEngine2;

public class ChronometerService2 extends Service {

    private static final String TAG2 = ChronometerService2.class.getSimpleName();
    private final IBinder mBinder2 = new LocalBinder();
    private ChronometerEngine2 mChronometerEngine2;

    /**
     *
     */
    public class LocalBinder extends Binder {
        public ChronometerService2 getService() {
            return ChronometerService2.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder2;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometerEngine2 = new ChronometerEngine2();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG2, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChronometerEngine2 = null;
        Log.d(TAG2, "onDestroy: ");
    }

    /* Client methods */
    public void playPauseChronometer() {
        mChronometerEngine2.tooglePlayPause();
    }

    public void addLap() {
        mChronometerEngine2.addLap();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resetChronometer() {
        mChronometerEngine2.reset();
        stopForeground(STOP_FOREGROUND_REMOVE);
    }

    public String getFullTime() {
        return mChronometerEngine2.getFullTime();
    }

    public String getMillisecondsTime() {
        return mChronometerEngine2.getMillisecondsTime();
    }

    public boolean isRunning() {
        return mChronometerEngine2.isRunning();
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateNotification() {
        if (mChronometerEngine.isStarted()) {
            startForeground(ChronometerNotificationBuilder.NOTIFICATION_ID, ChronometerNotificationBuilder.build(this, mChronometerEngine.getFullTime()));
        }
    }*/

}
