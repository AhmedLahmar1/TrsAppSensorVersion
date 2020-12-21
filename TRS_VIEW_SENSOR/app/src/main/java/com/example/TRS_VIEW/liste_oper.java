package com.example.TRS_VIEW;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class liste_oper extends AppCompatActivity {

   public static final String TAG = "liste_oper";
  ListView list ;
  Button okey1;
  TextView txxt;
  private SharedPreferences sharedPref;
  private SharedPreferences.Editor editor;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_oper);
        txxt=findViewById(R.id.txt_iden);
        sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = sharedPref.edit();
       Log.d(TAG, "onCreate: Started.");
        list = findViewById(R.id.liste_oper);
       person Mohamed = new person("Mohamed", "TRIGUI", "12345");
        person Ali = new person("Ali", "BEN SALAH", "7412");
        person Jalel = new person("Jalel", "FRIKHA", "78967");
        person Samir = new person("Samir", "NABLI", "95478");
        person Raouf = new person("Raouf", "BRADAI", "72593");
        person Lotfi = new person("Lotfi", "MAZZOUN", "78523");
        person Bilel = new person("Bilel", "TRIGUI", "34975");
        person Kamel = new person("Kamel", "BOUJELBEN", "54823");
        person Amel = new person(" Amel", "JALOULI", "97543");
        person Salma = new person("Salma", "BOUALI", "14289");
        person Lobna = new person("Lobna", "SAHNOUN", "22368");
        person Mariem = new person("Mariem", "BELGHITH", "33400");
        //ajouter l'objet person dans ArrayListe
        final ArrayList<person> peopleList = new ArrayList<>();
        peopleList.add(Mohamed);
        peopleList.add(Ali);
        peopleList.add(Jalel);
        peopleList.add(Samir);
        peopleList.add(Raouf);
        peopleList.add(Lotfi);
        peopleList.add(Bilel);
        peopleList.add(Kamel);
        peopleList.add(Amel);
        peopleList.add(Salma);
        peopleList.add(Lobna);
        peopleList.add(Mariem);
        final personListAdapter adapter = new personListAdapter(this, R.layout.client, peopleList);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(liste_oper.this,Main4Activity.class);
                //intent.putExtra("person",list.getItemAtPosition(i).toString());
                editor.putString("son", list.getItemAtPosition(i).toString());
                editor.commit();
                startActivity(intent);

            }
           });
        list.setAdapter(adapter);


        okey1 = findViewById(R.id.ok_oper);
        okey1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(liste_oper.this, Main4Activity.class);
                startActivity(i);

            }
        });

    }
}
