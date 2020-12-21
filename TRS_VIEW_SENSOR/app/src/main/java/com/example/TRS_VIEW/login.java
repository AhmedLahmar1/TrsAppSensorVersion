package com.example.TRS_VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class login extends AppCompatActivity {


    private EditText emailId, passwd;
    private EditText firstName,lastName,badgeNumber;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);


        btnSignUp = findViewById(R.id.btnSignUp);
        signIn = findViewById(R.id.TVSignIn);
        firstName=findViewById(R.id.fName);
        lastName=findViewById(R.id.lName);
        badgeNumber=findViewById(R.id.numBadge);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(login.this, "Connecté", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(login.this, Main3Activity.class);
                    startActivity(I);
                    finish();
                } else {
                    //Toast.makeText(login.this, "Connectez-vous pour continuer", Toast.LENGTH_SHORT).show();
                }
            }
        };


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = emailId.getText().toString();
                String paswd = passwd.getText().toString();
                if (emailID.isEmpty()) {
                    emailId.setError("Entrer votre email");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Entrer votre mot de passe");
                    passwd.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(login.this, "Champs vides", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (!task.isSuccessful()) {
                                Toast.makeText(login.this.getApplicationContext(),
                                        "Echec de connexion" + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(login.this, Main3Activity.class));
                                userID=firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fStore.collection("users").document(userID);
                                Map<String,Object> userM=new HashMap<>();
                                userM.put("fName",firstName.getText().toString());
                                userM.put("lName",lastName.getText().toString());
                                userM.put("bNumber",badgeNumber.getText().toString());
                                userM.put("email",emailId.getText().toString());
                               /* fStore.collection("users")
                                        .add(userM)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("tag", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                Toast.makeText(login.this, "added", Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("tag", "Error adding document", e);
                                                Toast.makeText(login.this, "Connectez-vous pour continuer", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(login.this, "Error adding document", Toast.LENGTH_SHORT).show();

                                            }
                                        });*/
                               documentReference.set(userM).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Log.d("tag","accepted");
                                   }
                               });
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(login.this, "Erreur", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(login.this, login2Activity.class);
                startActivity(I);
                finish();
            }
        });
    }



}
