package com.example.fechaconta.fragments;

import android.content.Context;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.Login;
import com.example.fechaconta.MainActivity;
import com.example.fechaconta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.EventListener;
import java.util.List;

public class LoginEmailFragment extends Fragment {
    private final String TAG = "LOGIN_EMAIL_FRAGMENT";
    private Button botao_entrar;
    private Button botao_voltar;
    private TextInputLayout lEmail;
    private TextInputLayout lSenha;
    private TextInputEditText email;
    private TextInputEditText senha;
    private FirebaseAuth mAuth;
    private TextInputLayout textInputLayout;
    private boolean checkSenha;
    private boolean checkemail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_email, container, false);

        mAuth =  FirebaseAuth.getInstance();
        email = view.findViewById(R.id.textEmail);
        lEmail = view.findViewById(R.id.editTextTextEmailAddress);
        lSenha = view.findViewById(R.id.editTextTextPassword);
        senha = view.findViewById(R.id.textPassword);
        botao_entrar = view.findViewById(R.id.botao_entrar);

        checkemail = false;
        checkSenha = false;

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().toLowerCase().contains("@") && (s.toString().toLowerCase().endsWith(".com") || s.toString().endsWith(".com.br"))){
                    checkemail = true;
                    if(lEmail.getError() != null){
                        lEmail.setError(null);
                    }
                    if(checkSenha){
                        botao_entrar.setEnabled(true);
                    }
                }
                else{
                    botao_entrar.setEnabled(false);
                    checkemail = false;
                    if(lEmail.getError() == null)
                        lEmail.setError("Email Inaválido!");
                }
            }
        });

        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 10 && s.toString().length() >= 6){
                    checkSenha = true;
                    if(lSenha.getError() != null) {
                        lSenha.setError(null);
                    }
                    if(checkemail) {
                        botao_entrar.setEnabled(true);
                    }
                }
                else{
                    botao_entrar.setEnabled(false);
                    checkSenha = false;
                    if (lSenha.getError() == null){
                        lSenha.setError("Senha deve conter entre 6 a 10 caracteres!");
                    }
                }
            }
        });

        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmailandPassword(email.getText().toString(), senha.getText().toString());
            }
        });


                /*                todo: consertar esse botão ou criar outro método para voltar p/ tella anterior
        //botão voltar troca para fragmento método de login
        botao_voltar = view.findViewById(R.id.botao_voltar);
        botao_voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((Login) getActivity()).trocaFragParaMetodo();
            }
        });     */

        return view;
    }

    private void loginEmailandPassword (String email, String senha){
        if(email != null && senha != null){
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(TAG, "sigInWithEmail:success");
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }else{

                                Log.d(TAG, "sigInWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Fahla ao Autenticar!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

}
