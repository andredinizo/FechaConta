package com.example.fechaconta.fragments;

import android.app.DownloadManager;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.adapter.RestaurantAdapter;
import com.example.fechaconta.models.Category;
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

public class CategoriaFragment extends Fragment {
    private final String TAG = "CATEGORIA_FRAGMENT";
    private Category category;

    private ImageView imageView;
    private TextView catTextView;
    private RecyclerView recyclerView;

    public CategoriaFragment(Category category){
        this.category = category;
    }

    public CategoriaFragment (){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        imageView = view.findViewById(R.id.capa_restaurante);;
        catTextView = view.findViewById(R.id.nome_categoria);
        recyclerView = view.findViewById(R.id.recycler_restaurantes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        catTextView.setText(category.getName());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference().child("Categorias/"+ category.getUrlimage());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });

        Query query = FirebaseFirestore.getInstance().collection("Restaurant");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Restaurant> restaurantList = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult()){
                        if(document.get("categoria").toString().toLowerCase()
                                .contains(category.getName().toLowerCase())){
                            restaurantList.add(document.toObject(Restaurant.class));
                            Log.d(TAG, "onComplete: ========> "+ document.getData().toString());
                        }

                        recyclerView.setAdapter(new RestaurantAdapter(restaurantList));

                    }
                }else{
                    Log.d(TAG, "onComplete: Falha ao resgatar os restaurante da Categoria: " + category.getName());
                }
            }
        });

        return view;
    }
}
