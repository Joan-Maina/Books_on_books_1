package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class registerAdmin extends AppCompatActivity {

    private EditText emailtv, passwordtv;
    private Button login;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        init();
        //reference = FirebaseDatabase.getInstance().getReference().child("Admin");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // loginValid(email,password);
                loginUser();


            }
        });
    }



    private void loginUser() {
        String email=  emailtv.getText().toString();
        String password = passwordtv.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(registerAdmin.this,"input email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(registerAdmin.this,"input passwdord",Toast.LENGTH_SHORT).show();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    pb.setVisibility(View.VISIBLE);
                    startActivity(new Intent(registerAdmin.this,adminInput.class));
                }else {
                    Toast.makeText(registerAdmin.this,"err" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void init(){
        emailtv = findViewById(R.id.email);
        passwordtv = findViewById(R.id.pswd);
        login = findViewById(R.id.loginBtn);
        pb = findViewById(R.id.progressbar);

        auth = FirebaseAuth.getInstance();
    }
}