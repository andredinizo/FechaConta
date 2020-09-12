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

import com.example.fechaconta.Login;
import com.example.fechaconta.R;
import com.example.fechaconta.models.Usuario;
import com.example.fechaconta.utilitys.StringStuff;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Registro2Fragment extends Fragment {

    private final String TAG = "Fragmento Registro 2";
    private TextInputEditText cadastroNome;
    private TextInputEditText cadastroSobrenome;
    private TextInputEditText cadastroCpf;
    private TextInputEditText cadastroCelular;
    private TextInputLayout erroNome;
    private TextInputLayout erroSobrenome;
    private TextInputLayout erroCpf;
    private TextInputLayout erroCelular;
    private boolean checkNome = false;
    private boolean checkSobrenome = false;
    private boolean checkCpf = false;
    private boolean checkCelular = false;
    private boolean permiteErroNome = false;
    private boolean permiteErroCpf = false;
    private boolean permiteerroCelular = false;
    private String stringCpf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro2_fragment,container,false);


        cadastroNome = view.findViewById(R.id.cadastroNome);

        cadastroSobrenome = view.findViewById(R.id.cadastroSobrenome);
        erroNome = view.findViewById(R.id.cadastroNomeErro);
        erroSobrenome = view.findViewById(R.id.cadastroSobrenomeErro);

        cadastroCpf = view.findViewById(R.id.cadastroCpf);
        erroCpf = view.findViewById(R.id.cadastroCpfErro);

        cadastroCelular = view.findViewById(R.id.cadastroCelular);
        erroCelular = view.findViewById(R.id.cadastroCelularErro);

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
                if(before > count) {
                    permiteErroCpf = false;
                    erroCpf.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                StringStuff.editablePutMask(s, "###.###.###-##");
                checkCpf=false;
                if(s.length()==14) {
                    stringCpf = s.toString();
                    if (StringStuff.isCPF(stringCpf, true))
                        checkCpf = true;
                    return;
                }
            }
        });


        cadastroCelular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before > count) {
                    permiteerroCelular = false;
                    erroCelular.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                StringStuff.editablePutMask(s, "(##)#####-####");
                checkCelular= s.length() == 14;
            }
        });


        botaoFinalizar.setOnClickListener(v -> {
            permiteErroNome=true;
            permiteErroCpf=true;
            permiteerroCelular=true;
            atualizaErro();

            if(checkNome && checkSobrenome && checkCpf && checkCelular){

                Log.e(TAG, "Nome:"+ Objects.requireNonNull(cadastroNome.getText()).toString());
                Log.e(TAG, "Sobrenome:"+ Objects.requireNonNull(cadastroSobrenome.getText()).toString());
                Log.e(TAG, "Cpf:"+ Objects.requireNonNull(cadastroCpf.getText()).toString());
                Log.e(TAG, "Telefone:"+ Objects.requireNonNull(cadastroCelular.getText()).toString());

                if(getActivity() instanceof Login)
                    mandaDBactivityLogin();
                else
                    mandaDBactivityCadastro();
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

        if(!checkCelular && permiteerroCelular)
            erroCelular.setError("Telefone inválido");
        else
            erroCelular.setError(null);
    }

    private void mandaDBactivityCadastro(){
        Usuario usuario = new Usuario();
        assert getArguments() != null;
        usuario.setEmail(getArguments().getString("email"));
        usuario.setNome(Objects.requireNonNull(cadastroNome.getText()).toString());
        usuario.setSobrenome(Objects.requireNonNull(cadastroSobrenome.getText()).toString());
        usuario.setCpf(Objects.requireNonNull(cadastroCpf.getText()).toString());
        usuario.setTelefone(Objects.requireNonNull(cadastroCelular.getText()).toString());
        Log.e(TAG, "mandaDBactivityCadastro");

        usuario.criarUsuarioBD(usuario);
    }

    private void mandaDBactivityLogin(){
        Usuario usuario = new Usuario();
        usuario.setNome(Objects.requireNonNull(cadastroNome.getText()).toString());
        usuario.setSobrenome(Objects.requireNonNull(cadastroSobrenome.getText()).toString());
        usuario.setCpf(Objects.requireNonNull(cadastroCpf.getText()).toString());
        usuario.setTelefone(Objects.requireNonNull(cadastroCelular.getText()).toString());
        Log.e(TAG, "mandaDBactivityLogin");

        usuario.criarUsuarioBDpeloFirebase(usuario);
        ((Login) getActivity()).entrar();
    }
}
