package com.example.fechaconta.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.adapter.CategoryAdapter;
import com.example.fechaconta.adapter.PromoAdapter;
import com.example.fechaconta.adapter.RestaurantAdapter;
import com.example.fechaconta.models.Category;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment {
    private final String TAG = "HOMEFRAGMENT";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewRestaurant;
    private RecyclerView recyclerViewPromo;


   public HomeFragment(){
        //Construtor publico vazio (Necessário)
   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_home, container,false);

       PromoAdapter promoAdapter = new PromoAdapter();

        recyclerViewCategory = view.findViewById(R.id.recycler_categorias);
        recyclerViewRestaurant = view.findViewById(R.id.recycler_restaurantes);
        recyclerViewPromo = view.findViewById(R.id.recyclerpromo);

        //Instancia os layout Manager e configura os recycler
        RecyclerView.LayoutManager layManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layManagerRes = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layManagerPromo = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCategory.setLayoutManager(layManagerCat);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        recyclerViewRestaurant.setLayoutManager(layManagerRes);
        recyclerViewRestaurant.setHasFixedSize(false);
        recyclerViewRestaurant.setNestedScrollingEnabled(false);
        recyclerViewPromo.setLayoutManager(layManagerPromo);
        recyclerViewPromo.setAdapter(promoAdapter);

        ScrollingPagerIndicator recyclerIndicator = view.findViewById(R.id.dotspromocao);
        recyclerIndicator.attachToRecyclerView(recyclerViewPromo);



        //Recupera os Restaurantes Ordenados pela media do Firestore
        Query queryRes = db.collection("Restaurant").orderBy("media", Query.Direction.DESCENDING);
        queryRes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Restaurant> listRes = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult()){
                        listRes.add(document.toObject(Restaurant.class));
                        Log.d(TAG, "onComplete:  =====> " + document.getData() );

                    }

                    RestaurantAdapter restaurantAdapter = new RestaurantAdapter(listRes);
                    recyclerViewRestaurant.setAdapter(restaurantAdapter);

                }else{
                    Log.d(TAG, "onFailure: Falha ao recuperar os Restaurantes");
                }


            }
        });



        //Recupera as Categorias do Firestore
        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Category> listCat = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()){
                        //Joga as Categorias no List
                        listCat.add(document.toObject(Category.class));
                        Log.d(TAG, "onComplete: ======>" + document.getData().toString());
                    }

                    //Instancia e seta o Adapter com a lista de categorias
                    CategoryAdapter categoryAdapter = new CategoryAdapter(listCat, listCat.size());
                    recyclerViewCategory.setAdapter(categoryAdapter);


                }else{
                    Log.d(TAG, "onComplete: Se fudeu");
                }


            }
        });


        return view;
    }
}
