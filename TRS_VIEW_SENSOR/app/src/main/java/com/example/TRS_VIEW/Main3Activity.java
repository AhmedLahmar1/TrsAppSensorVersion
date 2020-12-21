package com.example.TRS_VIEW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main3Activity extends AppCompatActivity {
    Button dema;
    private FirebaseAnalytics mFirebaseAnalytics;
    SharedPreferences sharedPref2;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.date);
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

                dema = findViewById(R.id.demarrer);
        dema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(i);

            }

        });
    }
}
