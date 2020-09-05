package com.example.fechaconta.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.ActivityRegistro;
import com.example.fechaconta.Login;
import com.example.fechaconta.MainActivity;
import com.example.fechaconta.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginFragment extends Fragment {
    private final String TAG = "LOGIN_FRAGMENT";
    private static final int RC_SIGN_IN = 9001;
    private Button botao_google;
    private Button botao_facebook;
    private Button botao_login_email;
    private Button botao_desenvolvedor;
    private Button botao_criar_conta;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login_metodo, container,false);
        mAuth = FirebaseAuth.getInstance();

        //botão desenvolvedor manda direto para dentro do app
        botao_desenvolvedor = view.findViewById(R.id.botao_desenvolvedor);
        botao_desenvolvedor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entrar();
            }
        });

        //botão login com email troca para fragmento com email e senha
        botao_login_email = view.findViewById(R.id.botao_login_email);
        botao_login_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((Login) getActivity()).trocaFragParaEmail();
            }
        });

        //botão criar conta chama função que abre activity cadastro
        botao_criar_conta = view.findViewById(R.id.botao_criar_conta);
        botao_criar_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();}
        });



        botao_google = view.findViewById(R.id.botao_google);
        botao_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        return view;
    }

    //fução de entrar na main activity
    public void entrar() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    //função que abre a activity cadastro
    public void abrirCadastro() {
        Intent intent = new Intent(getActivity(), ActivityRegistro.class);
        startActivity(intent);
        //getActivity().finish();
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completeTask) {
        try {
            GoogleSignInAccount account = completeTask.getResult(ApiException.class);
            Log.d(TAG, "handleSignInResult: "+ account.getId());
            firebaseAuthWithGoogle (account.getIdToken());
            entrar();
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:failed code=  "+ e.getStatusCode() );

        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithCredential:Success ");
                            entrar();

                        }else{
                            Log.w(TAG, "signInWithCredential:Failure ");
                            Toast.makeText(getContext(), "Autentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
