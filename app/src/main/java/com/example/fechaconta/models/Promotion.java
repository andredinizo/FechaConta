package com.example.fechaconta.models;

import com.example.fechaconta.interfaces.ModelsInterface;

public class Promotion {

    //Atributos
    private String nome
            , url;
    private boolean ativa;
    private int relevancia;

    //Construtores
    public Promotion() {
    }

    public Promotion(String nome, String url) {
        this.nome = nome;
        this.url = url;
    }

    // Getter and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
