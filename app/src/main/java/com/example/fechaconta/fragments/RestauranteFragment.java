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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.MainActivity;
import com.example.fechaconta.R;
import com.example.fechaconta.adapter.CardapioAdapter;
import com.example.fechaconta.adapter.HighlightsAdapter;
import com.example.fechaconta.adapter.RestaurantAdapter;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iammert.tabscrollattacherlib.TabScrollAttacher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestauranteFragment extends Fragment {

    final static public String TAG = "RestauranteFragment";
    private Restaurant restaurant;
    ImageView imageView;
    TextView nomeRestaurant;
    TextView endereçoRestaurant;
    TextView restaurantRating;
    TextView restaurantCategoria;
    RecyclerView recyclerDestaques;
    RecyclerView recyclerPratos;
    Toolbar toolbarRestaurante;
    List<Integer> indexTab;
    CoordinatorLayout coordinatorLayout;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TabLayout TabCategorias;


    public RestauranteFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RestauranteFragment() {
    }

    public void AttachTab(List<Integer> index){

        TabScrollAttacher tabAttacher = new TabScrollAttacher(TabCategorias, recyclerPratos, index);

    }

    public void AtualizaCardapio(final Restaurant restaurante) {
        Query queryHighlights = db.collection("Restaurant")
                .document(restaurante.getID_restaurante()).collection("Dishes").orderBy("category");
        queryHighlights.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                List<Dishes> cardapio = new ArrayList<>();
                List<Dishes> highlights = new ArrayList<>();
                List<String> categorias = new ArrayList<>();
                List<Integer> indexCategorias = new ArrayList<>();
                int position = 0;
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        cardapio.add(document.toObject(Dishes.class));
                        cardapio.get(cardapio.size() - 1).setID(document.getId());


                        if (position==0){

                            categorias.add(cardapio.get(position).getCategory());
                            indexCategorias.add(position);

                        } else if (!cardapio.get(position).getCategory().equals(cardapio.get(position - 1).getCategory())) {

                            categorias.add(cardapio.get(position).getCategory());
                            indexCategorias.add(position);

                        }


                        if (cardapio.get(cardapio.size() - 1).getIsHighlight() == 1) {

                            highlights.add(cardapio.get(cardapio.size() - 1));
                            Log.d("DISHESsize", "onComplete: " + highlights.size());

                        }
                        position++;
                    }

                    HighlightsAdapter highlightsadapter = new HighlightsAdapter(highlights, restaurante.getID_restaurante());
                    highlightsadapter.notifyDataSetChanged();
                    recyclerDestaques.setAdapter(highlightsadapter);

                    CardapioAdapter cardapioAdapter = new CardapioAdapter(cardapio);
                    cardapioAdapter.notifyDataSetChanged();
                    recyclerPratos.setAdapter(cardapioAdapter);


                    CriaTabs(categorias,indexCategorias);


                } else {
                    Toast.makeText(getActivity(), "Não foi possível atualizar o cardápio", Toast.LENGTH_LONG).show();

                }


            }


        });
    }

    public void CriaTabs(List<String> listaCategorias, List<Integer> listaIndex){

        if(listaCategorias.size()>0){

            for (int i=0;i<listaCategorias.size();i++){

                TabCategorias.addTab(TabCategorias.newTab().setText(listaCategorias.get(i)));
                //TODO: Linkar Tabs com Recycler
                TabCategorias.setTabMode(TabLayout.MODE_SCROLLABLE);
                TabCategorias.setTabGravity(TabLayout.GRAVITY_FILL);
            }

        }



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
        TabCategorias = view.findViewById(R.id.TabCategorias);
        toolbarRestaurante = view.findViewById(R.id.toolbar_restaurante);
        coordinatorLayout = view.findViewById(R.id.CoordinatorCardapio);

        MainActivity mainRestaurante = (MainActivity) getActivity();
        mainRestaurante.SetFitsWindows(false);


        toolbarRestaurante.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        /*
         *
         *  POPULA HIGHLIGHTS
         *
         * */

        recyclerDestaques = view.findViewById(R.id.recycler_destaques);
        RecyclerView.LayoutManager horizontalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerDestaques.setLayoutManager(horizontalManager);
        recyclerDestaques.setHasFixedSize(false);
        recyclerDestaques.setNestedScrollingEnabled(false);


        recyclerPratos = view.findViewById(R.id.recycler_pratos);
        RecyclerView.LayoutManager layManagerRes = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerPratos.setLayoutManager(layManagerRes);
        recyclerPratos.setHasFixedSize(false);
        recyclerPratos.setNestedScrollingEnabled(false);




        nomeRestaurant.setText(restaurant.getNome());
        endereçoRestaurant.setText(restaurant.getEnder() + " - " + restaurant.getCidade() + "/" + restaurant.getEstado());
        restaurantRating.setText(String.valueOf(restaurant.getMedia()));
        restaurantCategoria.setText(restaurant.getCategoria());


        AtualizaCardapio(restaurant);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference().child("Restaurantes/Header/" + restaurant.getUrlheader());
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
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float OffsetA = appBarLayout.getY() / appBar.getTotalScrollRange();

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    CollapsingToolbar.setTitle(restaurant.getNome());
                    tablayout.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    CollapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    tablayout.setVisibility(View.GONE);
                    isShow = false;
                }
            }

        });



        return view;
    }
}
