package com.example.fechaconta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.fechaconta.fragments.LoginEmailFragment;
import com.example.fechaconta.fragments.LoginFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    public TextView logo;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        logo = findViewById(R.id.LogoSplash);

        //Chama o handler que abre LoginFragment com atraso
        handler1.postDelayed(r1,1200);
        handler2.postDelayed(r2,1500);


    }


    //Definicão do handler que atrasa 1,5seg na splash
    final Handler handler1 = new Handler();
    final Handler handler2 = new Handler();

    final Runnable r1 = new Runnable() {
        @Override
        public void run() {
            //movimento do logo
            ObjectAnimator animation = ObjectAnimator.ofFloat(logo,"translationY", 0f, -470f);
            animation.setDuration(300);
            animation.start();

        }
    };

    //logo some e abre fragmento de login
    final Runnable r2 = new Runnable() {
        @Override
        public void run() {
            // logo.setVisibility(View.GONE);
            //getSupportFragmentManager().beginTransaction().add(R.id.FragContainer, new LoginFragment()).commit();
            verifyAuth();
        }
    };



    public void trocaFragParaEmail(){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentManager.replace(R.id.FragContainer, new LoginEmailFragment()).commit();
    }

    public void trocaFragParaMetodo(){
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentManager.replace(R.id.FragContainer, new LoginFragment()).commit();
    }

    public void verifyAuth () {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }else{
                getSupportFragmentManager().beginTransaction().add(R.id.FragContainer, new LoginFragment()).commit();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}