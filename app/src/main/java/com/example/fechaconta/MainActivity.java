package com.example.fechaconta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.fragments.ItemsFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {
    private TextView inicioBottom;
    private HomeFragment homeFragment;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ExtendedFloatingActionButton btnCheckin;
    private Boolean isCheck = false;

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    //TODO: Pensar necessidade
    public void SetFitsWindows(boolean flag) {

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

        btnCheckin = findViewById(R.id.btnCheckin);

        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), QRreader.class);
                startActivity(intent);

                if (!isCheck) {

                    //btnCheckin.setVisibility(View.GONE);
                   // isCheck = true;



                } else {

                   // btnCheckin.setVisibility(View.VISIBLE);
                   // isCheck = false;

                }
            }
        });


    }
}