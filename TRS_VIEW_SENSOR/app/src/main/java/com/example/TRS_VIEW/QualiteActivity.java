package com.example.TRS_VIEW;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QualiteActivity extends AppCompatActivity {
 TextView show1,show2,show3,sum;
 int counter1 = 0;
 int counter2 = 0 ;
 int counter3 = 0 ;
 public static int pieceMauvaise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualite);
        show1 = findViewById(R.id.show1);
        show2 = findViewById(R.id.show2);
        show3 = findViewById(R.id.show3);
        sum = findViewById(R.id.sum);

    }
    public  void count1 (View view){
        counter1 ++;
        show1.setText(Integer.toString(counter1));
    }
    public  void count2 (View view){
        counter2 ++;
        show2.setText(Integer.toString(counter2));
    }
    public  void count3 (View view){
        counter3 ++;
        show3.setText(Integer.toString(counter3));
    }
    public  void somme (View view){
        pieceMauvaise=counter1+counter2+counter3;
        sum.setText(Integer.toString(pieceMauvaise));

    }
}
