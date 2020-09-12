package com.example.fechaconta.models;

import java.util.List;

public class ItemComanda {

    private String ID;
    private List<Adicional> includeAdicional;
    private int quantidade;
    private int statusPedid;
    private float total;
    private int value;
    private String nomePrato;


    public ItemComanda(){} //CONSTRUTOR VAZIO P/FIREBASE


    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Adicional> getIncludeAdicional() {
        return includeAdicional;
    }

    public void setIncludeAdicional(List<Adicional> includeAdicional) {
        this.includeAdicional = includeAdicional;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getStatusPedid() {
        return statusPedid;
    }

    public void setStatusPedid(int statusPedid) {
        this.statusPedid = statusPedid;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
