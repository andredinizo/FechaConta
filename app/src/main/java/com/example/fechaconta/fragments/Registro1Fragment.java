package com.example.fechaconta.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.Login;
import com.example.fechaconta.R;
import com.example.fechaconta.utilitys.StringStuff;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Registro1Fragment extends Fragment {

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
    private FirebaseAuth mAuth;

    @Nullable
    @Override                                       //adequar layout dessa classe (nome do layout
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro1_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        cadastroEmail = view.findViewById(R.id.cadastroEmail);
        cadastroSenha = view.findViewById(R.id.cadastroSenha);
        cadastroRepeteSenha = view.findViewById(R.id.cadastroRepeteSenha);
        erroEmail = view.findViewById(R.id.cadastroEmailErro);
        erroSenha = view.findViewById(R.id.cadastroSenhaErro);
        erroRepeteSenha = view.findViewById(R.id.cadastroRepeteSenhaErro);
        Button botaoProximo = view.findViewById(R.id.botao_proximo);


        cadastroEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before > count) {
                    permiteErroEmail = false;
                    permiteErroRepeteSenha = false;
                    atualizaErro();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                email=s.toString();
                checkEmail= StringStuff.isEmail(email);
                atualizaErro();
            }
        });


        cadastroSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before > count) {
                    permiteErroSenha = false;
                    atualizaErro();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                permiteErroEmail=true;
                checkSenha = s.toString().length() <= 20 && s.toString().length() >= 6;

                atualizaErro();
            }
        });

        cadastroRepeteSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before > count) {
                    permiteErroRepeteSenha = false;
                    atualizaErro();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                permiteErroEmail=true;
                permiteErroSenha=true;

                checkRepeteSenha = Objects.requireNonNull(cadastroRepeteSenha.getText()).toString()
                        .equals(Objects.requireNonNull(cadastroSenha.getText()).toString());

                atualizaErro();
            }
        });


        botaoProximo.setOnClickListener(v -> {
            permiteErroEmail=true;
            permiteErroSenha=true;
            permiteErroRepeteSenha=true;
            atualizaErro();


            if(checkEmail && checkSenha && checkRepeteSenha){


                email= Objects.requireNonNull(cadastroEmail.getText()).toString().toLowerCase();
                senha= Objects.requireNonNull(cadastroSenha.getText()).toString();

                mAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        //Deu certo
                        mAuth.signInWithEmailAndPassword(email,senha);
                        ((Login) Objects.requireNonNull(getActivity())).trocaFragParaRegistro2(email);

                    }else{

                        Toast.makeText(getContext(),"Não foi possível realizar cadastro", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        return view;
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
}
