package com.example.fechaconta.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.adapter.CategoryAdapter;
import com.example.fechaconta.adapter.PromoAdapter;
import com.example.fechaconta.adapter.RestaurantAdapter;
import com.example.fechaconta.adapter.SnapHelperOneByOne;
import com.example.fechaconta.models.Category;
import com.example.fechaconta.models.Promotion;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class RestauranteFragment extends Fragment {
    private Restaurant restaurant;
    ImageView imageView;
    TextView nomeRestaurant;
    TextView endereçoRestaurant;
    TextView restaurantRating;
    TextView restaurantCategoria;



    public RestauranteFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RestauranteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurante, container, false);

        imageView = view.findViewById(R.id.capa_restaurante);
        nomeRestaurant = view.findViewById(R.id.nome_restaurante);
        endereçoRestaurant = view.findViewById(R.id.endereço_restaurante);
        restaurantRating = view.findViewById(R.id.restaurant_rating);
        restaurantCategoria = view.findViewById(R.id.restaurante_categoria);

        nomeRestaurant.setText(  restaurant.getNome());
        endereçoRestaurant.setText(restaurant.getEnder()+ " - "+ restaurant.getCidade() +"/"+restaurant.getEstado() );
        restaurantRating.setText(String.valueOf(restaurant.getMedia()));
        restaurantCategoria.setText(restaurant.getCategoria());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference().child("Restaurantes/Header"+ restaurant.getUrlheader());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(imageView);
            }
        });


        return view;
    }
}
