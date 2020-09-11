package com.example.fechaconta.models;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

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
public class Adicional {

    /**
     * MUDAR (feito) @isGratis NOS PORXIMOS TEXTES
     * DEIXEI ASSIM SÓ PRA NÃO TER O TRAMPO
     * DE REESCREVER NO BANCO DE DADOS. MAS
     * DEVE FICAR ALGO ASSIM:
     *
     * => Variaveis no Firestore: ====> Exemplo:
     * idxItem      : ordem de relevancia dos itens, escolher a ordem
     * nomeItem     : nome do item. -----> Baccon
     * valorItem    : valor do item. ---> 0.00 ou 5.00
     * gratis       : se é gratis ou não. -> true ou false
     *
     * => Variaveis apenas do Ambiente:
     * @include : determina se o
     * item vai ser contado ou não.
     * @reference : referencia do
     * documento, torna bem mais
     * facil de dar Query depois.
     *
     */

    //FireStore:
    private float   valorItem;
    private String  nomeItem;
    private boolean gratis;

    //Ambiente:
    private boolean include;
    private DocumentReference reference;

    //Construtores
    /**
     * Costrutor Vazio!
     * Usado para dar QUERY.
     */
    public Adicional (){

    }

    /**
     * Recomendado para Instanciar o Objeto.
     *
     * Construtor com os Parametros:
     *
     * Parametros:      Firestore:
     * @param nomeItem  getId().
     * @param valorItem valorItem.
     * @param gratis    isGratis.
     * @param reference getReference()
     */
    public Adicional(String nomeItem, float valorItem, boolean gratis, DocumentReference reference) {
        this.nomeItem = nomeItem;
        this.valorItem = valorItem;
        this.gratis = gratis;
        this.reference = reference;
    }

    // GettersAndSetters
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
        return gratis;
    }

    public void setGratis(boolean isGratis) {
        this.gratis = isGratis;
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

    // => METODOS <=


    /**
     * Soma o valor dos adicionais selecionados
     * de uma lista de ADICONAL's
     *
     * @param adicionals - lista de ADICIONAL
     * @return - Retorna um float referente
     * a soma dos valores dos ADICIONAL's
     * selecionados.
     */
    public static float contaValorCheck(List <Adicional> adicionals){

        // Inicializamos nosso float.
        float total = 0;

        // Percorremos a lista de ADICIONAL's
        for (Adicional adicional : adicionals) {

            // Filtramos os selecionados.
            if (adicional.isInclude()) {

                // Filtramos os pagos.
                if (!adicional.isGratis()) {

                    // Acrecentamos ao nosso total.
                    total = total + adicional.getValorItem();

                }
            }
        }

        return total;
    }






}