package com.example.fechaconta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fechaconta.fragments.LoginEmailFragment;
import com.example.fechaconta.fragments.LoginFragment;
import com.example.fechaconta.fragments.Registro1Fragment;
import com.example.fechaconta.fragments.Registro2Fragment;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.Objects;

public class Login extends AppCompatActivity {

    final private String TAG="LOGIN";
    public ImageView logo;
    private FirebaseAuth mAuth;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        *
        * Ativa App Center
        *
        * */

        AppCenter.start(getApplication(), "ace1eef7-0e90-4635-81c1-9e40d8323a8b", Analytics.class, Crashes.class);

        /*
        *
        *
        * Ativa app Center
        *
        * */

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        logo = findViewById(R.id.LogoSplash);

        //Chama o handler que abre LoginFragment com atraso
        handler1.postDelayed(r1,1200);
        handler2.postDelayed(r2,1500);


        //Tirar esse signout daqui
        FirebaseAuth.getInstance().signOut();
    }


    //Definicão do handler que atrasa 1,5seg na splash
    final Handler handler1 = new Handler();
    final Handler handler2 = new Handler();

    final Runnable r1 = new Runnable() {
        @Override
        public void run() {
            //movimento do logo
            ObjectAnimator animation = ObjectAnimator.ofFloat(logo,"translationY", 0f, -600f);
            animation.setDuration(300);
            animation.start();

        }
    };

    //logo some e abre fragmento de login
    final Runnable r2 = new Runnable() {
        @Override
        public void run() {
            verifyAuth();
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void verifyAuth () {
        FirebaseUser currentUser = mAuth.getCurrentUser();


        //Esse trecho está aqui apenas para testarmos como a conta está logada
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Log.d(TAG, "mAuth current user = "+ currentUser);
        Log.d(TAG, "FacebookAccessToken = "+ accessToken);
        Log.d(TAG, "GoogleLastSignedInAccount = "+ account);
        //Esse trecho está aqui apenas para testarmos como a conta está logada


        if(currentUser != null){
            /*
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
             */
            entrar();
        }else
            trocaFragParaMetodo();
    }



    public void trocaFragParaEmail(){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentManager.replace(R.id.FragContainer, new LoginEmailFragment())
                .addToBackStack(null)
                .commit();
    }

    public void trocaFragParaMetodo(){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentManager.replace(R.id.FragContainer, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }

    public void trocaFragParaRegistro1(){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentManager.replace(R.id.FragContainer, new Registro1Fragment())
                .addToBackStack(null)
                .commit();
    }

    public void trocaFragParaRegistro2(String email){
        Registro2Fragment registro2Fragment = new Registro2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        registro2Fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.FragContainer, registro2Fragment)
                .addToBackStack(null)
                .commit();
    }

    public void trocaFragParaFimCadastro(){
        Registro2Fragment registro2Fragment = new Registro2Fragment();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.constraint, registro2Fragment)
                .addToBackStack(null).commit();
    }

    public void entrar(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}