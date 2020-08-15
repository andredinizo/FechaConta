package com.example.fechaconta.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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
       final View view =inflater.inflate(R.layout.fragment_home, container,false);

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



        final LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerViewPromo);


        //Recupera as Promoções do Firestore
        Query promoQuery = db.collection("Promotion").whereEqualTo("ativa", true)
                .orderBy("relevancia", Query.Direction.DESCENDING);
        promoQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Promotion> promotionList = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult()){
                        promotionList.add(document.toObject(Promotion.class));
                        Log.d(TAG, "onComplete: =======>" + document.getData());
                    }

                    recyclerViewPromo.setAdapter(new PromoAdapter(promotionList));
                    ScrollingPagerIndicator recyclerIndicator = view.findViewById(R.id.dotspromocao);
                    recyclerIndicator.attachToRecyclerView(recyclerViewPromo);

                }else{
                    Log.d(TAG, "onComplete: Falha ao Resgatar as Promoções");
                }
            }
        });


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


        //Diminui Opacidade do Header
        final CollapsingToolbarLayout toolbar = view.findViewById(R.id.toolbar);
        final AppBarLayout appbar = view.findViewById(R.id.headerlayout);
        //final LinearLayout layout = view.findViewById(R.id.principahome);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int intColorCode=0;


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float offsetAlpha = (appBarLayout.getY() / appbar.getTotalScrollRange());
              // appBarLayout.setBackgroundColor(Color.argb(0,255,255,255));
                //toolbar.setBackgroundColor(Color.argb(0,255,255,255));
                //1 - (offsetAlpha * -1)
            }
        });



        return view;
    }
}
