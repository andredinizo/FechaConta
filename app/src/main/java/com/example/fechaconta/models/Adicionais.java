package com.example.fechaconta.models;


import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Soneca
 * Referente aos documentos da subcoleção "Adicionais"
 * Os documentos não dever possuir uma Key mas sim um nome
 * referente a este @nomeAd
 *
 * Estrutura FireStore!
 * V Colecton: Dishes
 *      V Document: dishe
 *          => atributos
 *          V Colection: Adicionais
 *              => atributos
 *              V Document: TipodeAdicionais (*) <--- Criaremos esse Objeto!
 *                  => atributos
 *
 *
 *         //Variaveis//
 * nomeAd     => Nome do grupo de adicionais. ---------> (Esta variavel é o id do documento).
 * tipoAd     => tipo de adicionais.
 * limite     => limite de items a serem escolhidos. --> (apenas pro tipo 2)
 * adicionals => lista com os ADICIONAL.
 *
 *          //Tipos//
 * Tipos referece ao Tipo de Escolha
 * do usuario quando aos adicionais dessa
 * Coleção, como se Pode selhesonar mais
 * de um item ou não, se sim até quantos e etc...
 *                                                          SuperTipo de ViewHolder:
 * Tipo 0 ===> Pode escolher dois ou mais.                  (CheckBox)
 * Tipo 1 ===> Pode escolher apenas uma das opções.         (RadioButton)
 *
 * * Por padrão o limite é 0, e significa que é
 * ilimitado o numero de escolhas.
 *
 */
public class Adicionais {
    public static final String TAG = "ADICIONAL";

    /**
     * SE PENSAREM EM NOMES MELHORES E INTUITIVOS
     * REFERENTE A CADA TIPO, SINTAN-SE A VONTADE
     * DE MUDAR.
     */

    public static final  int TIPO_ZERO   = 0;
    public static final  int TIPO_UM     = 1;

    // Firestore
    private String nomeAd;
    private int    limite;
    private int    tipoAd;

    // Ambiente
    private DocumentReference reference;
    private List <Adicional> adicionals;
        // Ui-Stuff
    private List<RadioButton> radioButtons = new ArrayList<>();
    private List<CheckBox> checkBoxes = new ArrayList<>();

    /*  Cosntrutores    */
    public Adicionais (){
    }

    public Adicionais(String nomeAd) {
        this.nomeAd = nomeAd;
    }

    public Adicionais(String nomeAd, int tipoAd) {
        this.nomeAd = nomeAd;
        this.tipoAd = tipoAd;
    }

    /*  Getters & Setters   */

    public List<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public void setRadioButtons(List<RadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    public List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    public List<Adicional> getAdicionals() {
        return adicionals;
    }

    public void setAdicionals(List<Adicional> adicionals) {
        this.adicionals = adicionals;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference reference) {
        this.reference = reference;
    }

    public int getTipoAd() {
        return tipoAd;
    }

    public void setTipoAd(int tipoAd) {
        this.tipoAd = tipoAd;
    }

    public String getNomeAd() {
        return nomeAd;
    }

    public void setNomeAd(String nomeAd) {
        this.nomeAd = nomeAd;
    }

    /**
     * Diz se um adicional pode
     * ser selecionado ou não.
     * ---UTIL MAIS NO CASO 0----
     * @param index - index do adicional a ser verificado na lista.
     * @return - returna true ou false.
     */
    public boolean isCheckable (int index) {

        switch (this.adicionals.get(index).getTipoAd()) {

            case 0 :

                if (this.getLimite() == 0) return true;
                else {

                    int x = 0;
                    for (Adicional adicional : this.adicionals) if(adicional.isInclude()) x++;

                    if (x < this.getLimite()) return true;
                    else return false;

                }

            case 1 :

                for (Adicional adicional : adicionals){
                    if (adicional.isInclude()) return false;
                    else return true;
                }



        }

        return false;

    }

    public void addRadioButton (final RadioButton radioButton) {
        if(radioButtons.size() < adicionals.size()){
            radioButtons.add(radioButton);
            radioButtons.get(radioButtons.indexOf(radioButton)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){

                        for (Adicional adicional : adicionals) adicionals.get(adicionals.indexOf(adicional)).setInclude(false);
                        adicionals.get(radioButtons.indexOf(radioButton)).setInclude(true);
                        for (RadioButton rb : radioButtons) radioButtons.get(radioButtons.indexOf(rb)).setChecked(false);
                        radioButtons.get(radioButtons.indexOf(radioButton)).setChecked(true);

                    }else{

                        radioButtons.get(radioButtons.indexOf(radioButton)).setChecked(false);
                        adicionals.get(radioButtons.indexOf(radioButton)).setInclude(false);

                    }
                }
            });


        }
    }

    public void addCheckBoxes (final CheckBox checkBox) {
        if (this.checkBoxes.size() < this.adicionals.size()){
            this.checkBoxes.add(checkBox);
            checkBoxes.get(checkBoxes.indexOf(checkBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (isCheckable(checkBoxes.indexOf(checkBox))) {

                            checkBoxes.get(checkBoxes.indexOf(checkBox)).setChecked(true);
                            adicionals.get(checkBoxes.indexOf(checkBox)).setInclude(true);

                        }else {

                            checkBoxes.get(checkBoxes.indexOf(checkBox)).setChecked(false);
                            adicionals.get(checkBoxes.indexOf(checkBox)).setInclude(false);

                        }
                    }else {

                        checkBoxes.get(checkBoxes.indexOf(checkBox)).setChecked(false);
                        adicionals.get(checkBoxes.indexOf(checkBox)).setInclude(false);


                    }

                }
            });
        }

    }


}
