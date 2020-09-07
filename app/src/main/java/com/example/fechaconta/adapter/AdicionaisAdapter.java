package com.example.fechaconta.adapter;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.List;

import static android.view.ViewGroup.*;
import static android.view.ViewGroup.LayoutParams.*;
import static com.example.fechaconta.R.color.radio_check_button;
import static com.example.fechaconta.R.id.cardview_item;
import static org.xmlpull.v1.XmlPullParser.START_DOCUMENT;

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
        TextView         itemAdicional;
        TextView         limiteItemAdicional;
        LinearLayout     linearLayout;
        MaterialCardView obrigatorioCardView;


        public ZeroUmViewHolder(@NonNull View itemView) {
            super(itemView);

            itemAdicional       = itemView.findViewById(R.id.textview_item_adicional);
            linearLayout        = itemView.findViewById(R.id.linearlayout_adicionail);
            limiteItemAdicional = itemView.findViewById(R.id.textview_item_adicional_limite);
            obrigatorioCardView = limiteItemAdicional.findViewById(cardview_item);

        }

        public void setView (final Adicionais adicionais, boolean ultimo, int viewType) {

            itemAdicional.setText(adicionais.getNomeAd());
            adicionais.attTextLimite(limiteItemAdicional);

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
                titulo.setTypeface(Typeface.create("overpass_light", Typeface.NORMAL));
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
                        final MaterialCheckBox checkBox = new MaterialCheckBox(itemView.getContext());
                        LinearLayout.LayoutParams checkLP = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0);
                        checkLP.gravity = Gravity.CENTER;
                        checkBox.setLayoutParams(checkLP);
                        checkBox.setButtonTintList(itemView.getContext().getColorStateList(radio_check_button));
                        adicionais.addCheckBoxes(checkBox, limiteItemAdicional);
                        horizontal.addView(adicionais.getCheckBoxes().get(adicionais.getAdicionals().indexOf(adicional)));
                        break;

                    case 1 :

                        MaterialRadioButton radioButton = new MaterialRadioButton(itemView.getContext());
                        LinearLayout.LayoutParams radioLP = new LinearLayout.LayoutParams(WRAP_CONTENT
                                , WRAP_CONTENT
                                , 0);
                        radioLP.gravity = Gravity.CENTER;
                        radioLP.setMargins(0,
                                PxDp.convertDptoPx(8, itemView.getContext()),
                                0,
                                0);
                        radioButton.setLayoutParams(radioLP);
                        radioButton.setButtonTintList(itemView.getContext().getColorStateList(radio_check_button));
                        adicionais.addRadioButton(radioButton, limiteItemAdicional);
                        horizontal.addView(adicionais.getRadioButtons().get(adicionais.getAdicionals().indexOf(adicional)));
                        break;
                }

                //if(ultimo) linearLayout.setPadding(0,0,0, PxDp.convertDptoPx(80, itemView.getContext()));



            }

        }

    }




}
