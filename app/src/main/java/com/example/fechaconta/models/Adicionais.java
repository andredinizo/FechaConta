package com.example.fechaconta.models;


import com.google.firebase.firestore.DocumentReference;

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
 * nomeAd => Nome do grupo de adicionais. ---------> (Esta variavel é o id do documento).
 * tipoAd => tipo de adicionais.
 * limite => limite de items a serem escolhidos. --> (apenas pro tipo 2)
 *
 *          //Tipos//
 * Tipos referece ao Tipo de Escolha
 * do usuario quando aos adicionais dessa
 * Coleção, como se Pode selhesonar mais
 * de um item ou não, se sim até quantos e etc...
 *                                                          SuperTipo de ViewHolder:
 * Tipo 0 ===> Pode escolher quantos quiser.                (CheckBox)
 * Tipo 1 ===> Pode escolher apenas uma das opções.         (RadioButton)
 * Tipo 2 ===> Pode escolher um numero limitado de itens.   (CheckBox)
 *
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
    public static final  int TIPO_DOIS   = 2;



    private String nomeAd;
    private int    limite;
    private int    tipoAd;

    private DocumentReference reference;

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




}
