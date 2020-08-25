package com.example.fechaconta.models;

import android.util.Log;

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
     * MUDAR @isGratis NOS PORXIMOS TEXTES
     * DEIXEI ASSIM SÓ PRA NÃO TER O TRAMPO
     * DE REESCREVER NO BANCO DE DADOS. MAS
     * DEVE FICAR ALGO ASSIM:
     *
     * => Variaveis no Firestore: ====> Exemplo:
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
    private String  nomeItem;
    private float   valorItem;
    private boolean isGratis;

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
     * @param isGratis    isGratis.
     * @param reference getReference()
     */
    public Adicional(String nomeItem, float valorItem, boolean isGratis, DocumentReference reference) {
        this.nomeItem = nomeItem;
        this.valorItem = valorItem;
        this.isGratis = isGratis;
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

    public boolean isIsGratis() {
        return isGratis;
    }

    public void setIsGratis(boolean isGratis) {
        this.isGratis = isGratis;
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
                if (!adicional.isIsGratis()) {

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

    /**
     * @soneca
     *================ Local Version =================
     *
     * -------------- Descrição ----------------------
     * Verifica se Adicional, é valido ou não
     * para ser selecionado, com base no seu
     * tipo, e em uma lista.
     * -----------------------------------------------
     * ATENÇÃO: Para funcionar corretamente tem que
     * setar o @include no adicional, quando
     * ele ser ativado, e apenas se.
     * -----------------------------------------------
     * @param adicionals - Lista de Adicionais que o
     *                   Adicional verificado pertence.
     *
     * @return - Retorna se o Adicional pode ou não
     *           ser selecionado.
     *           false = não pode.
     *           true = pode.
     *
     */
    public boolean verificarCheckB(List <Adicional> adicionals) {

        //No caso do Tipo For
        switch (this.getTipoAd()) {

            // 0 - Pode Escolher quantos quiser.
            case 0:
                //Retornamos true, já que neste caso não
                //há regras.
                return true;

            // 1 - Pode Escolher apenas uma opção.
            case 1:

                // Nossa confirmação a ser retornada.
                // Acontece que não podemos retornar dentro do
                // FOR pois senão ele retornara antes de concluir o loop.
                boolean cheked = false;

                //Passa pela lista de Acionais
                for (Adicional adFromList : adicionals) {

                    // Filtra apenas o que são do mesmo tipo.
                    if (adFromList.getNomeAd().equals(this.getNomeAd()))

                        // Tiramos o ADICIONAL que estamos verificando.
                        if(!adFromList.getNomeItem().equals(this.getNomeItem()))

                            // Se algum do mesmo tipo tiver sido ativado
                            if(adFromList.include)

                                //Retornamos falso.
                                cheked = false;

                            else

                                //Caso Contrário esse ADICIONAL pode ser Ativado.
                                cheked = true;

                }

                return cheked;
            // 2 - Pode Escolher um numero limitado de Items.
            case 2:

                // Nosso Contador pra saber quantos foram
                // selecionados.
                int contador = 0;

                // Passar pela lista de ADICIONAL's
                for (Adicional adFromList: adicionals) {

                    // Filtrar apenas os do mesmo tipo.
                    if(adFromList.getNomeAd().equals(this.getNomeAd()))

                        // Tiramos o ADICIONAL que estamos verificando da lista.
                        if(!adFromList.getNomeItem().equals(this.getNomeItem()))

                            // Filtramos os selecionados.
                            if(adFromList.include)

                                // Acrescentamos ao contador.
                                contador++;


                }

                // Verificamos se o contador é menor
                // que o numero limite de solucionados.
                if(contador < this.getLimite())

                    // Se é menor pode ser acionado.
                    return true;

                else

                    // Caso contrário não pode.
                    return false;


        }

        // Caso nehum caso se Sastifaça
        // avisamos o sistema que o tipo está mal setado,
        Log.d(TAG, "configurarTipo: -------------- Tipo Inválido --------------");

        // e retornamos por padrão false.
        return false;
    }


}