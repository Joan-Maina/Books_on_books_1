package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.TextUtilsCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public  static final String TAG = "TAG";
    EditText fname, lname, number, email, password;
    Button register;
    TextView log;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
log=findViewById(R.id.textViewlog);
        progressDialog = new ProgressDialog(this);
        register = findViewById(R.id.button3);
        fname = findViewById(R.id.editTextTextPersonName1);
        lname = findViewById(R.id.editTextTextPersonName2);
        number = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextNumber);
        password= findViewById(R.id.editTextTextPersonName);
        //findViewById(R.id.button3).setOnClickListener( this);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        /*
register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        RegisterUser();
    }
});*/
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(register.this, signin.class);
                startActivity(i);
            }
        });
    }



    private boolean ValidInputs(String firstname,String lastname, String contact, String personage,String personlocation){

        if (firstname.isEmpty()){
            fname.setError("Field required");
            fname.requestFocus();
            return true;
        }
        if (lastname.isEmpty()){
            lname.setError("Field required");
            lname.requestFocus();
            return true;
        }
        if (contact.isEmpty()){
            number.setError("Field required");
            number.requestFocus();
            return true;
        }
        if (personage.isEmpty()){
            email.setError("Field required");
            email.requestFocus();
            return true;
        }
        if (personlocation.isEmpty()){
            password.setError("Field required");
            password.requestFocus();
            return true;
        }
        return  false;
    }





    public void RegisterUser(View view) {

        String firstname = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        String phonenumber = number.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pswd1 = password.getText().toString().trim();

        if (firstname.isEmpty()) {
            fname.setError("Field required");
            fname.requestFocus();
            return;
        }
        if (lastname.isEmpty()) {
            lname.setError("Field required");
            lname.requestFocus();
            return;
        }
        if (phonenumber.isEmpty()) {
            number.setError("Field required");
            number.requestFocus();
            return;
        }
        if (mail.isEmpty()) {
            email.setError("Field required");
            email.requestFocus();
            return;
        }
        if (pswd1.isEmpty()) {
            password.setError("Field required");
            password.requestFocus();
            return;
        }


        // progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(mail, pswd1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(register.this, "user created!", Toast.LENGTH_SHORT).show();

                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userID = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("Users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("firstname", firstname);
                                user.put("lastname", lastname);
                                user.put("phonenumber", phonenumber);
                                user.put("mail", mail);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure:" + e.toString());
                                    }
                                });
                                Toast.makeText(register.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(register.this, signin.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(register.this, "Not verfified!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    Toast.makeText(register.this, "TRY AGAIN", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });




       /* if (!ValidInputs(firstname,lastname,mail,pswd1,phonenumber)){

            CollectionReference dbUsers = db.collection("users");
       User user = new User(firstname, lastname,pswd1,Integer.parseInt(phonenumber), mail);

       dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
           @Override
           public void onSuccess(DocumentReference documentReference) {

               Toast.makeText(Register.this,"useradded",Toast.LENGTH_SHORT).show();
               Intent i = new Intent(Register.this,MainActivity.class);
               startActivity(i);
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
           }
       });*/
    }
    private Boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }




}