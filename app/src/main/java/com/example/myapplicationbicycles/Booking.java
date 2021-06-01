package com.example.myapplicationbicycles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Booking extends AppCompatActivity {

    TextView instruction, code;

    Button generate, send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        instruction = findViewById(R.id.textView6);
        code = findViewById(R.id.textView8);
        generate = findViewById(R.id.button);
        send = findViewById(R.id.button4);
        //instruction = findViewById(R.id);
        
    }
}