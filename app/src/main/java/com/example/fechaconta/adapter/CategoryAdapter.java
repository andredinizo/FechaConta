package com.example.fechaconta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Category;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.models.Restaurant;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.catnome.setText(this.list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.intCount;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView catnome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            catnome = itemView.findViewById(R.id.catnome);




        }
    }


}
