package com.example.fechaconta.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.fechaconta.interfaces.ModelsInterface;
/**
 * -Firestore-
 * nome
 * des
 * ender
 * cidade
 * estado
 * categoria
 * media
 * taval
 * urlheader
 * urlicon
 *
 *
 *
 *  = Funcionalidades relacionadas ao Restaurante=
 *  * Funçoes para facilitar a fluidez do codigo,
 *  centralizando o maximo de call's de metodos a um
 *  tipo de classe.
 *
 */
public class Restaurant {

    //Firestore
    private String /* Id do documento:  */ ID_restaurante;
    private String nome;
    private String des;
    private String ender;
    private String cidade;
    private String estado, categoria;
    private float  media;
    private int    taval;
    private String urlheader;
    private String urlicon;

    //Ambiente
    private DocumentReference restaurantReference;


    public Restaurant(Restaurant restaurant) {

        this.ID_restaurante = restaurant.ID_restaurante;
        this.nome = restaurant.nome;
        this.des = restaurant.des;
        this.ender = restaurant.ender;
        this.cidade = restaurant.cidade;
        this.estado = restaurant.estado;
        this.categoria = restaurant.categoria;
        this.media = restaurant.media;
        this.taval = restaurant.taval;
        this.urlheader = restaurant.urlheader;
        this.urlicon = restaurant.urlicon;
        this.restaurantReference = restaurant.restaurantReference;
    }

    public Restaurant(){};


    //Getters e Setters


    public DocumentReference getRestaurantReference() {
        return restaurantReference;
    }

    public void setRestaurantReference(DocumentReference restaurantReference) {
        this.restaurantReference = restaurantReference;
    }

    public String getID_restaurante() {
        return ID_restaurante;
    }

    public void setID_restaurante(String ID_restaurante) {
        this.ID_restaurante = ID_restaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getEnder() {
        return ender;
    }

    public void setEnder(String ender) {
        this.ender = ender;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int getTaval() {
        return taval;
    }

    public void setTaval(int taval) {
        this.taval = taval;
    }

    public String getUrlheader() {
        return urlheader;
    }

    public void setUrlheader(String urlheader) {
        this.urlheader = urlheader;
    }

    public String getUrlicon() {
        return urlicon;
    }

    public void setUrlicon(String urlicon) {
        this.urlicon = urlicon;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
