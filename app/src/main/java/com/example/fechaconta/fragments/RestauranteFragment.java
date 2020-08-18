package com.example.fechaconta.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.adapter.CategoryAdapter;
import com.example.fechaconta.adapter.HighlightsAdapter;
import com.example.fechaconta.adapter.PromoAdapter;
import com.example.fechaconta.adapter.RestaurantAdapter;
import com.example.fechaconta.adapter.SnapHelperOneByOne;
import com.example.fechaconta.models.Category;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.models.Promotion;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
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
    final String TAG = "RestauranteFragment";
    private Restaurant restaurant;
    ImageView imageView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nomeRestaurant;
    TextView endereçoRestaurant;
    TextView restaurantRating;
    TextView restaurantCategoria;
    RecyclerView recyclerHighlights;
    RecyclerView recyclerViewRestaurant;


    public RestauranteFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RestauranteFragment() {
    }

    public void AtualizaRestaurantes() {
        //Recupera os Restaurantes Ordenados pela media do Firestore
        Query queryRes = db.collection("Restaurant").orderBy("media", Query.Direction.DESCENDING);
        queryRes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Restaurant> listRes = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        listRes.add(document.toObject(Restaurant.class));
                        Log.d(TAG, "onComplete:  =====> " + document.getData());

                    }

                    RestaurantAdapter restaurantAdapter = new RestaurantAdapter(listRes);
                    restaurantAdapter.notifyDataSetChanged();
                    recyclerViewRestaurant.setAdapter(restaurantAdapter);

                } else {
                    Log.d(TAG, "onFailure: Falha ao recuperar os Restaurantes");
                }


            }
        });
    }

    public void AtualizaCardapio(final Restaurant restaurante){
        Query queryHighlights = db.collection("Restaurant").document(restaurante.getID_restaurante()).collection("Dishes");
        queryHighlights.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                List<Dishes> cardapio = new ArrayList<>();
                List<Dishes> highlights = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()){
                        cardapio.add(document.toObject(Dishes.class));
                        cardapio.get(cardapio.size()-1).setID(document.getId());
                        Log.d("DISHES", "onComplete: "+document.getId());
                        Log.d("DISHESnome", "onComplete: "+document.toObject(Dishes.class).getName());
                        Log.d("DISHESid", "onComplete: "+document.toObject(Dishes.class).getID());
                        Log.d("DISHEShigh", "onComplete: "+document.toObject(Dishes.class).getIsHighlight());
                        ;
                        if(cardapio.get(cardapio.size() - 1).getIsHighlight()==1){
                            highlights.add(cardapio.get(cardapio.size()-1));
                            Log.d("DISHESsize", "onComplete: "+highlights.size());
                        }

                    }

                    HighlightsAdapter highlightsadapter = new HighlightsAdapter(highlights, restaurante.getID_restaurante());
                    highlightsadapter.notifyDataSetChanged();
                    recyclerHighlights.setAdapter(highlightsadapter);


                }else {
                    Toast.makeText(getActivity(), "Não foi possível atualizar o cardápio", Toast.LENGTH_LONG).show();

                }



            }


        });
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

        /*
        *
        *  POPULA HIGHLIGHTS
        *
        * */

        recyclerHighlights = view.findViewById(R.id.destaques_cardapio);
        RecyclerView.LayoutManager horizontalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerHighlights.setLayoutManager(horizontalManager);
        recyclerHighlights.setHasFixedSize(false);
        recyclerHighlights.setNestedScrollingEnabled(false);



        recyclerViewRestaurant = view.findViewById(R.id.recycler_restaurantes);
        RecyclerView.LayoutManager layManagerRes = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewRestaurant.setLayoutManager(layManagerRes);
        recyclerViewRestaurant.setHasFixedSize(false);
        recyclerViewRestaurant.setNestedScrollingEnabled(false);

        nomeRestaurant.setText(  restaurant.getNome());
        endereçoRestaurant.setText(restaurant.getEnder()+ " - "+ restaurant.getCidade() +"/"+restaurant.getEstado() );
        restaurantRating.setText(String.valueOf(restaurant.getMedia()));
        restaurantCategoria.setText(restaurant.getCategoria());

        AtualizaRestaurantes();
        AtualizaCardapio(restaurant);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference().child("Restaurantes/Header/"+ restaurant.getUrlheader());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });




        final AppBarLayout appBar;
        final AppBarLayout tablayout;
        final Toolbar materialToolbar;
        final CollapsingToolbarLayout CollapsingToolbar;

        CollapsingToolbar = view.findViewById(R.id.colapse_cardapio);
        materialToolbar = view.findViewById(R.id.toolbar_cardapio);
        tablayout = view.findViewById(R.id.tablayout);
        CollapsingToolbar.setContentScrimColor(Color.WHITE);
        CollapsingToolbar.setStatusBarScrimColor(Color.WHITE);
        CollapsingToolbar.setTitleEnabled(true);
        appBar = view.findViewById(R.id.appBarCardapio);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow=true;
            int scrollRange =-1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float OffsetA = appBarLayout.getY()/appBar.getTotalScrollRange();

            if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    CollapsingToolbar.setTitle(restaurant.getNome());
                    tablayout.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if(isShow) {
                    CollapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    tablayout.setVisibility(View.GONE);
                    isShow = false;
                }
            }

        });



        return view;
    }
}
