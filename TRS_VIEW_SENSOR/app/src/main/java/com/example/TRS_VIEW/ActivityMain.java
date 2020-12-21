package com.example.TRS_VIEW;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.view.View;
import android.widget.Button;
//import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity {
    private Chronometer chronometer;
    private Chronometer chrono;
    private long pauseOffset;
    private boolean running;
    private
    Button info;
    Button qlte;
    Button ft;
    Button arret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase())>= 1000000000){
                    chronometer.setBase(SystemClock.elapsedRealtime());

                }
            }
        });
       chrono = findViewById(R.id.chron_fct);
       chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
           public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase())>= 1000000000){
                    //chronometer.setBase(SystemClock.elapsedRealtime());

                }
            }
        });
        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view)
            {
                Intent i = new Intent(ActivityMain.this,InfoActivity.class);
                startActivity(i);
                //Bundle extras= getExtra()
            }});
        qlte= findViewById(R.id.qlte);
        qlte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this, QualiteActivity.class);
                startActivity(i);
            }} );
        ft=findViewById(R.id.ft);
        ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this, FinActivity.class);
                startActivity(i);
            }} );
        arret=findViewById(R.id.arret);
        arret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMain.this, ArretActivity.class);
                startActivity(i);
            }} );
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            chrono.start();
            running = true;
        }
    }

                 public void pauseChronometer(View v) {
                     if (running) {
                         chrono.stop();
                         pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                         running = false;
                 }
               }
                 public void resetChronometer(View v) {
                         chronometer.setBase(SystemClock.elapsedRealtime());
                         chrono.setBase(SystemClock.elapsedRealtime());
                         pauseOffset = 0;
                 }


    }


