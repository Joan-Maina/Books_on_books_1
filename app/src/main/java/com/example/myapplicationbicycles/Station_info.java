package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Station_info extends AppCompatActivity {

    TextView marker,space, bikes, about;
    Button book, cancel;
    //TextView marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);

        marker = findViewById(R.id.tryiing);
        space = findViewById(R.id.no_space);
        about = findViewById(R.id.about_station);
        bikes = findViewById(R.id.no_bikes);
        book = findViewById(R.id.book);
        cancel=findViewById(R.id.cancel);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("message");
        DatabaseReference reference1 = database.getReference("bikes");


        //get data from maps activity
        String title = getIntent().getStringExtra("title");
        marker.setText(title);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Station_info.this, homepage.class);
                startActivity(i);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Station_info.this, Booking.class);
                startActivity(i);
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot .getValue(String.class);
                bikes.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Station_info.this,"database error",Toast.LENGTH_SHORT).show();
            }
        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n = snapshot .getValue(String.class);
                space.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}