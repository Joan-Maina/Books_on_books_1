package com.example.myapplicationbicycles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminViewUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<UserModel> list;
    UserAdapter UserAdapter;
    //private UserAdapter.RecyclerViewClickListener listener;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching data");
        progressDialog.show();
        recyclerView = findViewById(R.id.userslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<UserModel>();

        recyclerView.setHasFixedSize(true);
        UserAdapter = new UserAdapter(list, AdminViewUsers.this);
        db = FirebaseFirestore.getInstance();

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error!=null){

                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();}
                    Log.e("Firestore error",error.getMessage());
                    return;
                }
                for (DocumentChange dc:value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){

                        list.add(dc.getDocument().toObject(UserModel.class));
                    }
                    UserAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }

                recyclerView.setAdapter(UserAdapter);
            }

        });

    }
}

