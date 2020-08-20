package com.example.fechaconta.interfaces;

import com.example.fechaconta.utilitys.MyListener;

public interface Observador {
    public void notificar(MyListener myListener);
}
