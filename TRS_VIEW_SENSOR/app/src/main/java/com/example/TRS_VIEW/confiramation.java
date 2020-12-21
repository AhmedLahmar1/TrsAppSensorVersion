package com.example.TRS_VIEW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class confiramation extends AppCompatActivity {
    private static final String TAG = "confiramation";
    Button okey;
    ListView oflist;
    TextView tyyt ;
    private SharedPreferences sharedPref2;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref2 = getApplicationContext().getSharedPreferences("MyPref2", 0);
        editor = sharedPref2.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confiramation);
        //tyyt = findViewById(R.id.txt_confi);
        Log.d(TAG, "onCreate: Started.");
        oflist = findViewById(R.id.liste_confi);
        person2 aaaa = new person2("BRONCHOKODAdultes","Sirop","CARBOCISTEINE","Flacon 300 ML","2100","8000");
        person2 cccc = new person2("BRONCHOKOD Adultes","Sirop","CARBOCISTEINE","Flacon 125 ML","2100","8000");
        person2 eeee = new person2("BRONCHOKOD Enfants","Sirop","CARBOCISTEINE","Flacon 125 ML","2100","8000");
        person2 gggg = new person2("BRONCHOKOD SANS SUCRE ADULTES","Sirop","CARBOCISTEINE","Flacon 300 ML","2100","8000");
        person2 iiii = new person2("DRILL SANS SUCRE ADULTES","Sirop","DEXTROMETHORPHANE BROMHYDRATE","Flacon 150 ML","2100","8000");
        person2 kkkk = new person2("PECTO 6 Adultes","Sirop","CODEINE + CODETHYLINE + SULFOGAICOL","Flacon 180 ML","2100","8000");
        person2 mmmm = new person2("PECTO 6 Enfants","Sirop","CODEINE + SULFOGAICOL","Flacon 150 ML","2100","8000");
        person2 pppp = new person2("PECTO 6 Nourrissons","Sirop","BROMURE DE SODIUM + BENZOATE DE SODIUM + SULFOGAICOL","Flacon 125 ML","2100","8000");
        person2 uuuu = new person2("KETOTIFENE","Sirop","KETOTIFENE","Flacon 150 ML","2100","8000");
        person2 tttt = new person2("SALBUTAMOL SIMED","Sirop","SALBUTAMOL","Flacon 150 ML","2100","8000");
        person2 ssss = new person2("RANITIDINE SIMED","Sirop","RANITIDINE","Flacon 150 ML","2100","8000");
        person2 wwww = new person2("PIZOTIFENE","Sirop","PIZOTIFENE","Flacon 150 ML","2100","8000");
        //ajouter l'objet person dans ArrayListe
        ArrayList<person2> peopleList2 = new ArrayList<>();
        peopleList2.add(aaaa);
        peopleList2.add(cccc);
        peopleList2.add(eeee);
        peopleList2.add(gggg);
        peopleList2.add(iiii);
        peopleList2.add(kkkk);
        peopleList2.add(mmmm);
        peopleList2.add(pppp);
        peopleList2.add(uuuu);
        peopleList2.add(tttt);
        peopleList2.add(ssss);
        peopleList2.add(wwww);
        final person2ListAdapter adapter1 = new person2ListAdapter(this, R.layout.customer, peopleList2);

        oflist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(confiramation.this,Main4Activity.class);
                //intent.putExtra("person2",oflist.getItemAtPosition(i).toString());
                editor.putString("son2", oflist.getItemAtPosition(i).toString());
                editor.commit();
                startActivity(intent);

            }
        });

        oflist.setAdapter(adapter1);

        okey = findViewById(R.id.ok_confi);
        okey.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(confiramation.this, Main4Activity.class);
                startActivity(i);
            }   });
    }

    public void retour(View view){
        Intent i = new Intent(confiramation.this, Main4Activity.class);
        startActivity(i);
    }

}
