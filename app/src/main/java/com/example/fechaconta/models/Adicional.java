package com.example.fechaconta.models;

import com.google.firebase.firestore.DocumentReference;

/**
 * @author Soneca
 * Classe referente a Subcoleção ItensAdicionais, da Classe
 *
 * Estrutura FireStore!
 *  v Colecton: Dishes
 *       v Document: dishe
 *           => atributos
 *           v Colection: Adicionais
 *               v Document: TipodeAdicionais
 *                   => atributos
 *                      v Colection: Adicional
 *                          v Document: item (*) <--- Criaremos esse Objeto!
 *                              => Atributos
 *
 *
 */
public class Adicional extends Adicionais {

    private String  nomeItem;
    private float   valorItem;
    private boolean isGratis;
    private boolean include;

    private DocumentReference reference;




    public Adicional(String tipoAdicional) {
        super(tipoAdicional);
    }

    public Adicional (){

    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public float getValorItem() {
        return valorItem;
    }

    public void setValorItem(float valorItem) {
        this.valorItem = valorItem;
    }

    public boolean isGratis() {
        return isGratis;
    }

    public void setGratis(boolean gratis) {
        isGratis = gratis;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference reference) {
        this.reference = reference;
    }
}