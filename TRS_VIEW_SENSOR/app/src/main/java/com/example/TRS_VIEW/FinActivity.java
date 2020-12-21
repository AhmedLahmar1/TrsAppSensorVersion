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

public class FinActivity extends AppCompatActivity {
 private
     Button bb;
     Button aa;
    private FirebaseAnalytics mFirebaseAnalytics;
    private SharedPreferences.Editor editor;
    SharedPreferences sharedPref2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);
        // Obtain the FirebaseAnalytics instance.
        sharedPref2=getSharedPreferences("MyPref2", MODE_PRIVATE);
        editor = sharedPref2.edit();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        TextView dispo=findViewById(R.id.dtxt);
        TextView cality=findViewById(R.id.qtxt);
        TextView perf=findViewById(R.id.ptxt);
        TextView trs=findViewById(R.id.ttxt);

        dispo.setText(InfoActivity.DISPONIBILITYF);
        cality.setText(InfoActivity.QUALITYF);
        perf.setText(InfoActivity.PERFORMANCEF);
        trs.setText(InfoActivity.TRSF);


        Bundle bundle = new Bundle();
        String[] parts4 = InfoActivity.DISPONIBILITYF.split("%");
        double d1=Double.parseDouble(parts4[0]);
        Log.i("dispo",d1+"");

        bundle.putDouble("Disponibilité", d1);

        String[] parts3 = InfoActivity.QUALITYF.split("%");
        double d=Double.parseDouble(parts3[0]);
        Log.i("quality",d+"");
        bundle.putDouble("Qualité", d);


        String[] parts2 = InfoActivity.PERFORMANCEF.split("%");
        double d2=Double.parseDouble(parts2[0]);
        Log.i("PERFORMANCEF",d2+"");

         bundle.putDouble("Performance", d2);


        String[] parts5 = InfoActivity.TRSF.split("%");
        double d5=Double.parseDouble(parts5[0]);
        Log.i("TRSF",d5+"");

         bundle.putDouble("TRS", d5);

        mFirebaseAnalytics.logEvent("TRSF", bundle);



        bb=findViewById(R.id.btnx);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(FinActivity.this, Main2Activity.class);
                startActivity(i);
            }   });
        aa=findViewById(R.id.bu);
        aa.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                editor.putString("son2","");
                editor.commit();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }});

        }

}
