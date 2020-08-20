package com.example.fechaconta.utilitys;

import com.example.fechaconta.interfaces.Observador;

import java.util.ArrayList;
import java.util.List;

public class MyListener {
    private boolean gatilho;
    private final List<Observador> zoiudos = new ArrayList<Observador>();

    private void notificarObservadores () {
        for(Observador observador : zoiudos){
            observador.notificar(this);
        }
    }

    public void adicionarObservador (Observador obs) {
        zoiudos.add(obs);
    }
    public void removerObservadore (Observador obs) {
        zoiudos.remove(obs);
    }
    public void setGatilho (Boolean gatilho){
        this.gatilho = gatilho;
        notificarObservadores();
    }

}
