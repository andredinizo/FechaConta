package com.example.fechaconta.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.fechaconta.R;
import com.example.fechaconta.adapter.SearchAdapter;
import com.example.fechaconta.models.Restaurant;
import com.example.fechaconta.utilitys.StringStuff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    public static final String TAG = "SEARCH_FRAGMENT";
    private SearchView searchView;
    private RecyclerView restSearchRecycler;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Restaurant> listRestaurant = new ArrayList<>();

    public SearchFragment(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchable, container, false);

        searchView = view.findViewById(R.id.search_view);
        restSearchRecycler = view.findViewById(R.id.recycler_restsearch);
        searchView.setQuery("",true);

        searchView.setFocusedByDefault(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        restSearchRecycler.setLayoutManager(layoutManager);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMyQuery(newText.toLowerCase());
                return true;
            }
        });


        return view;
    }




    private void searchMyQuery(final String query) {
        Query searchQuery = db.collection("Restaurant").orderBy("media", Query.Direction.DESCENDING);
        searchQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Restaurant> list = new ArrayList<>();

                    for(DocumentSnapshot documentSnapshot : task.getResult()){

                        if(StringStuff.converterString(documentSnapshot.get("nome").toString().toLowerCase(),
                                StringStuff.RETIRAR_ESPACOS)
                                .contains(query.toLowerCase())){

                            list.add(documentSnapshot.toObject(Restaurant.class));
                            list.get(list.size() - 1).setID_restaurante(documentSnapshot.getId());
                            Log.d(TAG, "onComplete: ======>" + documentSnapshot.getData().toString());

                        }

                        restSearchRecycler.setAdapter(new SearchAdapter(list));


                    }
                }

                Log.d(TAG, "onComplete: ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(TAG, "onFailure: get the restaurant ===>" + e.getMessage());
            }
        });
    }


}
