package com.example.fechaconta.adapter;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Adicional;
import com.microsoft.appcenter.ingestion.models.json.DefaultLogSerializer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class AdicionaisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Adicional> adicionals;
    private List<RadioGroup> radioGroups = new ArrayList<>();

    public AdicionaisAdapter(List<Adicional> adicionals) {

        this.adicionals = adicionals;
    }

    /**
     * CheckBox (-) Default ADICIONAIS - TIPO_ZERO, TIPO_TRES
     * Tipo 00 ==> Cabeçalho  (com Preço)
     * Tipo 01 ==> Corpo      (com Preço)
     * Tipo 02 ==> Cabeçalho  (sem Preço)
     * Tipo 03 ==> Corpo      (sem Preço)
     *
     * RadioButtom (1) ADICIONAIS - TIPO_UM
     * Tipo 10 ==> Cabeçalho  (com Preço)
     * Tipo 11 ==> Corpo      (com Preço)
     * Tipo 12 ==> Cabeçalho  (sem Preço)
     * Tipo 13 ==> Corpo      (sem Preço)
     *
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        //Verificamos sé o ADICIONAIS é do tipo
        // 1 se sim, iremos usar radio BUTTOM
        if (adicionals.get(position).getTipoAd() == 1) {

            if(position == 0) {                                                // As tomadas são as mesmas para todo tipo de
                if (adicionals.get(position).isIsGratis())                     // de ADICIONAIS, ja que todos tem Cabeçalho
                    return 12; //Se for o Primeiro for Gratis                  // Corpo e etc... */
                    //retorna Cabeçalho sem Preço.
                else
                    return 10; //Senão for Gratis Retorna
                //Cabeçalho sem Preço.
            }else
            if(adicionals.get(position).getNomeAd().equals(adicionals.get(position - 1).getNomeAd())) {
                // ========================> É Corpo
                if (adicionals.get(position).isIsGratis())
                    // ====================> Corpo sem Preço
                    return 13;
                else
                    // ====================> Corpo com Preço
                    return 11;
            }else {
                // ==============> É Cabeçalho
                if(adicionals.get(position).isIsGratis())
                    // ==========> Cabeçalho sem Preço
                    return 12;
                else
                    // ==========> Cabeçalho com Preço
                    return 10;
            }


        }

        // Senão usamos o Check box normal.
        else{
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
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType){
            // CheckBox - 0
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

            //RadioButtom - 1
            case 10 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_first_item_radio, parent, false);
                return new RadioFirstItemViewHolder(view);
            case 11 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_radio, parent, false);
                return new RadioItemViewHolder(view);
            case 12 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_first_item_novalue_radio, parent, false);
                return new RadioFirstItemNoValureViewHolder(view);
            case 13 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_novalue_radio, parent, false);
                return new RadioItemNoValueViewHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {



        switch (getItemViewType(position)){
            // CHECKBOX - DEFAULT
            case 0 :


                setListenerCheckbox(((FirstItemViewHolder) holder).checkBoxItemAdicional, position);
                ((FirstItemViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 1 :

                setListenerCheckbox(((ItemViewHolder) holder).checkBoxItemAdicional, position);
                ((ItemViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 2 :

                setListenerCheckbox(((FirstItemNoValureViewHolder) holder).checkBoxItemAdicional, position);
                ((FirstItemNoValureViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 3 :

                setListenerCheckbox(((ItemNoValueViewHolder) holder).checkBoxItemAdicional, position);
                ((ItemNoValueViewHolder) holder).setViews(adicionals.get(position));
                break;

            // RADIOBUTTOM - 1
            case 10 :

                setListenerRadiobutton(((RadioFirstItemViewHolder) holder).radiobuttonItemAdicional, position);
                ((RadioFirstItemViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 11 :

                setListenerRadiobutton( ((RadioItemViewHolder) holder).radiobuttonItemAdicional, position);
                ((RadioItemViewHolder) holder).setViews(adicionals.get(position));
                break;

            case 12 :

                setListenerRadiobutton( ((RadioFirstItemNoValureViewHolder) holder).radiobuttonItemAdicional, position);
                ((RadioFirstItemNoValureViewHolder) holder).setViews(adicionals.get(position));
                break;
            case 13 :

                setListenerRadiobutton(((RadioItemNoValueViewHolder) holder).radiobuttonItemAdicional, position);
                ((RadioItemNoValueViewHolder) holder).setViews(adicionals.get(position));
                break;
        }



    }

    @Override
    public int getItemCount() {
        return this.adicionals.size();
    }

    private void setListenerRadiobutton (RadioButton radiobuttonItemAdicional, int position){

    }


    private void setListenerCheckbox (final CheckBox checkBoxItemAdicional, final int position){
        checkBoxItemAdicional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    if (adicionals.get(position).verificarCheckB(adicionals)){

                        adicionals.get(position).setInclude(true);
                        checkBoxItemAdicional.setChecked(true);

                    }else {

                        adicionals.get(position).setInclude(false);
                        checkBoxItemAdicional.setChecked(false);

                    }

                }else {


                    adicionals.get(position).setInclude(false);
                    checkBoxItemAdicional.setChecked(false);

                }
            }
        });
    }



    /* VIEWS HOLDERS CHECKBOX */

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

    /* VIEWS HOLDERS RADIOBUTTOM */
    public class RadioFirstItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicional;
        private TextView textViewItemAdicionalTitulo;
        private RadioButton radiobuttonItemAdicional;
        private TextView textViewItemAdicionalValor;

        public RadioFirstItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            radiobuttonItemAdicional = itemView.findViewById(R.id.radiobutton_item_adicional);
            textViewItemAdicionalValor = itemView.findViewById(R.id.textview_item_adicional_valor);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicional.setText(adicional.getNomeAd());
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
            textViewItemAdicionalValor.setText(NumberFormat.getCurrencyInstance().format(adicional.getValorItem()));

        }
    }

    public class RadioItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicionalTitulo;
        private RadioButton radiobuttonItemAdicional;
        private TextView textViewItemAdicionalValor;


        public RadioItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            radiobuttonItemAdicional = itemView.findViewById(R.id.radiobutton_item_adicional);
            textViewItemAdicionalValor = itemView.findViewById(R.id.textview_item_adicional_valor);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
            textViewItemAdicionalValor.setText(NumberFormat.getCurrencyInstance().format(adicional.getValorItem()));
        }
    }

    public class RadioFirstItemNoValureViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicional;
        private TextView textViewItemAdicionalTitulo;
        private RadioButton radiobuttonItemAdicional;

        public RadioFirstItemNoValureViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            radiobuttonItemAdicional = itemView.findViewById(R.id.radiobutton_item_adicional);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicional.setText(adicional.getNomeAd());
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
        }
    }

    public class RadioItemNoValueViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemAdicionalTitulo;
        private RadioButton radiobuttonItemAdicional;

        public RadioItemNoValueViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAdicionalTitulo = itemView.findViewById(R.id.textview_item_adicional_titulo);
            radiobuttonItemAdicional = itemView.findViewById(R.id.radiobutton_item_adicional);
        }

        public void setViews(Adicional adicional) {
            textViewItemAdicionalTitulo.setText(adicional.getNomeItem());
        }
    }

}
