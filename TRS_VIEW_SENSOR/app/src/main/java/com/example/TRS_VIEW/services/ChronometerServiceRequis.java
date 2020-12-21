package com.example.TRS_VIEW.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.chronometer.ChronometerEngineRequis;

public class ChronometerServiceRequis extends Service {

    private static final String TAG3 = ChronometerServiceRequis.class.getSimpleName();
    private final IBinder mBinder3 = new LocalBinder();
    private ChronometerEngineRequis mChronometerEngineRequis;

    /**
     *
     */
    public class LocalBinder extends Binder {
        public ChronometerServiceRequis getService() {
            return ChronometerServiceRequis.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder3;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometerEngineRequis = new ChronometerEngineRequis();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG3, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChronometerEngineRequis = null;
        Log.d(TAG3, "onDestroy: ");
    }

    /* Client methods */
    public void playPauseChronometer() {
        mChronometerEngineRequis.tooglePlayPause();
    }

    public void addLap() {
        mChronometerEngineRequis.addLap();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resetChronometer() {
        mChronometerEngineRequis.reset();
        stopForeground(STOP_FOREGROUND_REMOVE);
    }

    public String getFullTime() {
        return mChronometerEngineRequis.getFullTime();
    }

    public String getMillisecondsTime() {
        return mChronometerEngineRequis.getMillisecondsTime();
    }

    public boolean isRunning() {
        return mChronometerEngineRequis.isRunning();
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateNotification() {
        if (mChronometerEngine.isStarted()) {
            startForeground(ChronometerNotificationBuilder.NOTIFICATION_ID, ChronometerNotificationBuilder.build(this, mChronometerEngine.getFullTime()));
        }
    }*/

}
