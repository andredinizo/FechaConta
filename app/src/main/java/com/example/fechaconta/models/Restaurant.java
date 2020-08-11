package com.example.fechaconta.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Restaurant {

    //Atributos
    private String ID_restaurant;
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
    private String avgRating;
    private String totalRating;
    private String urlheader;
    private String urlIcon;

    //Getters e Setters

    public String getID_restaurant() {
        return ID_restaurant;
    }

    public void setID_restaurant(String ID_restaurant) {
        this.ID_restaurant = ID_restaurant;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getUrlheader() {
        return urlheader;
    }

    public void setUrlheader(String urlheader) {
        this.urlheader = urlheader;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

}
