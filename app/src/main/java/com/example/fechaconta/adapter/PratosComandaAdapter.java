package com.example.fechaconta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.ItemComanda;

import java.util.List;

public class PratosComandaAdapter extends RecyclerView.Adapter<PratosComandaAdapter.mViewHolder> {

    private List<ItemComanda> listaItens;

    public PratosComandaAdapter(List<ItemComanda> listaItens){

        this.listaItens = listaItens;

    }


    @NonNull
    @Override
    public PratosComandaAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_itens_comanda, parent, false);


        return new PratosComandaAdapter.mViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PratosComandaAdapter.mViewHolder holder, int position) {

        holder.nomePrato.setText(listaItens.get(position).getNomePrato());
        holder.valorPratoTotal.setText(String.valueOf(listaItens.get(position).getTotal()));

        holder.nomePrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.containerAdicionais.getVisibility()==View.VISIBLE){

                    holder.containerAdicionais.setVisibility(View.GONE);

                }

                if (holder.containerAdicionais.getVisibility()==View.GONE){

                    holder.containerAdicionais.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.listaItens.size();
    }


    static class mViewHolder extends RecyclerView.ViewHolder{

        private TextView nomePrato;
        private TextView valorPratoTotal;
        private LinearLayout containerAdicionais;


        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            nomePrato = itemView.findViewById(R.id.NomePratoComanda);
            valorPratoTotal = itemView.findViewById(R.id.ValorPratoComanda);
            containerAdicionais = itemView.findViewById(R.id.LinearLayoutAdicionaisItens);


        }
    }

}



