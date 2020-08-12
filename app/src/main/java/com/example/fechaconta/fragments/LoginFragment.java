package com.example.fechaconta.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.Login;
import com.example.fechaconta.MainActivity;
import com.example.fechaconta.R;

public class LoginFragment extends Fragment {

    public Button botao_google;
    public Button botao_facebook;
    public Button botao_login_email;
    public Button botao_desenvolvedor;
    public Button botao_criar_conta;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login_metodo, container,false);

        botao_desenvolvedor = view.findViewById(R.id.botao_desenvolvedor);
        botao_desenvolvedor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entrar();
            }
        });

        return view;
    }

        //fução de entrar na main activity
    public void entrar() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

}
