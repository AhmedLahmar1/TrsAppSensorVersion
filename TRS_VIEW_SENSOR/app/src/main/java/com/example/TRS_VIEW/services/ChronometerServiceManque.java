package com.example.TRS_VIEW.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.TRS_VIEW.chronometer.ChronometerEngineManque;

public class ChronometerServiceManque extends Service {

    private static final String TAG3 = ChronometerServiceManque.class.getSimpleName();
    private final IBinder mBinder3 = new LocalBinder();
    private ChronometerEngineManque mChronometerEngine3;

    /**
     *
     */
    public class LocalBinder extends Binder {
        public ChronometerServiceManque getService() {
            return ChronometerServiceManque.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder3;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometerEngine3 = new ChronometerEngineManque();
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



}
