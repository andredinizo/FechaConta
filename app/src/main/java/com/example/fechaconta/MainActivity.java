package com.example.fechaconta;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.fragments.SearchFragment;
import com.example.fechaconta.models.Restaurant;
import com.example.fechaconta.models.WriteDB;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView inicioBottom;
    private HomeFragment homeFragment;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, homeFragment, homeFragment.getTag()).commit();
        FirebaseAuth.getInstance().signOut();

    }



}