package com.example.fechaconta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.utilitys.StringStuff;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


           // APAGAR ESSA ACTIVITY

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
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro1_fragment);
        mAuth = FirebaseAuth.getInstance();
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
                        //abreRegistro2();

                    }else{

                        Toast.makeText(ActivityRegistro.this,"Não foi possível realizar cadastro", Toast.LENGTH_SHORT).show();

                    }
                });
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


    /*private void abreRegistro2(){
        Registro2Fragment registro2Fragment = new Registro2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", Objects.requireNonNull(cadastroEmail.getText()).toString());
        registro2Fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.registroContainer, registro2Fragment)
                .addToBackStack(null).commit();
    }*/


}