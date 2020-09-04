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
public class Adicional extends Adicionais {

    /**
     * MUDAR (feito) @isGratis NOS PORXIMOS TEXTES
     * DEIXEI ASSIM SÓ PRA NÃO TER O TRAMPO
     * DE REESCREVER NO BANCO DE DADOS. MAS
     * DEVE FICAR ALGO ASSIM:
     *
     * => Variaveis no Firestore: ====> Exemplo:
     * @relevIt : ordem de relevancia dos itens, escolher a ordem
     * @nomeItem : nome do item. -----> Baccon
     * @valorItem : valor do item. ---> 0.00 ou 5.00
     * @gratis : se é gratis ou não. -> true ou false
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

    /**
     * Conta os item selecionados de
     * uma mesmo ADICIONAIS.
     *
     * @param adicionals - Lista de adicionais a ser contado.
     * @return - Retorna um inteiro referente
     * ao numero de ADICIONAL's selecionados de um mesmo
     * ADICIONAIS.
     */
    public int contaCheck(List <Adicional> adicionals) {

        // Nosso total a ser retornado
        int total = 0;

        // Passamos por todos os ADICIONAL's
        for (Adicional adicional : adicionals){

            // Filtramos os do mesmo ADICIONAIS
          if (this.getNomeAd().equals(adicional.getNomeAd()))

              // Pra cada ADICIONAL que tiver selecionado
              if (adicional.isInclude())

                  // Acrescentamos ao total.
                  total ++;

        }

        // E retonamos o Total de selecionados
        // se um mesmo ADICIONAIS
        return total;

    }


}