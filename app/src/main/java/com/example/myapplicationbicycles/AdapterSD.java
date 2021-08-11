package com.example.myapplicationbicycles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSD extends RecyclerView.Adapter<AdapterSD.MyViewHolder> {

    List<StationData> list;
    private RecyclerViewClickListener listener;

    public AdapterSD(List<StationData> list,RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public AdapterSD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       // ViewHolder viewHolder = (ViewHolder) holder;
        StationData stationData = list.get(position);
        holder.name.setText(stationData.getName());
       holder.number.setText(stationData.getNumber().toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.station_name);
            number = itemView.findViewById(R.id.bikes);
       itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onClick(v,getAdapterPosition());
        }
    }
}
