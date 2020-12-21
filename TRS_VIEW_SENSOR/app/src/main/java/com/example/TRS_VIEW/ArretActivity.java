package com.example.TRS_VIEW;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.example.TRS_VIEW.services.ChronometerService;
import com.example.TRS_VIEW.services.ChronometerService2;
import com.example.TRS_VIEW.services.ChronometerService3;
import com.example.TRS_VIEW.services.ChronometerServicePause;
import com.example.TRS_VIEW.services.ChronometerServiceMaintenance;
import com.example.TRS_VIEW.services.ChronometerServiceManque;
import com.example.TRS_VIEW.services.ChronometerServiceReglage;

import java.text.DecimalFormat;

public class ArretActivity extends AppCompatActivity {
    private Button main_prev;
    private Chronometer c1, c2, c3, c4, c5, c6, c7, c8, c9;

    private static final String TAG3 = ArretActivity.class.getSimpleName();
    private static final String TAG4 = ArretActivity.class.getSimpleName();

    private ChronometerService3 mChronometerService3;
    private ChronometerServicePause mChronometerServicePause;
    private ChronometerServiceManque mChronometerServiceManque;
    private ChronometerServiceMaintenance mChronometerServiceMaintenance;
    private ChronometerServiceReglage mChronometerServiceReglage;

    private boolean mBound3 = false;
    private boolean mBound4 = false;
    private boolean mBoundManque = false;
    private boolean mBoundMaintenance = false;
    private boolean mBoundReglage = false;

    Context context;
    private AlphaAnimation buttonClickAnimation = new AlphaAnimation(1F, 0.8F);
    public static String flagArret;
    public static String valChrono3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG3, "onCreate: ");
        setContentView(R.layout.activity_arret);
        final TextView timeEditText = findViewById(R.id.txt_millis);
        if(Main2Activity.var==2){
            timeEditText.setVisibility(View.INVISIBLE);

        }

        Thread t5 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              TextView numPieces=findViewById(R.id.chron_qte_prod);
                                numPieces.setText(Main2Activity.numPiece);

                            }
                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        };
        t5.start();

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG3, "onStart: ");

        Intent intent3 = new Intent(this, ChronometerService3.class);
        Intent intent4 = new Intent(this, ChronometerServicePause.class);
        Intent intentManque = new Intent(this, ChronometerServiceManque.class);
        Intent intentMaintenance = new Intent(this, ChronometerServiceMaintenance.class);
        Intent intentReglage = new Intent(this, ChronometerServiceReglage.class);

        startService(intent3);
        startService(intent4);
        startService(intentMaintenance);
        startService(intentManque);
        startService(intentReglage);

        bindService(intent3, mServiceConnection3, Context.BIND_AUTO_CREATE);
        bindService(intent4, mServiceConnectionPause, Context.BIND_AUTO_CREATE);
        bindService(intentReglage, mServiceConnectionReglage, Context.BIND_AUTO_CREATE);
        bindService(intentMaintenance, mServiceConnectionMaintenance, Context.BIND_AUTO_CREATE);
        bindService(intentManque, mServiceConnectionManque, Context.BIND_AUTO_CREATE);


    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG3, "onPause: ");
        Log.d(TAG4, "onPause: ");

        //showNotification();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG3, "onStop: ");
       // showNotification();
        unbindService(mServiceConnection3);
        unbindService(mServiceConnectionPause);
        unbindService(mServiceConnectionReglage);
        unbindService(mServiceConnectionMaintenance);
        unbindService(mServiceConnectionManque);

        mBound3 = false;
        mBound4 = false;
        mBoundMaintenance = false;
        mBoundManque = false;
        mBoundReglage = false;



    }

    public void onClickStart() {
        if (mBound3 ) {
            mChronometerService3.playPauseChronometer();
            updateIU3();
            toogleStartStopIcon();

        }

    }


    public void fermerActivityArret(View view) {
        Intent i = new Intent(ArretActivity.this, Main2Activity.class);
        startActivity(i);
    }

    private void toogleStartStopIcon() {
        if (mBound3) {
            final Button startStopButton = findViewById(R.id.arret_inde);

            if (mChronometerService3.isRunning()) {
                startStopButton.setBackgroundColor(Color.RED);
                Main2Activity.var=1;

            } else {
                startStopButton.setBackgroundColor(Color.WHITE);

            }
        }
    }

    public void onClickSplit(View view) {
        if (mBound3) {
            mChronometerService3.addLap();
            view.startAnimation(buttonClickAnimation);
        }
    }

    public void onClickReset(View view) {
        if (mBound3) {
           // mChronometerService.resetChronometer();
           // view.startAnimation(buttonClickAnimation);
        }
    }

    public void startChronoPanne(View view){
        if (mBound4 ) {
            mChronometerServicePause.playPauseChronometer();
            updateIU4();
            toogleStartStopIcon();
            sendMail();
            Main2Activity.var=2;
            final TextView timeEditText = findViewById(R.id.txt_millis);
            timeEditText.setVisibility(View.INVISIBLE);
            if(mChronometerServiceManque.isRunning()){
                mChronometerServiceManque.playPauseChronometer();
            }

            if(mChronometerServiceReglage.isRunning()){
                mChronometerServiceReglage.playPauseChronometer();
            }

            if(mChronometerServiceMaintenance.isRunning()){
                mChronometerServiceMaintenance.playPauseChronometer();
            }
        }

    }

    public void startChronoManque(View view){
        if (mBoundManque ) {
            mChronometerServiceManque.playPauseChronometer();
            updateIUManque();
            toogleStartStopIcon();
            Main2Activity.var=4;

            final TextView timeEditText = findViewById(R.id.txt_millis);
            timeEditText.setVisibility(View.INVISIBLE);
            if(mChronometerServicePause.isRunning()){
                mChronometerServicePause.playPauseChronometer();
            }

            if(mChronometerServiceReglage.isRunning()){
                mChronometerServiceReglage.playPauseChronometer();
            }

            if(mChronometerServiceMaintenance.isRunning()){
                mChronometerServiceMaintenance.playPauseChronometer();
            }
        }

    }

    public void updateIUManque() {
        final TextView timeEditTextManque = findViewById(R.id.arret_indeter9);
        final Handler handler4 = new Handler();
        handler4.post(new Runnable() {
            @Override
            public void run() {
                String value;
                value=mChronometerServiceManque.getFullTime()+":"+ mChronometerServiceManque.getMillisecondsTime();
                timeEditTextManque.setText(value);
                handler4.postDelayed(this, 0);

            }
        });
    }




    public void startChronoReglage(View view){
        if (mBoundReglage ) {
            mChronometerServiceReglage.playPauseChronometer();
            updateIUReglage();
            toogleStartStopIcon();
            Main2Activity.var=3;
            sendMail2();
            final TextView timeEditText = findViewById(R.id.txt_millis);
            timeEditText.setVisibility(View.INVISIBLE);
            if(mChronometerServicePause.isRunning()){
                mChronometerServicePause.playPauseChronometer();
            }

            if(mChronometerServiceManque.isRunning()){
                mChronometerServiceManque.playPauseChronometer();
            }

            if(mChronometerServiceMaintenance.isRunning()){
                mChronometerServiceMaintenance.playPauseChronometer();
            }
        }

    }

    public void updateIUReglage() {
        final TextView timeEditTextReglage = findViewById(R.id.arret_indeter7);

        final Handler handler5 = new Handler();
        handler5.post(new Runnable() {
            @Override
            public void run() {
                String value;
                value=mChronometerServiceReglage.getFullTime()+":"+ mChronometerServiceReglage.getMillisecondsTime();
                timeEditTextReglage.setText(value);
                handler5.postDelayed(this, 0);
            }
        });
    }

    public void startChronoMaintenance(View view){
        if (mBoundMaintenance ) {
            mChronometerServiceMaintenance.playPauseChronometer();
            updateIUMaintenance();
            toogleStartStopIcon();
            Main2Activity.var=4;
            sendMail3();
            final TextView timeEditText = findViewById(R.id.txt_millis);
            timeEditText.setVisibility(View.INVISIBLE);
            if(mChronometerServicePause.isRunning()){
                mChronometerServicePause.playPauseChronometer();

            }

            if(mChronometerServiceReglage.isRunning()){
                mChronometerServiceReglage.playPauseChronometer();
            }

            if(mChronometerServiceManque.isRunning()){
                mChronometerServiceManque.playPauseChronometer();
            }
        }

    }

    public void updateIUMaintenance() {
        final TextView timeEditTextMain = findViewById(R.id.arret_indeter8);

        final Handler handler6 = new Handler();
        handler6.post(new Runnable() {
            @Override
            public void run() {
                String value;
                value=mChronometerServiceMaintenance.getFullTime()+":"+ mChronometerServiceMaintenance.getMillisecondsTime();
                timeEditTextMain.setText(value);
                handler6.postDelayed(this, 0);
            }
        });
    }

    public void updateIU3() {
        final TextView timeEditText = findViewById(R.id.txt_millis);

        final Handler handler3 = new Handler();
        handler3.post(new Runnable() {
            @Override
            public void run() {
                String value;
                value=mChronometerService3.getFullTime()+":"+ mChronometerService3.getMillisecondsTime();
                timeEditText.setText(value);
                valChrono3=value;
                handler3.postDelayed(this, 0);
            }
        });
    }
    public void updateIU4() {
        final TextView EditTextPause = findViewById(R.id.arret_Pause);
        final TextView timeEditText = findViewById(R.id.txt_millis);

        final Handler handler4 = new Handler();
        handler4.post(new Runnable() {
            @Override
            public void run() {
                String value2;
                value2=mChronometerServicePause.getFullTime()+":"+ mChronometerServicePause.getMillisecondsTime();
                EditTextPause.setText(value2);
                handler4.postDelayed(this, 0);
            }
        });
    }


    public void startChronometer2(View view){
        Chronometer chronometer2=findViewById(R.id.arret_indeter2);
        chronometer2.start();
    }
    public void startChronometer3(View view){
        Chronometer chronometer3=findViewById(R.id.arret_indeter4);
        chronometer3.start();

    }
    public void startChronometer4(View view){
        Chronometer chronometer4=findViewById(R.id.arret_indeter5);
        chronometer4.start();

    }

    public void startChronometer5(View view){
        Chronometer chronometer5=findViewById(R.id.arret_indeter3);
        chronometer5.start();

    }
    public void Arret(View view){

    }

    public void production(View view){
        Chronometer chronometer3=findViewById(R.id.arret_indeter4);
        chronometer3.stop();
        Chronometer chronometer4=findViewById(R.id.arret_indeter5);
        chronometer4.stop();
        Chronometer chronometer5=findViewById(R.id.arret_indeter3);
        chronometer5.stop();
        onClickStart();
        Button button=findViewById(R.id.restProd);
        button.setBackgroundColor(Color.GREEN);
        Main2Activity.var=5;
        //mChronometerService3.isRunning()
        if(mChronometerServicePause.isRunning()){
            mChronometerServicePause.playPauseChronometer();
        }

        if(mChronometerServiceManque.isRunning()){
            mChronometerServiceManque.playPauseChronometer();
        }

        if(mChronometerServiceReglage.isRunning()){
            mChronometerServiceReglage.playPauseChronometer();
        }

        if(mChronometerServiceMaintenance.isRunning()){
            mChronometerServiceMaintenance.playPauseChronometer();
        }

    }


   /* private void showNotification() {
        if (mBound) {
            mChronometerService.updateNotification();
        }
    }*/

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mServiceConnection3 = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerService3.LocalBinder binder3 = (ChronometerService3.LocalBinder) service;
            mChronometerService3 = binder3.getService();
            mBound3 = true;
            mChronometerService3.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            if (Main4Activity.startArret2){
                onClickStart();
                Main4Activity.startArret2=false;
            }
            updateIU3();
            toogleStartStopIcon();
            Log.d(TAG3, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound3 = false;
            Log.d(TAG3, "onServiceDisconnected: ");
        }
    };

    private ServiceConnection mServiceConnectionPause = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerServicePause.LocalBinder binder4 = (ChronometerServicePause.LocalBinder) service;
            mChronometerServicePause = binder4.getService();
            mBound4 = true;
            mChronometerServicePause.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            updateIU4();
            toogleStartStopIcon();
            Log.d(TAG4, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound4 = false;
            Log.d(TAG4, "onServiceDisconnected: ");
        }
    };

    private ServiceConnection mServiceConnectionMaintenance = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerServiceMaintenance.LocalBinder binder4 = (ChronometerServiceMaintenance.LocalBinder) service;
            mChronometerServiceMaintenance = binder4.getService();
            mBoundMaintenance = true;
            mChronometerServiceMaintenance.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            updateIUMaintenance();
            toogleStartStopIcon();
            Log.d(TAG4, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundMaintenance = false;
            Log.d(TAG4, "onServiceDisconnected: ");
        }
    };

    private ServiceConnection mServiceConnectionManque = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerServiceManque.LocalBinder binder4 = (ChronometerServiceManque.LocalBinder) service;
            mChronometerServiceManque = binder4.getService();
            mBoundManque = true;
            mChronometerServiceManque.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            updateIUManque();
            toogleStartStopIcon();
            Log.d(TAG4, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundManque = false;
            Log.d(TAG4, "onServiceDisconnected: ");
        }
    };

    private ServiceConnection mServiceConnectionReglage = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerServiceReglage.LocalBinder binder4 = (ChronometerServiceReglage.LocalBinder) service;
            mChronometerServiceReglage = binder4.getService();
            mBoundReglage = true;
            mChronometerServiceReglage.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            updateIUReglage();
            Log.d(TAG4, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundReglage = false;
            Log.d(TAG4, "onServiceDisconnected: ");
        }
    };



    private void sendMail() {
       String mail="bileltrigui.c2i@gmail.com";
       String message= " *****ALERTE DE MAINTENANCE*****" +"\n" +
               "Cher chef de maintenance"+"\n" +
               "La machine X est en panne " +"\n"+
               "Le service de production vous invite pour une maintenance."+"\n"+
               "Cordialement.";
       String subjet="Service de production SIMED ";
       JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subjet,message);
       javaMailAPI.execute();

    }
    private void sendMail2() {
        String mail = "bileltrigui.c2i@gmail.com";
        String message = " *****ALERTE DE MAINTENANCE*****" + "\n" +
                "Cher chef de maintenance" + "\n" +
                "La machine X est en panne " + "\n" +
                "Le service de production vous invite pour une maintenance." + "\n" +
                "Cordialement.";
        String subjet = "Service de production SIMED ";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mail, subjet, message);
        javaMailAPI.execute();
    }

        private void sendMail3() {
            String mail="bileltrigui.c2i@gmail.com";
            String message= " *****ALERTE DE MAINTENANCE*****" +"\n" +
                    "Cher chef de maintenance"+"\n" +
                    "La machine X est en panne " +"\n"+
                    "Le service de production vous invite pour une maintenance."+"\n"+
                    "Cordialement.";
            String subjet="Service de production SIMED ";
            JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subjet,message);
            javaMailAPI.execute();

    }
}