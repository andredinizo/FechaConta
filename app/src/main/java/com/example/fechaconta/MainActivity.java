package com.example.fechaconta;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.fragments.SearchFragment;
import com.example.fechaconta.models.Restaurant;
import com.example.fechaconta.models.WriteDB;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView inicioBottom;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, homeFragment, homeFragment.getTag()).commit();

        inicioBottom = findViewById(R.id.bottom_inicio);
        inicioBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeFragment != null && !homeFragment.isVisible()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new HomeFragment()).commit();
                }
            }
        });

       /* WriteDB wdb = new WriteDB();
        List<Restaurant> reslist = new ArrayList<>();

        reslist.add(new Restaurant());
        reslist.add(new Restaurant());
        reslist.add(new Restaurant());
        reslist.add(new Restaurant());
        reslist.add(new Restaurant());
        reslist.add(new Restaurant());
        reslist.add(new Restaurant());

        reslist.get(1).setNome("McDonald's");
        reslist.get(1).setDes("Faça seu pedido Mcdonald's");
        reslist.get(1).setCategoria("FastFood, Lanches");
        reslist.get(1).setMedia(3.5f);
        reslist.get(1).setTaval(200);
        reslist.get(1).setEnder("Av. São Carlos, 3134");
        reslist.get(1).setCidade("São Carlos");
        reslist.get(1).setEstado("SP");
        reslist.get(1).setUrlicon("MCDONALDS.png");
        reslist.get(1).setUrlheader("");

        reslist.get(2).setNome("Burger King");
        reslist.get(2).setDes("Passeio São Carlos");
        reslist.get(2).setCategoria("FastFood, Lanches");
        reslist.get(2).setMedia(4.5f);
        reslist.get(2).setTaval(54);
        reslist.get(2).setEnder("Av. Francisco Pereira Lopes, 1701");
        reslist.get(2).setCidade("São Carlos");
        reslist.get(2).setEstado("SP");
        reslist.get(2).setUrlicon("BURGERKING.png");
        reslist.get(2).setUrlheader("");

        reslist.get(3).setNome("Domino's Pizza");
        reslist.get(3).setDes("Rede de Restaurantes com grande variedade de Pizza!");
        reslist.get(3).setCategoria("Pizza");
        reslist.get(3).setMedia(2.3f);
        reslist.get(3).setTaval(450);
        reslist.get(3).setEnder(" Av. São Carlos, 3183");
        reslist.get(3).setCidade("São Carlos");
        reslist.get(3).setEstado("SP");
        reslist.get(3).setUrlicon("DOMINOSPIZZA.png");
        reslist.get(3).setUrlheader("");

        reslist.get(4).setNome("Giraffas");
        reslist.get(4).setDes("Cadeia de lanchonetes com menu de hambúrgueres, sanduíches diversos e pratos executivos em regime expresso.");
        reslist.get(4).setCategoria("Lanches, Almoço");
        reslist.get(4).setMedia(4.7f);
        reslist.get(4).setTaval(279);
        reslist.get(4).setEnder("Passeio dos Flamboyants, 200");
        reslist.get(4).setCidade("São Carlos");
        reslist.get(4).setEstado("SP");
        reslist.get(4).setUrlicon("GIRAFFAS.png");
        reslist.get(4).setUrlheader("");

        reslist.get(5).setNome("Subway");
        reslist.get(5).setDes("Rede de fast-food onde você monta seus próprios lanches e saladas.");
        reslist.get(5).setCategoria("Lanches");
        reslist.get(5).setMedia(3.9f);
        reslist.get(5).setTaval(317);
        reslist.get(5).setEnder("Av. São Carlos, 2911");
        reslist.get(5).setCidade("São Carlos");
        reslist.get(5).setEstado("SP");
        reslist.get(5).setUrlicon("SUBWAY.jpeg");
        reslist.get(5).setUrlheader("");

        reslist.get(6).setNome("Ya San");
        reslist.get(6).setDes("Restaurante japonês elegante de decoração e menu contemporâneos com iguarias inovadoras entre as tradicionais.");
        reslist.get(6).setCategoria("Japonesa, Drinks");
        reslist.get(6).setMedia(4.9f);
        reslist.get(6).setTaval(715);
        reslist.get(6).setEnder("Av. Dr. Carlos Botelho, 1768");
        reslist.get(6).setCidade("São Carlos");
        reslist.get(6).setEstado("SP");
        reslist.get(6).setUrlicon("YASAN.png");
        reslist.get(6).setUrlheader("");

        for( int i =1; i < 7; i++){
            wdb.escrRestaurante(reslist.get(i));
        }*/



    }



}