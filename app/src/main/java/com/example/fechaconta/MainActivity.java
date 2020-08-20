package com.example.fechaconta;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.fragments.SearchFragment;
import com.example.fechaconta.models.Restaurant;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView inicioBottom;
    private HomeFragment homeFragment;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public void SetFitsWindows(boolean flag){

        LinearLayout mainLayout;
        mainLayout = findViewById(R.id.MainLayout);
        mainLayout.setFitsSystemWindows(flag);

    }


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

        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, homeFragment, homeFragment.getTag()).commit();
        FirebaseAuth.getInstance().signOut();

    }
}