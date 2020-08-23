package com.example.fechaconta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Adicional;
import com.microsoft.appcenter.ingestion.models.json.DefaultLogSerializer;

import java.text.NumberFormat;
import java.util.List;

public class AdicionaisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Adicional> adicionals;

    public AdicionaisAdapter(List<Adicional> adicionals) {

        this.adicionals = adicionals;
    }

    /**
     * Tipo 0 ==> Cabeçalho  (com Preço)
     * Tipo 1 ==> Corpo      (com Preço)
     * Tipo 2 ==> Cabeçalho  (sem Preço)
     * Tipo 3 ==> Corpo      (sem Preço)
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            if (adicionals.get(position).isIsGratis())
                return 2; //Se for o Primeiro for Gratis
                //retorna Cabeçalho sem Preço.
            else
                return 0; //Senão for Gratis Retorna
            //Cabeçalho sem Preço.
        }else
            if(adicionals.get(position).getNomeAd().equals(adicionals.get(position - 1).getNomeAd())) {
                // ========================> É Corpo
                if (adicionals.get(position).isIsGratis())
                    // ====================> Corpo sem Preço
                    return 3;
                else
                    // ====================> Corpo com Preço
                    return 1;
            }else {
                // ==============> É Cabeçalho
                if(adicionals.get(position).isIsGratis())
                    // ==========> Cabeçalho sem Preço
                    return 2;
                else
                    // ==========> Cabeçalho com Preço
                    return 0;
            }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType){
            case 0 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_first_item, parent, false);
                return new FirstItemViewHolder(view);
            case 1 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item, parent, false);
                return new ItemViewHolder(view);
            case 2 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_first_item_novalue, parent, false);
                return new FirstItemNoValureViewHolder(view);
            case 3 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_novalue, parent, false);
                return new ItemNoValueViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case 0 :
                ((FirstItemViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 1 :
                ((ItemViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 2 :
                ((FirstItemNoValureViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 3 :
                ((ItemNoValueViewHolder) holder).setViews(adicionals.get(position));
                break;
        }



    }

    @Override
    public int getItemCount() {
        return this.adicionals.size();
    }

    public class FirstItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicional;
        private TextView textViewItemAdicionalTitulo;
        private CheckBox checkBoxItemAdicional;
        private TextView textViewItemAdicionalValor;

        public FirstItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            checkBoxItemAdicional = itemView.findViewById(R.id.checkbox_item_adicional);
            textViewItemAdicionalValor = itemView.findViewById(R.id.textview_item_adicional_valor);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicional.setText(adicional.getNomeAd());
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
            textViewItemAdicionalValor.setText(NumberFormat.getCurrencyInstance().format(adicional.getValorItem()));

        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicionalTitulo;
        private CheckBox checkBoxItemAdicional;
        private TextView textViewItemAdicionalValor;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            checkBoxItemAdicional = itemView.findViewById(R.id.checkbox_item_adicional);
            textViewItemAdicionalValor = itemView.findViewById(R.id.textview_item_adicional_valor);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
            textViewItemAdicionalValor.setText(NumberFormat.getCurrencyInstance().format(adicional.getValorItem()));
        }
    }

    public class FirstItemNoValureViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicional;
        private TextView textViewItemAdicionalTitulo;
        private CheckBox checkBoxItemAdicional;

        public FirstItemNoValureViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            checkBoxItemAdicional = itemView.findViewById(R.id.checkbox_item_adicional);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicional.setText(adicional.getNomeAd());
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
        }
    }

    public class ItemNoValueViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicionalTitulo;
        private CheckBox checkBoxItemAdicional;

        public ItemNoValueViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            checkBoxItemAdicional = itemView.findViewById(R.id.checkbox_item_adicional);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
        }
    }

}
