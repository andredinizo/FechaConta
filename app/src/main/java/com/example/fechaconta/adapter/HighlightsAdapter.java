package com.example.fechaconta.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Dishes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HighlightsAdapter extends RecyclerView.Adapter<HighlightsAdapter.MyHighlights> {


    private final List<Dishes> cardapio;
    private final int listsize;
    private final String idRestaurante;

    public HighlightsAdapter (List<Dishes> cardapio, String idRestaurante){
        this.cardapio = cardapio;
        this.listsize = cardapio.size();
        this.idRestaurante = idRestaurante;
    }
    
    @NonNull
    @Override
    public MyHighlights onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_highlights_cardapio, parent, false);
        
        
        return new MyHighlights(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHighlights holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagem = FirebaseStorage.getInstance().getReference().child("Restaurantes/Pratos/"+cardapio.get(position).getUrlImagem());
        Log.d("URL", "onSuccess: " + imagem);
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("URL", "onSuccess: " + uri);
                Picasso.get().load(uri).fit().centerCrop().into(holder.imagemPrato);
            }
        });

        holder.nomePrato.setText(cardapio.get(position).getName());
        holder.descPrato.setText(cardapio.get(position).getDescription());
        holder.valorPrato.setText("R$ " +String.format(cardapio.get(position).getValue().toString()));

        if (position==0){
            holder.LinearLayoutHighlights.setPadding(16,8,0,8);

        }

    }

    @Override
    public int getItemCount() {
        return this.listsize;
    }

    class MyHighlights extends RecyclerView.ViewHolder{
        
        ImageView imagemPrato;
        TextView nomePrato;
        TextView descPrato;
        TextView valorPrato;
        LinearLayout LinearLayoutHighlights;
        MaterialCardView cardHigh;
        
        public MyHighlights(@NonNull View itemView) {
            super(itemView);

            LinearLayoutHighlights = itemView.findViewById(R.id.LinearLayoutHighlights);
            cardHigh = itemView.findViewById(R.id.cardHighlight);
            imagemPrato = itemView.findViewById(R.id.imagemPrato);
            nomePrato = itemView.findViewById(R.id.nomePrato);
            descPrato = itemView.findViewById(R.id.descPrato);
            valorPrato = itemView.findViewById(R.id.valorPrato);
                        
        }
    } 
    
}
