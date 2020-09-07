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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginFragment extends Fragment {
    private final String TAG = "LOGIN_FRAGMENT";
    private static final int RC_SIGN_IN = 9001;
    private LoginButton botao_facebook;
    private CallbackManager fb_callback_manager;
    private Button botao_google;
    private Button botao_login_email;
    private Button botao_desenvolvedor;
    private Button botao_criar_conta;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private boolean login_google;

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




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        botao_google = view.findViewById(R.id.botao_google);
        botao_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });





        //preparação login facebook
        fb_callback_manager = CallbackManager.Factory.create();
        botao_facebook =view.findViewById(R.id.botao_facebook);
        botao_facebook.setFragment(this);
        botao_facebook.setPermissions("email","public_profile");
        // fb callback registration
        botao_facebook.registerCallback(fb_callback_manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "facebook:onError");
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


    //função que abre a activity cadastro
    public void abrirCadastro() {
        Intent intent = new Intent(getActivity(), ActivityRegistro.class);
        startActivity(intent);
        //getActivity().finish();
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        login_google=true;
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //google
        if (login_google) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
            return;
        }

        //facebook
        fb_callback_manager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completeTask) {
        try {
            GoogleSignInAccount account = completeTask.getResult(ApiException.class);
            Log.d(TAG, "handleSignInResult: "+ account.getId());
            firebaseAuthWithGoogle (account.getIdToken());
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



    private void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            entrar();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Autentication Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
