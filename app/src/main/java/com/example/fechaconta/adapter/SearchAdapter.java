package com.example.fechaconta.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Category;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    List<Restaurant> restaurantList = new ArrayList<>();

    public SearchAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference().child("Restaurantes/"+ this.restaurantList.get(position).getUrlicon());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(75*4, 75*4).into(holder.iconImageView);
            }
        });
        holder.nomeTextView.setText(this.restaurantList.get(position).getNome());
        holder.categTextView.setText(this.restaurantList.get(position).getCategoria());
        holder.mediaTextView.setText(String.valueOf(this.restaurantList.get(position).getMedia()));


    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeTextView;
        TextView mediaTextView;
        TextView categTextView;
        ImageView iconImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeTextView = itemView.findViewById(R.id.result_nomeres);
            mediaTextView = itemView.findViewById(R.id.result_mediares);
            categTextView = itemView.findViewById(R.id.result_catres);
            iconImageView = itemView.findViewById(R.id.result_logo);


        }
    }
}
