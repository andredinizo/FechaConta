package com.example.fechaconta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Iniciar();

        //getSupportFragmentManager().beginTransaction().add(R.id.FragContainer) //add classe da frag
    }

    //Inicia Atividade Entrar
    public void Iniciar() {
        Intent intent = new Intent(
                Login.this, MainActivity.class
        );
        startActivity(intent);
        finish();

    }
}