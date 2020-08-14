package com.example.fechaconta.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.Login;
import com.example.fechaconta.R;

public class LoginEmailFragment extends Fragment {

    private Button botao_entrar;
    private Button botao_voltar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_email, container, false);


        //botão voltar troca para fragmento método de login
        botao_voltar = view.findViewById(R.id.botao_voltar);
        botao_voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((Login) getActivity()).trocaFragParaMetodo();
            }
        });

        return view;
    }

}
