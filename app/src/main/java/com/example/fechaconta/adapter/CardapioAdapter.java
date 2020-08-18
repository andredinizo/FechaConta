package com.example.fechaconta.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Dishes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardapioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dishes> pratos;
    private List<String> Categoria;

    public CardapioAdapter(List<Dishes> pratos) {
        this.pratos = pratos;

    }


    /*
    Type 0 ====> Titulo + Prato
    Type 1 ====> Prato
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        else if (pratos.get(position).getCategory()
                .equals(pratos.get(position - 1).getCategory()))
            return 1;

        else
            return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_categorias_cardapio, parent, false);
                return new CategoriaViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_itens_cardapio, parent, false);
                return new PratosViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference()
                .child("Restaurantes/Pratos/"+pratos.get(position).getUrlImagem());

        if (getItemViewType(position) == 0) {

            ((CategoriaViewHolder) holder).setViews(this.pratos.get(position));

            imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(((CategoriaViewHolder) holder).imagemPrato);
                }
            });
        }

        if (getItemViewType(position) == 1) {
            ((PratosViewHolder) holder).setViews(this.pratos.get(position));

            imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(((PratosViewHolder) holder).imagemPrato);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return pratos.size();
    }


    class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView categoria;
        TextView nomeItem;
        TextView precoItem;
        TextView descricaoItem;
        ImageView imagemPrato;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);

            categoria = itemView.findViewById(R.id.textView_categoria);
            nomeItem = itemView.findViewById(R.id.textView_nomeItem);
            precoItem = itemView.findViewById(R.id.textView_precoItem);
            descricaoItem = itemView.findViewById(R.id.textView_descricaoItem);
            imagemPrato = itemView.findViewById(R.id.categorias_imagemPratos);
        }

        public void setViews(Dishes dishes) {
            this.categoria.setText(dishes.getCategory());
            this.nomeItem.setText(dishes.getName());
            this.precoItem.setText(String.valueOf(dishes.getValue()));
            this.descricaoItem.setText(dishes.getDescription());


        }
    }


    class PratosViewHolder extends RecyclerView.ViewHolder {
        TextView nomeItem;
        TextView precoItem;
        TextView descricaoItem;
        ImageView imagemPrato;
        public PratosViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeItem = itemView.findViewById(R.id.textView_nomeItem);
            precoItem = itemView.findViewById(R.id.textView_precoItem);
            descricaoItem = itemView.findViewById(R.id.textView_descricaoItem);
            imagemPrato = itemView.findViewById(R.id.imagemPrato);
        }

        public void setViews(Dishes dishes) {
            this.nomeItem.setText(dishes.getName());
            this.precoItem.setText(String.valueOf(dishes.getValue()));
            this.descricaoItem.setText(dishes.getDescription());


        }


    }
}


