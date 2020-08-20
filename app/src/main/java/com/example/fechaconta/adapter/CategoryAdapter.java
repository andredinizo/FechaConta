package com.example.fechaconta.adapter;

import android.app.Activity;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.fragments.CategoriaFragment;
import com.example.fechaconta.models.Category;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.models.Restaurant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>  {
    private final String TAG = "CATEGORYADAPTER";
    private List<Category> list;
    private int intCount;


    public CategoryAdapter (List<Category> list, int intCount){
        this.intCount = intCount;

        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorias, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagem = FirebaseStorage.getInstance().getReference().child("Categorias" + "/" + list.get(position).getUrlimage());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(400,240).centerCrop().into(holder.imagemView);
            }
        });

        if(position==0){
            holder.linearlayout.setPadding(32,0,0,0);
        }


        holder.catnome.setText(this.list.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new CategoriaFragment(list.get(position))).commit();
                Log.d(TAG, "onClick: ========> cliclou" );
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.intCount;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catnome;
        ImageView imagemView;
        LinearLayout linearlayout;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemView = itemView.findViewById(R.id.imagemcategoria);
            catnome = itemView.findViewById(R.id.catnome);
            linearlayout = itemView.findViewById(R.id.adaptercategorias_layout);
            cardView = itemView.findViewById(R.id.categoria_cardview);



        }
    }


}
