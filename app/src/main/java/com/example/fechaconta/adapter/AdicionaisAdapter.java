package com.example.fechaconta.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Adicionais;
import com.example.fechaconta.models.Adicional;
import com.example.fechaconta.utilitys.PxDp;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

import static android.view.ViewGroup.*;
import static android.view.ViewGroup.LayoutParams.*;

public class AdicionaisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Adicionais> adicionais;

    public AdicionaisAdapter(List<Adicionais> adicionais) {

        this.adicionais = adicionais;
    }

    /**
     * Tipo 0 --- CheckBox
     * tipo 1 --- RadioButton
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        switch (adicionais.get(position).getTipoAd()){

            case 0 :
                return 0;
            case 1 :
                return 1;
        }

        // Padrão;
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;


        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_adicionail_zero_um, parent, false);
        return new ZeroUmViewHolder(view);





    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {



        ((ZeroUmViewHolder) holder).setView(adicionais.get(position), getUltimo(position), getItemViewType(position));






    }

    private boolean getUltimo (int position) {
        if ((adicionais.size() - 1) == position) return true;
        else return false;
    }

    @Override
    public int getItemCount() {
        return this.adicionais.size();
    }


    public class ZeroUmViewHolder extends RecyclerView.ViewHolder {
        TextView itemAdicional;
        LinearLayout linearLayout;

        public ZeroUmViewHolder(@NonNull View itemView) {
            super(itemView);

            itemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            linearLayout = itemView.findViewById(R.id.linearlayout_adicionail);

        }

        public void setView (final Adicionais adicionais, boolean ultimo, int viewType) {

            itemAdicional.setText(adicionais.getNomeAd());
            for(final Adicional adicional : adicionais.getAdicionals()){

                LinearLayout horizontal = new LinearLayout(itemView.getContext());
                LinearLayout.LayoutParams horizontalLP = new LinearLayout
                        .LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                horizontal.setLayoutParams(horizontalLP);
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(horizontal);

                LinearLayout vertical = new LinearLayout(itemView.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        WRAP_CONTENT,
                        WRAP_CONTENT,
                        1f);
                int start = PxDp.convertDptoPx(6, itemView.getContext());
                layoutParams.setMargins(start, 0 , 0 , 0);
                layoutParams.gravity = Gravity.CENTER;
                vertical.setLayoutParams(layoutParams);
                vertical.setOrientation(LinearLayout.VERTICAL);
                horizontal.addView(vertical);


                TextView titulo = new TextView(itemView.getContext());
                titulo.setLayoutParams(new LayoutParams(WRAP_CONTENT,
                        WRAP_CONTENT));
                titulo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                titulo.setTypeface(Typeface.create("overpass_light", Typeface.BOLD));
                titulo.setText(adicional.getNomeItem());
                titulo.setTextColor(Color.BLACK);
                vertical.addView(titulo);

                if (!adicional.isGratis()){
                    TextView preco = new TextView(itemView.getContext());
                    preco.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    preco.setText("R$"+adicional.getValorItem());
                    preco.setTextColor(itemView.getContext().getColor(R.color.Cor3));
                    vertical.addView(preco);
                }


                switch (viewType){

                    case 0 :
                        final CheckBox materialCheckBox = new MaterialCheckBox(itemView.getContext());
                        LinearLayout.LayoutParams checkLP = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0);
                        checkLP.gravity = Gravity.CENTER;
                        materialCheckBox.setLayoutParams(checkLP);
                        adicionais.addCheckBoxes(materialCheckBox);
                        horizontal.addView(adicionais.getCheckBoxes().get(adicionais.getAdicionals().indexOf(adicional)));
                        break;

                    case 1 :

                        RadioButton radioButton = new RadioButton(itemView.getContext());
                        LinearLayout.LayoutParams radioLP = new LinearLayout.LayoutParams(WRAP_CONTENT
                                , WRAP_CONTENT
                                , 0);
                        radioLP.gravity = Gravity.CENTER;
                        radioLP.setMargins(0,
                                PxDp.convertDptoPx(8, itemView.getContext()),
                                PxDp.convertDptoPx(16, itemView.getContext()),
                                0);
                        radioButton.setLayoutParams(radioLP);
                        adicionais.addRadioButton(radioButton);
                        horizontal.addView(adicionais.getRadioButtons().get(adicionais.getAdicionals().indexOf(adicional)));
                        break;
                }

                if(ultimo) linearLayout.setPadding(0,0,0, PxDp.convertDptoPx(80, itemView.getContext()));



            }

        }

    }




}
