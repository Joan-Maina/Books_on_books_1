package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminInput extends AppCompatActivity {

    private Button save,view;
    private EditText inputname, inputnumber;
    DatabaseReference reference;
    private FirebaseRecyclerOptions<model> options;
    private FirebaseRecyclerAdapter<model, MyViewHolder2> adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);
        save = findViewById(R.id.button);
        view = findViewById(R.id.button2);
        inputname = findViewById(R.id.editTextTextPersonName);
        inputnumber = findViewById(R.id.editTextNumber);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference().child("stations");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(inputnumber.getText().toString());
                String name = inputname.getText().toString();
                String key =reference.push().getKey();
                reference.child(key).child("number").setValue(number);
                reference.child(key).child("name").setValue(name);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminInput.this, AdminViewUsers.class);
                startActivity(i);
            }
        });
        options=new FirebaseRecyclerOptions.Builder<model>().setQuery(reference,model.class).build();
        adapter=new FirebaseRecyclerAdapter<model, MyViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder, int position, @NonNull model model) {

                holder.name.setText(model.getName());
                holder.number.setText(""+model.getNumber());

            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false);
                return new MyViewHolder2(v);

            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}