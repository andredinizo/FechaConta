package com.example.fechaconta.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.ActivityRegistro;
import com.example.fechaconta.R;
import com.example.fechaconta.models.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Registro2Fragment extends Fragment {

    private final String TAG = "Fragmento Registro 2";
    private TextInputEditText cadastroNome;
    private TextInputEditText cadastroSobrenome;
    private TextInputEditText cadastroCpf;
    private TextInputEditText cadastroTelefone;
    private TextInputLayout erroNome;
    private TextInputLayout erroSobrenome;
    private TextInputLayout erroCpf;
    private TextInputLayout erroTelefone;
    private boolean checkNome = false;
    private boolean checkSobrenome = false;
    private boolean checkCpf = false;
    private boolean checkTelefone = false;
    private boolean permiteErroNome = false;
    private boolean permiteErroCpf = false;
    private boolean permiteErroTelefone = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro2,container,false);


        cadastroNome = view.findViewById(R.id.cadastroNome);
        cadastroSobrenome = view.findViewById(R.id.cadastroSobrenome);
        cadastroCpf = view.findViewById(R.id.cadastroCpf);
        cadastroTelefone = view.findViewById(R.id.cadastroTelefone);
        erroNome = view.findViewById(R.id.cadastroNomeErro);
        erroSobrenome = view.findViewById(R.id.cadastroSobrenomeErro);
        erroCpf = view.findViewById(R.id.cadastroCpfErro);
        erroTelefone = view.findViewById(R.id.cadastroTelefoneErro);
        Button botaoFinalizar = view.findViewById(R.id.botaoFinalizar);



        cadastroNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s!=null)
                    checkNome = true;

                else
                    checkNome = false;

                atualizaErro();
            }
        });


        cadastroSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s!=null)
                    checkSobrenome = true;

                else
                    checkSobrenome = false;

                atualizaErro();
            }
        });


        cadastroCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                permiteErroNome=true;

                if(s!=null)
                    checkCpf = true;

                else
                    checkCpf = false;

                atualizaErro();
            }
        });


        cadastroTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                permiteErroNome=true;
                permiteErroCpf=true;

                if(s!=null)
                    checkTelefone = true;

                else
                    checkTelefone = false;

                atualizaErro();
            }
        });


        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permiteErroNome=true;
                permiteErroCpf=true;
                permiteErroTelefone=true;
                atualizaErro();

                if(checkNome && checkSobrenome && checkCpf && checkTelefone){

                    Log.e(TAG, "Nome:"+ Objects.requireNonNull(cadastroNome.getText()).toString());
                    Log.e(TAG, "Sobrenome:"+ Objects.requireNonNull(cadastroSobrenome.getText()).toString());
                    Log.e(TAG, "Cpf:"+ Objects.requireNonNull(cadastroCpf.getText()).toString());
                    Log.e(TAG, "Telefone:"+ Objects.requireNonNull(cadastroTelefone.getText()).toString());

                    mandaDB();


                }
            }
        });

        return view;
    }


    private void atualizaErro(){
        if(!checkNome && permiteErroNome)
            erroNome.setError("Campo obrigatório");
        else
            erroNome.setError(null);

        if(!checkSobrenome && permiteErroNome)
            erroSobrenome.setError("Campo obrigatório");
        else
            erroSobrenome.setError(null);

        if(!checkCpf && permiteErroCpf)
            erroCpf.setError("CPF inválido");
        else
            erroCpf.setError(null);

        if(!checkTelefone && permiteErroTelefone)
            erroTelefone.setError("Telefone inválido");
        else
            erroTelefone.setError(null);
    }

    private void mandaDB(){
        Usuario usuario = new Usuario();
        assert getArguments() != null;
        usuario.setEmail(getArguments().getString("email"));
        usuario.setNome(Objects.requireNonNull(cadastroNome.getText()).toString());
        usuario.setSobrenome(Objects.requireNonNull(cadastroSobrenome.getText()).toString());
        usuario.setCpf(Objects.requireNonNull(cadastroCpf.getText()).toString());
        usuario.setTelefone(Objects.requireNonNull(cadastroTelefone.getText()).toString());

        usuario.criarUsuarioBD(usuario);
    }
}
