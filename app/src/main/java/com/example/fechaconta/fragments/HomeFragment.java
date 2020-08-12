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
import com.example.fechaconta.models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG = "HOMEFRAGMENT";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewCategory;


   public HomeFragment(){
        //Construtor publico vazio (Necessário)
   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container,false);
        recyclerViewCategory = view.findViewById(R.id.recycler_categorias);

        RecyclerView.LayoutManager layManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCategory.setLayoutManager(layManager);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.isNestedScrollingEnabled();








        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Category> listCat = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()){
                        listCat.add(document.toObject(Category.class));
                        Log.d(TAG, "onComplete: ======>" + document.getData().toString());
                    }

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
