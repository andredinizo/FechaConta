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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.fragments.RestauranteFragment;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private final String TAG = "RESTAURANTAT_ADAPTER";
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagem = storage.getReference().child("Restaurantes/"+ this.list.get(position).getUrlicon());

        Log.d("URL", "onSuccess2: " + imagem);
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

        holder.nomeRes.setText(this.list.get(position).getNome());
        holder.catRes.setText(this.list.get(position).getCategoria());
        holder.mediaRes.setText(String.valueOf(this.list.get(position).getMedia()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment,
                        new RestauranteFragment(list.get(position))).addToBackStack("HOMEFRAGMENT").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeRes;
        TextView catRes;
        TextView mediaRes;
        ImageView imageView;
        LinearLayout linearLayout;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeRes = itemView.findViewById(R.id.nome_res);
            catRes = itemView.findViewById(R.id.cat_res);
            mediaRes = itemView.findViewById(R.id.media_res);
            imageView = itemView.findViewById(R.id.logorestaurante);
            linearLayout = itemView.findViewById(R.id.linearlayout_restaurantes);
            cardView = itemView.findViewById(R.id.restaurant_cardview);
        }
    }
}
