package com.example.myapplicationbicycles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
    Context context;
    ArrayList<UserModel> list;
    // private RecyclerViewClickListener listener;
    public UserAdapter(ArrayList<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
        //this.listener = listener;
    }



    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3,parent,false);

        MyViewHolder viewHolder=new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {

        // ViewHolder viewHolder=(ViewHolder)holder;
        UserModel userData=list.get(position);
        holder.firstname.setText(userData.firstname);
        holder.lastname.setText(userData.lastname);
        holder.contact.setText(String.valueOf(userData.contact));
        holder.email.setText(userData.email);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /*public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }
*/
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstname,lastname,contact, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstname=itemView.findViewById(R.id.name);
            lastname=itemView.findViewById(R.id.name2);
            contact=itemView.findViewById(R.id.contact);
            email=itemView.findViewById(R.id.email);
            // itemView.setOnClickListener(this);

          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MYAdapter.this.listener !=null && getAbsoluteAdapterPosition()!=RecyclerView.NO_POSITION){
                        MYAdapter.this.listener.OnItemClick(getAbsoluteAdapterPosition());
                    }
                }
            });*/
        }
/*
        @Override
        public void onClick(View v) {

            listener.onClick(v,getAdapterPosition());
        }
        */

    }

}
