package com.example.myapplicationbicycles;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder2   extends RecyclerView.ViewHolder {

    TextView name,number;
    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        number = itemView.findViewById(R.id.number);

    }
}
