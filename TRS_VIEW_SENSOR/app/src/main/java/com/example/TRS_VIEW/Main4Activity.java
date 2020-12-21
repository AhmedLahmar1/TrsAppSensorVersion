package com.example.TRS_VIEW;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class Main4Activity extends AppCompatActivity {

    private Button idd;
    private Button conf;
    private Button demar;
    private Button annuler1;
    private TextView txxt;
    private TextView tyyt;
    private TextView test1;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private TextView txt6;
    private TextView test2;
    private TextView fNameUser;
    private TextView lNameUser;
    private  TextView badgeUser;
    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    SharedPreferences sharedPrefChrono;
    private SharedPreferences.Editor editor;
    public static boolean flagChrono=false;
    public static boolean flagChrono2=false;
    public static boolean flagChrono3=false;

    public static boolean startArret=true;
    public static boolean startArret2=true;

    public static int numberPiece=0;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView profilePic;
    private SharedPreferences produitValuesPref;
    private SharedPreferences.Editor editorProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        produitValuesPref = getApplicationContext().getSharedPreferences("prefProd", 0);
        editorProd = produitValuesPref.edit();

        init();
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference  profileRef=storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
            }
        });


        DocumentReference documentReference=fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fNameUser.setText(value.getString("fName"));
                editorProd.putString("FirstName", value.getString("fName"));
                editorProd.commit();
                lNameUser.setText(value.getString("lName"));
                editorProd.putString("LastName", value.getString("lName"));
                editorProd.commit();
                badgeUser.setText(value.getString("bNumber"));


            }
        });



        sharedPref=getSharedPreferences("MyPref", MODE_PRIVATE);
        sharedPref2=getSharedPreferences("MyPref2", MODE_PRIVATE);
        sharedPrefChrono=getSharedPreferences("MyPrefChrono", MODE_PRIVATE);
        editor = sharedPref2.edit();

        test1.setText(sharedPref.getString("son","Empty"));
        String splitter=sharedPref2.getString("son2","Empty");
        String[] listSp= splitter.split("°");
        String ch="";

       if(listSp.length>2) {
           txt1.setText(listSp[0]);
           editorProd.putString("produit", listSp[0]);

           txt2.setText(listSp[1]);
           editorProd.putString("of", listSp[1]);

           txt3.setText(listSp[2]);
           editorProd.putString("matiere", listSp[2]);

           txt4.setText(listSp[3]);
           editorProd.putString("qntite", listSp[3]);

           txt6.setText(listSp[5]);
           editorProd.putString("cadence", listSp[5]);

           txt5.setText(listSp[4]);
           editorProd.putString("qtefab", listSp[4]);
           editorProd.commit();

       }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
          //  txxt.setText(bundle.getString("son"));
            //tyyt.setText(bundle.getString("person2"));

        }

    }

    private void init() {
        fNameUser=findViewById(R.id.fNameUser);
        lNameUser=findViewById(R.id.lNameUser);
        badgeUser=findViewById(R.id.badgeUser);
        annuler1=findViewById(R.id.annuler1);
        txxt = findViewById(R.id.txt_iden);
        //tyyt = findViewById(R.id.txt_confi);
        test1 = findViewById(R.id.test1);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6= findViewById(R.id.txt6);
        profilePic=findViewById(R.id.profilePic);


        test2 = findViewById(R.id.txt5);
        idd = findViewById(R.id.iden);
        conf = findViewById(R.id.confi);
        demar = findViewById(R.id.demarrer1);
        ecouteur();

    }



    private void ecouteur() {



        idd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main4Activity.this, liste_oper.class);
                startActivity(i);
            }
        });


        conf.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(Main4Activity.this, confiramation.class);
                startActivity(i);

            }
        });


        demar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i = new Intent(Main4Activity.this, Main2Activity.class);
                flagChrono=true;
                flagChrono2=true;
                flagChrono3=true;

                //Main2Activity.var=3;

                editor.putBoolean("start", true);
                editor.commit();
                startActivity(i);


            }
        });
        annuler1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("son2","");
                editor.commit();
                Intent i = new Intent(Main4Activity.this, Main3Activity.class);
                startActivity(i);
            }
        });

    }

    public void changePic(View view){
        Intent openGallary= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallary,1000);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable Intent data){
        super.onActivityResult(resultCode,resultCode,data);
        if (requestCode==1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri=data.getData();
                //profilePic.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef=storageReference.child("users/" +fAuth.getCurrentUser().getUid()+ "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(Main4Activity.this,"Image uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePic);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main4Activity.this,"Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}













