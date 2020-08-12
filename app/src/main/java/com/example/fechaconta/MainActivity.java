package com.example.fechaconta;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.fragments.SearchFragment;
import com.example.fechaconta.models.Restaurant;
import com.example.fechaconta.models.WriteDB;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new HomeFragment()).commit();



    }



}