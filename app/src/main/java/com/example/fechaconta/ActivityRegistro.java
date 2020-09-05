package com.example.fechaconta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.fragments.Registro2Fragment;
import com.example.fechaconta.models.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ActivityRegistro extends AppCompatActivity {

    private final String TAG = "Activity registro";
    private TextInputEditText cadastroEmail;
    private TextInputEditText cadastroSenha;
    private TextInputEditText cadastroRepeteSenha;
    private TextInputLayout erroEmail;
    private TextInputLayout erroSenha;
    private TextInputLayout erroRepeteSenha;
    private String email;
    private String senha;
    private boolean checkEmail = false;
    private boolean checkSenha = false;
    private boolean checkRepeteSenha = false;
    private boolean permiteErroEmail = false;
    private boolean permiteErroSenha = false;
    private boolean permiteErroRepeteSenha = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        cadastroEmail = findViewById(R.id.cadastroEmail);
        cadastroSenha = findViewById(R.id.cadastroSenha);
        cadastroRepeteSenha = findViewById(R.id.cadastroRepeteSenha);
        erroEmail = findViewById(R.id.cadastroEmailErro);
        erroSenha = findViewById(R.id.cadastroSenhaErro);
        erroRepeteSenha = findViewById(R.id.cadastroRepeteSenhaErro);
        Button botaoProximo = findViewById(R.id.botao_proximo);


        cadastroEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().contains("@") && s.toString().contains(".")){
                    checkEmail = true;
                }

                else{
                    checkEmail = false;
                    if(Objects.requireNonNull(cadastroEmail.getText()).toString().length()==0) {
                        permiteErroEmail = false;
                        permiteErroSenha = false;
                        permiteErroRepeteSenha = false;
                    }
                }

                atualizaErro();
            }
        });

        cadastroSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                permiteErroEmail=true;

                if (s.toString().length() <= 20 && s.toString().length() >= 6){
                    checkSenha = true;
                }

                else{
                    checkSenha = false;
                    if(Objects.requireNonNull(cadastroSenha.getText()).toString().length()==0) {
                        permiteErroSenha = false;
                        permiteErroRepeteSenha = false;
                    }
                }

                atualizaErro();
            }
        });

        cadastroRepeteSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                permiteErroEmail=true;
                permiteErroSenha=true;

                checkRepeteSenha = Objects.requireNonNull(cadastroRepeteSenha.getText()).toString()
                        .equals(Objects.requireNonNull(cadastroSenha.getText()).toString());

                if(cadastroRepeteSenha.getText().toString().length()==0) {
                    permiteErroRepeteSenha = false;
                }

                atualizaErro();
            }
        });


        botaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permiteErroEmail=true;
                permiteErroSenha=true;
                permiteErroRepeteSenha=true;
                atualizaErro();


                if(checkEmail && checkSenha && checkRepeteSenha){


                    email=cadastroEmail.getText().toString().toLowerCase();
                    senha=cadastroSenha.getText().toString();

                    abreRegistro2();
                }
            }
        });
    }

    private void atualizaErro(){
        if(!checkEmail && permiteErroEmail)
            erroEmail.setError("Email inválido");
        else
            erroEmail.setError(null);

        if(!checkSenha && permiteErroSenha)
            erroSenha.setError("Senha deve ter entre 6 e 20 caracteres");
        else
            erroSenha.setError(null);

        if(!checkRepeteSenha && permiteErroRepeteSenha)
            erroRepeteSenha.setError("As senhas estão diferentes");
        else
            erroRepeteSenha.setError(null);
    }


    private void abreRegistro2(){
        Registro2Fragment registro2Fragment = new Registro2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("email",cadastroEmail.getText().toString());
        registro2Fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.registroContainer, registro2Fragment)
                .addToBackStack(null).commit();
    }


}