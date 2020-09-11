package com.example.fechaconta.models;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * todo replace : Adicionais = GrupoAdicionais
 * * public static float calcTotal (): Calcula o preço final do prato (Adicionais).
 * * public static List <Adicionais> getIncludeAicionaisList () : Retorna a lista de Adicionais, com apenas os Adicional's incluso
 * public void adicionarComanda (int x) : Adiciona na comanda, a quantidade x deste prato.
 */
public class Dishes{

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
    private DocumentReference reference;

    //Ambiente
    private List<Adicionais> adicionais;
    private  int quantidade = 1;


    //Métodos
    public void adicionarComanda (){

    }

    /**
     *
     */
    public static final int ADICIONAR = 0;
    public static final int RETIRAR   = 1;
     /**
     * @param addOrRetirar 0 Adiciona
     *                     1 Retira
     */
    public void altQuantidade (int addOrRetirar) {
        switch (addOrRetirar){
            case 0 :
                quantidade ++;
                break;
            case 1 :
                if(quantidade > 1) quantidade --;
                break;
        }
    }



    public float calcularTotal() {

        float total = this.getValue();

        for(Adicionais adicionais : this.getAdicionais()) {

            for(Adicional adicional : adicionais.getAdicionals()){

                if (adicional.isInclude())
                    if (!adicional.isGratis())
                        total = total + adicional.getValorItem();

            }

        }

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


}
