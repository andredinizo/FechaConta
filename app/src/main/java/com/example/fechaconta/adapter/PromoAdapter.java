package com.example.fechaconta.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Promotion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.MyViewHolder>{

    private List<Promotion> promotionList = new ArrayList<>();

    public PromoAdapter(List<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_promo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagem = storage.getReference().child("Promo/"+ this.promotionList.get(position).getUrl());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri)/*.resize(360*2,150*2)*/
                        .fit().centerCrop().into(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagempromo);

        }
    }


}
