package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class stationsDisplay extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference reference;
    //myAdapter myadapter;
    List<StationData> list;
    AdapterSD adapterSD;
    private AdapterSD.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_display);

        reference = FirebaseDatabase.getInstance().getReference().child("stations");
        recyclerView = findViewById(R.id.stationslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StationData mystation = dataSnapshot.getValue(StationData.class);
                    list.add(mystation);
                }
                adapterSD =new AdapterSD(list, listener);
                //myViewHolder.notifyDataSetChanged();
                recyclerView.setAdapter(adapterSD);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //recyclerView.setHasFixedSize(false);

        listener = new AdapterSD.RecyclerViewClickListener(){
            @Override
            public void onClick(View v, int position){
                Intent intent = new Intent(getApplicationContext(), Station_info.class);
                intent.putExtra("name", list.get(position).getName().toString());
                intent.putExtra("number", list.get(position).getNumber().toString());
                startActivity(intent);
            }
        };

    }
}