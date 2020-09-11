package com.example.fechaconta.models;


import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.fechaconta.utilitys.StringStuff;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
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
 *         //Variaveis Firestore//
 * nomeAd     => Nome do grupo de adicionais. ---------> (Esta variavel é o id do documento).
 * tipoAd     => Tipo de adicionais.
 * limite     => Limite de items a serem escolhidos. --> (apenas pro tipo 2)
 * index      => Ordem de exibição. -------------------> (apenas no Firestore)
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



    /* Firestore */
    private String /* Id do documento: */ nomeAd;
    private int    limite;
    private int    tipoAd;
        /* Just Firestore
        private int index;    */

    /* Ambiente */
    private DocumentReference reference;
    private List <Adicional> adicionals;
    /*  UI - Stuff */
        private List<MaterialRadioButton> radioButtons = new ArrayList<>();
        private List<MaterialCheckBox> checkBoxes = new ArrayList<>();

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

    public List<MaterialRadioButton> getRadioButtons() {
        return radioButtons;
    }

    public void setRadioButtons(List<MaterialRadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    public List<MaterialCheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<MaterialCheckBox> checkBoxes) {
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
     * @return - returna true ou false.
     */
    public boolean isCheckable () {

        switch (this.getTipoAd()) {

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


    /**
     * Att os dados de limite
     * em um text view.
     * @param textView - TextView Limite
     */
    public void attTextLimite (TextView textView) {

        int i = 0;
        for (Adicional adicional : this.adicionals ) if(adicional.isInclude()) i++;

        switch (this.getTipoAd()){
            case 0 :
                if (this.getLimite() == 0) textView.setText("Escolha " + i +"/-");
                else textView.setText("Escolha " + i + "/"+ this.getLimite());
                break;
            case 1 :
                textView.setText("Escolha " + i + "/" + 1);
                break;
        }
    }

    /**
     * Adiciona um RadioButton a uma Lista
     * já configurando, tanto seus listeners,
     * quanto o comportamento deles, segundo o
     * tipo de ADICIONAIS. E chama attTextLimite().
     * @param radioButton - radioButton a ser setado.
     * @param dinamicLimitText - textView Referente ao Limite.
     */
    public void addRadioButton (final MaterialRadioButton radioButton, TextView dinamicLimitText,TextView precosTotal, Dishes dishes )  {
        if(radioButtons.size() < adicionals.size()){
            radioButtons.add(radioButton);
            radioButtons.get(radioButtons.indexOf(radioButton)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){

                        for (int i = 0; i < adicionals.size(); i++)
                            adicionals.get(i).setInclude(false);

                        adicionals.get(radioButtons.indexOf(radioButton)).setInclude(true);

                        for (int i = 0; i < radioButtons.size(); i++)
                            radioButtons.get(i).setChecked(false);

                        radioButtons.get(radioButtons.indexOf(radioButton)).setChecked(true);


                    }else{

                        radioButtons.get(radioButtons.indexOf(radioButton)).setChecked(false);
                        adicionals.get(radioButtons.indexOf(radioButton)).setInclude(false);

                    }


                    attTextLimite(dinamicLimitText);
                    attTotalPreços(precosTotal, dishes);
                }



            });


        }
    }




    public void attTotalPreços(TextView textView, Dishes dishes){

        textView.setText(StringStuff.converterString(dishes.calcularTotal(), StringStuff.FORMATAR_VALOR));


    }

    /**
     * Adiciona um checkBox a uma Lista
     * já configurando, tanto seus listeners,
     * quanto o comportamento deles, segundo o
     * tipo de ADICIONAIS. E chama attTextLimite().
     * @param checkBox - checkBox a ser setado.
     * @param dinamicLimitText - textView Referente ao Limite.
     */
    public void addCheckBoxes (final MaterialCheckBox checkBox, TextView dinamicLimitText, TextView precosTotal, Dishes dishes) {
        if (this.checkBoxes.size() < this.adicionals.size()){
            this.checkBoxes.add(checkBox);
            checkBoxes.get(checkBoxes.indexOf(checkBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (isCheckable()) {

                            checkBoxes.get(checkBoxes.indexOf(checkBox)).setChecked(true);
                            adicionals.get(checkBoxes.indexOf(checkBox)).setInclude(true);

                            int i = 0;
                            for (Adicional adicional : adicionals) if(adicional.isInclude()) i++;
                            if(i == getLimite())
                                for (MaterialCheckBox checkBox1 : checkBoxes)
                                    if(!checkBox1.isChecked()) checkBoxes
                                            .get(checkBoxes
                                            .indexOf(checkBox1))
                                            .setEnabled(false);

                        }else {

                            checkBoxes.get(checkBoxes.indexOf(checkBox)).setChecked(false);
                            adicionals.get(checkBoxes.indexOf(checkBox)).setInclude(false);

                        }
                    }else {

                        checkBoxes.get(checkBoxes.indexOf(checkBox)).setChecked(false);
                        adicionals.get(checkBoxes.indexOf(checkBox)).setInclude(false);

                        for(MaterialCheckBox checkBox1 : checkBoxes)
                            checkBoxes.get(checkBoxes.indexOf(checkBox1)).setEnabled(true);


                    }

                    attTextLimite(dinamicLimitText);
                    attTotalPreços(precosTotal, dishes);

                }
            });
        }

    }


}
