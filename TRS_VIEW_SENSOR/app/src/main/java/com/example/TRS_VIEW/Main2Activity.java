package com.example.TRS_VIEW;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.TRS_VIEW.services.ChronometerService;
import com.example.TRS_VIEW.services.ChronometerService2;
import com.example.TRS_VIEW.services.ChronometerServiceRequis;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Handler;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {
    long pauseOffset;
    boolean running;
    TextView QQ;
    DatabaseReference reff;
    TextView chrono1;
    TextView chrono22;
    TextView operateur;
    TextView produit;
    TextView matiereT;
    TextView of;
    TextView qntite;
    TextView cadence;
    TextView testR;
    TextView edit_qntite2;
    public static long elapsedMillis;
    TextView edt;
    Button qte;
    Button arret;
    Button oper;
    Button matiere;
    Button donne;
    Button graph;
    Button fin_travail;
    private SharedPreferences produitValuesPref;
    private SharedPreferences.Editor editorProd;
    TextView text;
    //***************************************************
    private static final String TAG = Main2Activity.class.getSimpleName();
    private static final String TAG2 = Main2Activity.class.getSimpleName();
    public static String valChrono1;
    public static String valChrono2;
    public static String valChrono3;
    public static int var;
    private FirebaseAnalytics mFirebaseAnalytics;



    private ChronometerService mChronometerService;
    private ChronometerService2 mChronometerService2;
    private ChronometerServiceRequis mChronometerServiceRequis;


    private boolean mBound = false;
    private boolean mBound2 = false;
    private boolean mBound3 = false;

    private boolean condition2 = false;
    public static String numPiece;

    private AlphaAnimation buttonClickAnimation = new AlphaAnimation(1F, 0.8F);

    //*****************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG2, "onCreate: ");
        setContentView(R.layout.activity_main2);
        ecouteur();
        currentDate();
        currentDate1();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        produitValuesPref=getSharedPreferences("prefProd",MODE_PRIVATE);
        operateur=findViewById(R.id.edit_oper);
        operateur.setText(produitValuesPref.getString("FirstName","Empty")+" "+produitValuesPref.getString("LastName",""));

        produit=findViewById(R.id.edit_prod);
        produit.setText(produitValuesPref.getString("produit","Empty"));

        matiereT=findViewById(R.id.edit_matiere);
        matiereT.setText(produitValuesPref.getString("matiere","Empty"));

        of=findViewById(R.id.edit_of);
        of.setText(produitValuesPref.getString("of","Empty"));

        qntite=findViewById(R.id.edit_qntite);
        qntite.setText(produitValuesPref.getString("qntite","Empty"));

        cadence=findViewById(R.id.edit_cadence);
        cadence.setText(produitValuesPref.getString("qtefab","Empty"));

        edit_qntite2=findViewById(R.id.edit_qntite2);
        edit_qntite2.setText(produitValuesPref.getString("cadence","Empty"));


        text=findViewById(R.id.textView23);
        QQ = (TextView) findViewById(R.id.chron_qte_prod);
        qte = findViewById(R.id.qte);
        donne = findViewById(R.id.MM);
        arret = findViewById(R.id.arret);
        oper = findViewById(R.id.oper);
        graph = findViewById(R.id.graph);
        fin_travail = findViewById(R.id.fin_travail);
        edt = findViewById(R.id.edit_oper);

        switch (var) {
            case 1:
                text.setText("ARRET");
                text.setBackgroundColor(Color.YELLOW);
                break;
            case 2:
                text.setText("PANNE");
                text.setBackgroundColor(Color.RED);
                break;
            case 3:
                text.setText("REGLAGE");
                text.setBackgroundColor(Color.BLUE);
                break;
            case 4:
                text.setText("MAINTEMANCE");
                text.setBackgroundColor(Color.MAGENTA);
                break;

            default:
                text.setText("PRODUCTION");
                text.setBackgroundColor(Color.GREEN);
                break;
        }



        qte.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, QualiteActivity.class);
                startActivity(i);
            }
        });

        donne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, confiramation.class);
                startActivity(i);

            }
        });

        arret.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                condition2=true;
                if(Main4Activity.startArret){
                    onClickStart1();
                    Main4Activity.startArret=false;

                }

                Intent i = new Intent(Main2Activity.this, ArretActivity.class);
                startActivity(i);
            }
        });

        oper.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, liste_oper.class);
                startActivity(i);

            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                InfoActivity.close=false;
                Intent i = new Intent(Main2Activity.this, InfoActivity.class);
                startActivity(i);


            }
        });

        fin_travail.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, FinActivity.class);
                startActivity(i);
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, liste_oper.class);
                startActivity(i);
            }
        });
    }





    @Override
    protected void onStart() {

        super.onStart();
        Log.d(TAG2, "onStart: ");
        Intent intent = new Intent(this, ChronometerService.class);
        Intent intent2 = new Intent(this, ChronometerService2.class);
        Intent intent3 = new Intent(this, ChronometerServiceRequis.class);

        startService(intent);
        startService(intent2);
        startService(intent3);



        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        bindService(intent2, mServiceConnection2, Context.BIND_AUTO_CREATE);
        bindService(intent3, mServiceConnection3, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG2, "onPause: ");
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG2, "onStop: ");
        Log.d(TAG, "onStop: ");

        unbindService(mServiceConnection);
        unbindService(mServiceConnection2);
        unbindService(mServiceConnection3);

        mBound2 = false;
        mBound = false;
        mBound3 = false;

    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.d(TAG2, "onStop: ");
        Log.d(TAG, "onStop: ");


    }

    public void onClickStart1() {
        if ((mBound & (Main4Activity.flagChrono==true || condition2==true)) || Main2Activity.var==5 ) {
            Main4Activity.flagChrono=false;
            mChronometerService.playPauseChronometer();
            updateIU();
        }
    }

    public void onClickStart2(){
        if (mBound2 & (Main4Activity.flagChrono2==true)) {
            Main4Activity.flagChrono2=false;
            mChronometerService2.playPauseChronometer();
            updateIU2();
        }
    }

    public void onClickStart3(){
        if ((mBound3 & (Main4Activity.flagChrono3==true))|| Main2Activity.var==2 || Main2Activity.var==3 || Main2Activity.var==4)  {
            Main4Activity.flagChrono3=false;
            mChronometerServiceRequis.playPauseChronometer();
            updateIU3();
        }
    }


        public void onClickSplit (View view){
            if (mBound) {
                mChronometerService.addLap();
                view.startAnimation(buttonClickAnimation);
            }
            if (mBound2) {
                mChronometerService2.addLap();
                view.startAnimation(buttonClickAnimation);
            }
        }

        public void onClickReset (View view){
            if (mBound) {
                view.startAnimation(buttonClickAnimation);
            }
            if (mBound2) {
                view.startAnimation(buttonClickAnimation);
            }
        }

    public void updateIU() {
        chrono1=findViewById(R.id.chrono1);
       // final TextView numberPiece=findViewById(R.id.chron_qte_prod);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String value;
                value=mChronometerService.getFullTime()+":"+ mChronometerService.getMillisecondsTime();
                chrono1.setText(value);
                valChrono1=value;

                String[] parts3 = mChronometerService.getFullTime().split(":");
                if(parts3.length==1){
                    int part2 = Integer.parseInt(parts3[0]);//sec
                   // numberPiece.setText(Integer.toString(part2));
                  //  numPiece=Integer.toString(part2);

                }else {
                    int part2 = Integer.parseInt(parts3[1]);//sec
                    int part3= Integer.parseInt(parts3[0]); //min
                    //numberPiece.setText(Integer.toString(part2+ 60*part3));
                   // numPiece=Integer.toString(part2+ 60*part3);

                }


                handler.postDelayed(this, 0);

            }
        });
    }
        public void updateIU2 () {

            chrono22=findViewById(R.id.chrono22);
            final Handler handler2 = new Handler();
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    String value2;
                    value2=mChronometerService2.getFullTime() + ":" + mChronometerService2.getMillisecondsTime();
                    chrono22.setText(value2);
                    valChrono2=value2;
                    handler2.postDelayed(this, 0);
                }
            });
        }
    public void updateIU3 () {
        //testR=findViewById(R.id.test);
        final Handler handler2 = new Handler();
        handler2.post(new Runnable() {
            @Override
            public void run() {
                String value2;
                value2=mChronometerServiceRequis.getFullTime() + ":" + mChronometerServiceRequis.getMillisecondsTime();
                //testR.setText(value2);
                valChrono3=value2;
                handler2.postDelayed(this, 0);
            }
        });
    }

        // Defines callbacks for service binding, passed to bindService()

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerService.LocalBinder binder = (ChronometerService.LocalBinder) service;
            mChronometerService =  binder.getService();
            mBound = true;
            onClickStart1();
            mChronometerService.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            updateIU();
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

        private final ServiceConnection mServiceConnection2 = new ServiceConnection() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                ChronometerService2.LocalBinder binder = (ChronometerService2.LocalBinder) service;
                mChronometerService2 = binder.getService();
                mBound2= true;
                onClickStart2();
                mChronometerService2.stopForeground(Service.STOP_FOREGROUND_REMOVE);
                updateIU2();
                Log.d(TAG2, "onServiceConnected: ");
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                mBound2 = false;
                Log.d(TAG2, "onService2Disconnected: ");
            }
        };


    private final ServiceConnection mServiceConnection3 = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ChronometerServiceRequis.LocalBinder binder = (ChronometerServiceRequis.LocalBinder) service;
            mChronometerServiceRequis = binder.getService();
            mBound3= true;
            onClickStart3();
            mChronometerServiceRequis.stopForeground(Service.STOP_FOREGROUND_REMOVE);
            updateIU3();
            Log.d(TAG2, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound3 = false;
            Log.d(TAG2, "onService2Disconnected: ");
        }
    };




    private void ecouteur() {

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final TextView numberPiece = findViewById(R.id.chron_qte_prod);
                                        reff = FirebaseDatabase.getInstance().getReference().child("Test Val");

                                        reff.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String piece_numero = dataSnapshot.child("piece numero").getValue().toString();
                                                numberPiece.setText(piece_numero);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                            }
                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        };
        t.start();
    }

    private void currentDate() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.edit_heure);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat(" dd MMM yyyy  hh:mm:ss ");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        };
        t.start();

    }
    private void currentDate1() {

                                TextView tdate = (TextView) findViewById(R.id.edit_travail);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat(" dd MMM yyyy  hh:mm:ss ");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);

    }

    }


