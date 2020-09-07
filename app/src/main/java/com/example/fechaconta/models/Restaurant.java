package com.example.fechaconta.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Restaurant {

    //Atributos
    private String ID_restaurante;
    private String nome;
    private String des;
    private String ender;
    private String cidade;
    private String estado, categoria;
    private float media;
    private int taval;
    private String urlheader;
    private String urlicon;




    public Restaurant(){};

    //Getters e Setters

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
