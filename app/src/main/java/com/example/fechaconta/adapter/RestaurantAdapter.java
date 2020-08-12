package com.example.fechaconta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private final String TAG = "RESTAURANTATADAPTER";
    private List<Restaurant> list = new ArrayList<>();

    public RestaurantAdapter (List<Restaurant> list){
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restaurante, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nomeres.setText(this.list.get(position).getNome());
        holder.catres.setText(this.list.get(position).getCategoria());
        holder.mediares.setText(String.valueOf(this.list.get(position).getMedia()));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeres;
        TextView catres;
        TextView mediares;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeres = itemView.findViewById(R.id.nomeres);
            catres = itemView.findViewById(R.id.catres);
            mediares = itemView.findViewById(R.id.mediares);

        }
    }
}
