package com.example.fechaconta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextView inicioBottom;
    private HomeFragment homeFragment;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ExtendedFloatingActionButton btnCheckin;
    private Boolean existeCheckin = false;
    private Usuario usuarioLogado;
    private Usuario.CheckIn checkin;

    public Boolean getExisteCheckin() {
        return existeCheckin;
    }

    public void setExisteCheckin(Boolean check) {
        existeCheckin = check;

        if (existeCheckin) {
            btnCheckin.setVisibility(View.GONE);
        } else {
            btnCheckin.setVisibility(View.VISIBLE);
        }

    }

    //TODO: Pensar necessidade
    public void SetFitsWindows(boolean flag) {

        LinearLayout mainLayout;
        mainLayout = findViewById(R.id.MainLayout);
        mainLayout.setFitsSystemWindows(flag);


    }

    public TextView getInicioBottom() {
        return inicioBottom;
    }

    public void setInicioBottom(TextView inicioBottom) {
        this.inicioBottom = inicioBottom;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public ExtendedFloatingActionButton getBtnCheckin() {
        return btnCheckin;
    }

    public void setBtnCheckin(ExtendedFloatingActionButton btnCheckin) {
        this.btnCheckin = btnCheckin;
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


        btnCheckin = findViewById(R.id.btnCheckin);

        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), QRreader.class);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference dbrealtime;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuario = db.collection("User").document(user.getUid());

        usuario.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                usuarioLogado = Objects.requireNonNull(task.getResult()).toObject(Usuario.class);
            }
        });

        dbrealtime = FirebaseDatabase.getInstance().getReference();
        dbrealtime.child("checkin").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario.CheckIn checkin  = snapshot.getValue(Usuario.CheckIn.class);

                if(checkin != null){

                    MainActivity.this.setExisteCheckin(true);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //VERIFICA SE EXISTE CHECKIN DO USUARIO ATIVO


    }
}