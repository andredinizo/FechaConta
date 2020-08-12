package com.example.fechaconta.models;

public class Dishes {

    //Atributos
    private String name;
    private String description;
    private Boolean isVegan;
    private Boolean isVeg;
    private Boolean isGlutenFree;
    private Float value;
    private String category;
    private Float avgrating;
    private int numratings;

    //Getters e Setters

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

    public Boolean getVegan() {
        return isVegan;
    }

    public void setVegan(Boolean vegan) {
        isVegan = vegan;
    }

    public Boolean getVeg() {
        return isVeg;
    }

    public void setVeg(Boolean veg) {
        isVeg = veg;
    }

    public Boolean getGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        isGlutenFree = glutenFree;
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
