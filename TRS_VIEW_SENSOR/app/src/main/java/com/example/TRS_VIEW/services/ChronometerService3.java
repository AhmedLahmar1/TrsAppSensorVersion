package com.example.TRS_VIEW.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.chronometer.ChronometerEngine3;

public class ChronometerService3 extends Service {

    private static final String TAG3 = ChronometerService3.class.getSimpleName();
    private final IBinder mBinder3 = new LocalBinder();
    private ChronometerEngine3 mChronometerEngine3;

    /**
     *
     */
    public class LocalBinder extends Binder {
        public ChronometerService3 getService() {
            return ChronometerService3.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder3;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometerEngine3 = new ChronometerEngine3();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG3, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChronometerEngine3 = null;
        Log.d(TAG3, "onDestroy: ");
    }

    /* Client methods */
    public void playPauseChronometer() {
        mChronometerEngine3.tooglePlayPause();
    }

    public void addLap() {
        mChronometerEngine3.addLap();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resetChronometer() {
        mChronometerEngine3.reset();
        stopForeground(STOP_FOREGROUND_REMOVE);
    }

    public String getFullTime() {
        return mChronometerEngine3.getFullTime();
    }

    public String getMillisecondsTime() {
        return mChronometerEngine3.getMillisecondsTime();
    }

    public boolean isRunning() {
        return mChronometerEngine3.isRunning();
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateNotification() {
        if (mChronometerEngine.isStarted()) {
            startForeground(ChronometerNotificationBuilder.NOTIFICATION_ID, ChronometerNotificationBuilder.build(this, mChronometerEngine.getFullTime()));
        }
    }*/

}
