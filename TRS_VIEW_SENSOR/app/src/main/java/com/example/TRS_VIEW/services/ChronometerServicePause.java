package com.example.TRS_VIEW.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.chronometer.ChronometerEnginePause;

public class ChronometerServicePause extends Service {

    private static final String TAG4 = ChronometerServicePause.class.getSimpleName();
    private final IBinder mBinder4 = new LocalBinder();
    private ChronometerEnginePause mChronometerEnginePause;

    /**
     *
     */
    public class LocalBinder extends Binder {
        public ChronometerServicePause getService() {
            return ChronometerServicePause.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder4;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometerEnginePause = new ChronometerEnginePause();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG4, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mChronometerEnginePause = null;
        Log.d(TAG4, "onDestroy: ");
    }

    /* Client methods */
    public void playPauseChronometer() {
        mChronometerEnginePause.tooglePlayPause();
    }

    public void addLap() {
        mChronometerEnginePause.addLap();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resetChronometer() {
        mChronometerEnginePause.reset();
        stopForeground(STOP_FOREGROUND_REMOVE);
    }

    public String getFullTime() {
        return mChronometerEnginePause.getFullTime();
    }

    public String getMillisecondsTime() {
        return mChronometerEnginePause.getMillisecondsTime();
    }

    public boolean isRunning() {
        return mChronometerEnginePause.isRunning();
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateNotification() {
        if (mChronometerEngine.isStarted()) {
            startForeground(ChronometerNotificationBuilder.NOTIFICATION_ID, ChronometerNotificationBuilder.build(this, mChronometerEngine.getFullTime()));
        }
    }*/

}
