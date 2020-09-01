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
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.models.Adicionais;
import com.example.fechaconta.models.Adicional;
import com.example.fechaconta.utilitys.PxDp;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

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

        switch (viewType) {

            case 0 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_adicionail_zero, parent, false);
                return new ZeroViewHolder(view);

            case 1 :
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_adicional_um, parent, false);
                return new UmViewHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)){

            case 0 :

                ((ZeroViewHolder) holder)
                        .setView(adicionais.get(position),
                                adicionais.get(position).getNomeAd());
                break;

            case 1 :

                ((UmViewHolder) holder)
                        .setView(adicionais.get(position),
                        adicionais.get(position).getNomeAd());
                break;
        }





    }

    @Override
    public int getItemCount() {
        return this.adicionais.size();
    }


    public class ZeroViewHolder extends RecyclerView.ViewHolder {
        TextView itemAdicional;
        LinearLayout linearLayout;

        public ZeroViewHolder(@NonNull View itemView) {
            super(itemView);

            itemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            linearLayout = itemView.findViewById(R.id.linearlayout_adicionail);

        }

        public void setView (final Adicionais adicionais, String nomeAd) {

            itemAdicional.setText(nomeAd);
            for(final Adicional adicional : adicionais.getAdicionals()){

                LinearLayout horizontal = new LinearLayout(itemView.getContext());
                horizontal.setLayoutParams(new LinearLayout
                      .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(horizontal);

                LinearLayout vertical = new LinearLayout(itemView.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f);
                int start = PxDp.convertDptoPx(6, itemView.getContext());
                layoutParams.setMargins(start, 0 , 0 , 0);
                layoutParams.gravity = Gravity.CENTER;
                vertical.setLayoutParams(layoutParams);
                vertical.setOrientation(LinearLayout.VERTICAL);
                horizontal.addView(vertical);


                TextView titulo = new TextView(itemView.getContext());
                titulo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                titulo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                titulo.setTypeface(Typeface.create("overpass_light", Typeface.BOLD));
                titulo.setText(adicional.getNomeItem());
                titulo.setTextColor(Color.BLACK);
                vertical.addView(titulo);

                if (adicional.isGratis()){
                    TextView preco = new TextView(itemView.getContext());
                    preco.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    preco.setText("R$"+adicional.getValorItem());
                    preco.setTextColor(itemView.getContext().getColor(R.color.Cor3));
                    vertical.addView(preco);
                }

                final CheckBox materialCheckBox = new MaterialCheckBox(itemView.getContext());
                TableLayout.LayoutParams layoutParams1 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT
                        , 0);
                layoutParams1.gravity = Gravity.CENTER;
                materialCheckBox.setGravity(Gravity.CENTER);
                adicionais.addCheckBoxes(materialCheckBox);
                horizontal.addView(adicionais.getCheckBoxes().get(adicionais.getAdicionals().indexOf(adicional)));




            }

        }

    }

    public class UmViewHolder extends RecyclerView.ViewHolder {
        TextView itemAdicional;
        RadioGroup radioGroup;

        public UmViewHolder(@NonNull View itemView) {
            super(itemView);

            itemAdicional = itemView.findViewById(R.id.textview_item_adicional);
            radioGroup = itemView.findViewById(R.id.radiogroup_adicional);

        }

        public void setView (Adicionais adicionais, String nomeAd) {

            itemAdicional.setText(nomeAd);

            for(Adicional adicional : adicionais.getAdicionals()){
                LinearLayout horizontal = new LinearLayout(itemView.getContext());
                horizontal.setLayoutParams(new LinearLayout
                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                radioGroup.addView(horizontal);

                LinearLayout vertical = new LinearLayout(itemView.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f);
                int start = PxDp.convertDptoPx(6, itemView.getContext());
                layoutParams.setMargins(start, 0 , 0 , 0);
                layoutParams.gravity = Gravity.CENTER;
                vertical.setLayoutParams(layoutParams);
                vertical.setOrientation(LinearLayout.VERTICAL);
                horizontal.addView(vertical);


                TextView titulo = new TextView(itemView.getContext());
                titulo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
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

                RadioButton radioButton = new RadioButton(itemView.getContext());
                TableLayout.LayoutParams layoutParams1 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT
                        , 0);
                layoutParams1.gravity = Gravity.CENTER;
                radioButton.setGravity(Gravity.CENTER);
                adicionais.addRadioButton(radioButton);
                horizontal.addView(adicionais.getRadioButtons().get(adicionais.getAdicionals().indexOf(adicional)));

            }

        }
    }


}
