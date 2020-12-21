package com.example.TRS_VIEW.chronometer;

import android.os.Handler;
import android.os.SystemClock;

import java.util.Locale;

public class ChronometerEngine {

    /**
     * Chronometer variables
     */
    private long mStartTime, mTimeMinusStart, mTimeWhenPaused, mCurrentMilliseconds;
    private int mHours, mMinutes, mSeconds, mMilliseconds;
    private String mFullTimeString = "0", mMillisString = "00";

    /**
     * Indicates if chronometer is running, variable is false when it is paused.
     */
    private boolean mIsRunning = false;

    /**
     * Indicates when chronometer has been started, no matter if it's running or paused.
     */
    private boolean mIsStarted = false;

    final Handler mHandler = new Handler();

    /**
     *
     */
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTimeMinusStart = SystemClock.uptimeMillis() - mStartTime;
            mCurrentMilliseconds = mTimeWhenPaused + mTimeMinusStart;

            mMilliseconds = (int) ((mCurrentMilliseconds % 1000) / 10);
            mSeconds = (int) (mCurrentMilliseconds / 1000);
            mMinutes = mSeconds / 60;
            mHours = mMinutes / 60;
            mSeconds = mSeconds % 60;

            String hoursString = mHours > 0 ? String.format(Locale.US, "%d:", mHours) : "";
            String minutesString = mMinutes > 0 ? String.format(Locale.US, "%d:", mMinutes) : "";
            String secondsString = String.format(Locale.US, "%d", mSeconds);

            mFullTimeString = hoursString + minutesString + secondsString;
            mMillisString = String.format(Locale.US, "%02d", mMilliseconds);

            mHandler.postDelayed(this, 0);
        }
    };

    public void tooglePlayPause() {
        if (mIsRunning) {
            mTimeWhenPaused += mTimeMinusStart;
            mHandler.removeCallbacks(mRunnable);
        } else {
            mIsStarted = true;
            mStartTime = SystemClock.uptimeMillis();
            mHandler.postDelayed(mRunnable, 0);
        }

        mIsRunning = !mIsRunning;
    }

    public void reset() {
        mStartTime = 0;
        mTimeWhenPaused = 0;
        mTimeMinusStart = 0;
        mCurrentMilliseconds = 0;
        mHours = 0;
        mMinutes = 0;
        mSeconds = 0;
        mMilliseconds = 0;

        mFullTimeString = "0";
        mMillisString = "00";

        mIsRunning = false;
        mIsStarted = false;

        mHandler.removeCallbacks(mRunnable);
    }

    public void addLap() {

    }

    public String getFullTime() {
        return mFullTimeString;
    }

    public String getMillisecondsTime() {
        return mMillisString;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public boolean isStarted() {
        return mIsStarted;
    }

}
