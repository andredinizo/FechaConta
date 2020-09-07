package com.example.fechaconta.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class Mesa {

    private int estado;
    private String nu_mesa;
    private List<String> user_id;
    private int vagas;

    public Mesa() {
        //Public Constructor
    }



    public Mesa(String urlMesa) {

        String restauranteId;
        String mesaId;
        String[] split;


        split = urlMesa.split("//urlmesa//");


        if (split.length == 2) {
            restauranteId = split[0];
            mesaId = split[1];



        }
    }


    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNu_mesa() {
        return nu_mesa;
    }

    public void setNu_mesa(String nu_mesa) {
        this.nu_mesa = nu_mesa;
    }

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
}
