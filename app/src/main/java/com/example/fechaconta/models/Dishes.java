package com.example.fechaconta.models;

public class Dishes {

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
    //Getters e Setters


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
