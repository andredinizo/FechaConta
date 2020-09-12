package com.example.fechaconta.models;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * todo replace : Adicionais = GrupoAdicionais
 * todo aninhar as classes
 * todo adicionar a uma lista de include o adicional incluso ao inves de @calcTotal
 * * public static float calcTotal (): Calcula o preço final do prato (Adicionais).
 * * public static List <Adicionais> getIncludeAicionaisList () : Retorna a lista de Adicionais, com apenas os Adicional's incluso
 * public void adicionarComanda (int x) : Adiciona na comanda, a quantidade x deste prato.
 * <p>
 * FireBase
 * - name
 * - description
 * - isVegan
 * - isVeg
 * - isGlutenFree
 * - value
 * - category
 * - avgrating
 * - numratings
 * - isHighlight
 * - urlImagem
 */
public class Dishes extends Restaurant {

    //Atributos
    private String ID;
    private String name;
    private String description;
    private int isVegan;
    private int isVeg;
    private int isGlutenFree;
    private Float value;
    private String category;
    private Float avgrating;
    private int numratings;
    private int isHighlight;
    private String urlImagem;

    public Dishes() {
    }

    //RealTime Variables
    private int statusPedido;
    private List<String> dividirCom;

    //Ambiente
    private DocumentReference reference;
    private List<Adicionais> adicionais;
    private int quantidade = 1;


    //Métodos
    public void adicionarComanda() {
        if (Usuario.CheckIn.verificaCheckIn())
            if (Usuario.CheckIn.getInstance().getRestaurante().equals(super.getID_restaurante()))
                Usuario.CheckIn.adicionarPratoComanda(this);
    }

    /**
     * Acrescenta ou diminui a qunatidade de itens
     * use as duas constantes a seguir para selecionar
     * qual ação será executada
     */
    public static final int /** Adiciona Qunatidade ++ */
            ADICIONAR = 0;
    public static final int /** Retira a Quantidade -- */
            RETIRAR = 1;

    /**
     * apenas quantidade > 1
     *
     * @param addOrRetirar 0 Adiciona
     *                     1 Retira
     */
    public void altQuantidade(int addOrRetirar) {
        switch (addOrRetirar) {
            case 0:
                quantidade++;
                break;
            case 1:
                if (quantidade > 1) quantidade--;
                break;
        }
    }


    /**
     * Calcula o total, segundo o valor do prato,
     * e os adicionais selecionados, e multiplica
     * tudo pela quantidade deste prato que queremos.
     *
     * @return - Retorna o valor total.
     */
    public float calcularTotal() {

        // Total a ser acrescentado.
        float total = this.getValue();

        // Percorremos nossa lista de GruposAdicionais
        for (Adicionais adicionais : this.getAdicionais()) { /*Perceba que como usamos a mesma instância de Dishes,
                                                                    quando mexemos no prato dentro do adapter, mexemos no
            //Percorremos as listas de Adicionais                         no do ItemFragment tbm, já que ambos tem a mesma istância.*/
            for (Adicional adicional : adicionais.getAdicionals()) {

                // Pegamos apenas os Inclusos
                if (adicional.isInclude())
                    // Se não forem Gratis
                    if (!adicional.isGratis())
                        // Acresntamos ao total o valor do item;
                        total = total + adicional.getValorItem();

            }

        }

        // Retornamos o total (Valor do prato + Adicionais) multiplicado pela quantidade.
        return total * quantidade;
    }


    //Getters e Setters

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference restaurantId) {
        this.reference = restaurantId;
    }

    public List<Adicionais> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<Adicionais> adicionais) {
        this.adicionais = adicionais;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsVegan() {
        return isVegan;
    }

    public void setIsVegan(int isVegan) {
        this.isVegan = isVegan;
    }

    public int getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(int isVeg) {
        this.isVeg = isVeg;
    }

    public int getIsGlutenFree() {
        return isGlutenFree;
    }

    public void setIsGlutenFree(int isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }

    public int getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(int isHighlight) {
        this.isHighlight = isHighlight;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(Float avgrating) {
        this.avgrating = avgrating;
    }

    public int getNumratings() {
        return numratings;
    }

    public void setNumratings(int numratings) {
        this.numratings = numratings;
    }

    public int getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(int statusPedido) {
        this.statusPedido = statusPedido;
    }

    public List<String> getDividirCom() {
        return dividirCom;
    }

    public void setDividirCom(List<String> dividirCom) {
        this.dividirCom = dividirCom;
    }
}
