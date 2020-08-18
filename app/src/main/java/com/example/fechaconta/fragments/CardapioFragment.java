package com.example.fechaconta.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.adapter.PromoAdapter;
import com.example.fechaconta.adapter.RestaurantAdapter;
import com.example.fechaconta.models.Promotion;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class CardapioFragment extends Fragment {

    private String TAG = "TESTE";
    private RecyclerView recycler;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AppBarLayout header;
    private MaterialToolbar toolbar;
    private CollapsingToolbarLayout collapsing;

    /*

    public CardapioFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Cardapio);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        final View view = inflater.inflate(R.layout.fragment_cardapio, container, false);
        getActivity().setTheme(R.style.Cardapio);
        header = view.findViewById(R.id.toolbarheader_cardapio);
        toolbar = view.findViewById(R.id.toolbar_cardapio);
        collapsing = view.findViewById(R.id.colapse_cardapio);
        collapsing.setTitle("Testando");
        collapsing.setContentScrim(getResources().getDrawable(R.drawable.scrim2,null));

        header.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            }
        });




       // RECYCLER TESTE
        recycler = view.findViewById(R.id.Recyclerteste);

        RecyclerView.LayoutManager layManagerRes = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layManagerRes);
        recycler.setHasFixedSize(false);
        recycler.setNestedScrollingEnabled(false);

            //Recupera as Promoções do Firestore
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
                    recycler.setAdapter(restaurantAdapter);

                } else {
                    Log.d(TAG, "onFailure: Falha ao recuperar os Restaurantes");
                }


            }
        });




        return localInflater.inflate(R.layout.fragment_cardapio, container, false);
    }

    */
}
