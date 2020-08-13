package com.example.fechaconta.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagem = storage.getReference().child("Restaurantes/"+ this.list.get(position).getUrlicon());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(75*4, 75*4).centerCrop().into(holder.imageView);
            }
        });

        if (position == 0){
            holder.linearLayout.setPadding(0,8,0,0);
        }

        if (position == list.size()){
            holder.linearLayout.setPadding(0,0,0,0);
        }

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
        ImageView imageView;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeres = itemView.findViewById(R.id.nomeres);
            catres = itemView.findViewById(R.id.catres);
            mediares = itemView.findViewById(R.id.mediares);
            imageView = itemView.findViewById(R.id.logorestaurante);
            linearLayout = itemView.findViewById(R.id.linearlayout_restaurantes);
        }
    }
}
