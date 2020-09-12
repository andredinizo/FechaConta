package com.example.fechaconta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.adapter.PratosComandaAdapter;
import com.example.fechaconta.adapter.UsuarioComandaAdapter;
import com.example.fechaconta.fragments.HomeFragment;
import com.example.fechaconta.models.ItemComanda;
import com.example.fechaconta.models.Mesa;
import com.example.fechaconta.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private View includeBottomSheet;
    private TextView inicioBottom;
    private HomeFragment homeFragment;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ExtendedFloatingActionButton btnCheckin;
    private Boolean existeCheckin = false;
    private Usuario usuarioLogado;
    private Usuario.CheckIn checkin = Usuario.CheckIn.getInstance();
    private int tempolimiteCheckin;
    private Timer timer;
    private CoordinatorLayout bottomsheetComanda;
    private RecyclerView recyclerUsuariosComanda;
    private RecyclerView recyclerItensComanda;
    private Mesa mesaCheckin;


    public Boolean getExisteCheckin() {
        return existeCheckin;
    }

    public void setExisteCheckin(Boolean check) {
        existeCheckin = check;

        if (MainActivity.this.existeCheckin) {

            btnCheckin.setVisibility(View.GONE);
            bottomsheetComanda.setVisibility(View.VISIBLE);

        } else {

            btnCheckin.setVisibility(View.VISIBLE);
            bottomsheetComanda.setVisibility(View.GONE);

        }

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

        //FirebaseAuth.getInstance().signOut();

        bottomsheetComanda = findViewById(R.id.bottomsheet_comanda);

        AppCenter.start(getApplication(), "ace1eef7-0e90-4635-81c1-9e40d8323a8b", Analytics.class, Crashes.class);

        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, homeFragment, homeFragment.getTag()).commit();


        //Instancia os layout Manager e configura os recycler USUARIOS
        recyclerUsuariosComanda = findViewById(R.id.pessoasNaMesaRecycler);
        RecyclerView.LayoutManager layManagerUserComanda = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerUsuariosComanda.setLayoutManager(layManagerUserComanda);
        recyclerUsuariosComanda.setHasFixedSize(false);
        recyclerUsuariosComanda.setNestedScrollingEnabled(true);

        //INSTANCIA OS LAYOUTMANAGER E CONFIGURA O RECYCLER ITENS
        recyclerItensComanda = findViewById(R.id.ItensRecycler);
        RecyclerView.LayoutManager layManagerItensComanda = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerItensComanda.setLayoutManager(layManagerItensComanda);
        recyclerItensComanda.setHasFixedSize(false);

        bottomsheetComanda = findViewById(R.id.bottomsheet_comanda);
        btnCheckin = findViewById(R.id.btnCheckin);
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), QRreader.class);
                startActivity(intent);

            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuario = db.collection("User").document(user.getUid());

        usuario.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                usuarioLogado = Objects.requireNonNull(task.getResult()).toObject(Usuario.class);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        VerificaCheckin();

    }

    //POPULA COMANDA

    public void AtualizaUsuarios() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Usuario> listaUsuarioLogado = new ArrayList<>();

        UsuarioComandaAdapter usuarioLogadoAdapter = new UsuarioComandaAdapter();
        usuarioLogadoAdapter.notifyDataSetChanged();
        recyclerUsuariosComanda.setNestedScrollingEnabled(false);
        recyclerUsuariosComanda.setHasFixedSize(true);
        recyclerUsuariosComanda.setAdapter(usuarioLogadoAdapter);

    }

    public void AtualizaItensComanda() {

        DatabaseReference dbrealtime = FirebaseDatabase.getInstance().getReference();
        ;

        dbrealtime.child("comanda").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Usuario.CheckIn.setListaPedidos(snapshot.getValue(ItemComanda.class));
                PratosComandaAdapter pratosComanda = new PratosComandaAdapter(Usuario.CheckIn.getListaPedidos());
                pratosComanda.notifyDataSetChanged();
                recyclerItensComanda.setAdapter(pratosComanda);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Usuario.CheckIn.setListaPedidos(snapshot.getValue(ItemComanda.class));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    //Busca Mesa

    public void BuscaMesa() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Restaurant").document(Usuario.CheckIn.getInstance().getRestaurante()).collection("Mesas").document(Usuario.CheckIn.getInstance().getMesaId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mesaCheckin = Objects.requireNonNull(task.getResult()).toObject(Mesa.class);
                            assert mesaCheckin != null;
                            Log.d("MESA", mesaCheckin.toString());
                            AtualizaUsuarios();
                            AtualizaItensComanda();
                        }
                    }
                });

    }

    //VERIFICA SE EXISTE CHECKIN DO USUARIO ATIVO

    public void VerificaCheckin() {

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
                checkin = snapshot.getValue(Usuario.CheckIn.class);

                if (checkin != null) {

                    MainActivity.this.setExisteCheckin(true);

                } else {

                    MainActivity.this.setExisteCheckin(false);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        dbrealtime.child("checkin").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario.CheckIn checkInAtualizado = snapshot.getValue(Usuario.CheckIn.class);

                if (checkInAtualizado != null) {

                    switch (checkInAtualizado.getEstado()) {
                        // 0 - Esperando confirmação;
                        // 1- Check-in Aceito ;
                        // 2- Check-in Recusado;
                        // 3 - Tempo de Checkin esgotado ;
                        // 4- Check-in Concluido c/ Pagamento;
                        // 5 - Checkin concluido s/ pagamento

                        case 0:
                            CheckinAguardando();
                            break;
                        case 1:
                            ChecInAceito();
                            if (timer != null) {
                                timer.cancel();
                            }
                            break;
                        case 2:
                            CheckInRecusado();
                            if (timer != null) {
                                timer.cancel();
                            }
                            break;
                        case 3:
                            CheckinTempoEsgotado();

                            break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (MainActivity.this.existeCheckin) {

            btnCheckin.setVisibility(View.GONE);
            bottomsheetComanda.setVisibility(View.VISIBLE);

        } else {

            btnCheckin.setVisibility(View.VISIBLE);
            bottomsheetComanda.setVisibility(View.GONE);

        }

    }

    public void ChecInAceito() {

        LinearLayout linearCheckinAceito = findViewById(R.id.CheckinAceito);
        LinearLayout linearAguardando = findViewById(R.id.AguardandoConfirmar);
        LinearLayout linearRecusado = findViewById(R.id.CheckinRecusado);
        LinearLayout linearTimedOut = findViewById(R.id.CheckinTimedOut);

        linearCheckinAceito.setVisibility(View.VISIBLE);
        linearAguardando.setVisibility(View.GONE);
        linearRecusado.setVisibility(View.GONE);
        linearTimedOut.setVisibility(View.GONE);
        BuscaMesa();



    }

    public void CheckInRecusado() {

        LinearLayout linearCheckinAceito = findViewById(R.id.CheckinAceito);
        LinearLayout linearAguardando = findViewById(R.id.AguardandoConfirmar);
        LinearLayout linearRecusado = findViewById(R.id.CheckinRecusado);
        LinearLayout linearTimedOut = findViewById(R.id.CheckinTimedOut);

        linearCheckinAceito.setVisibility(View.GONE);
        linearAguardando.setVisibility(View.GONE);
        linearRecusado.setVisibility(View.VISIBLE);
        linearTimedOut.setVisibility(View.GONE);

    }

    public void CheckinAguardando() {

        if (this.timer != null) {
            this.timer.cancel();
        }

        this.timer = new Timer();

        LinearLayout linearCheckinAceito = findViewById(R.id.CheckinAceito);
        LinearLayout linearAguardando = findViewById(R.id.AguardandoConfirmar);
        LinearLayout linearRecusado = findViewById(R.id.CheckinRecusado);
        LinearLayout linearTimedOut = findViewById(R.id.CheckinTimedOut);

        linearCheckinAceito.setVisibility(View.GONE);
        linearAguardando.setVisibility(View.VISIBLE);
        linearRecusado.setVisibility(View.GONE);
        linearTimedOut.setVisibility(View.GONE);

        TextView txtTempo = findViewById(R.id.tempocheckin);

        ProgressBar progressBar = findViewById(R.id.progressCheckin);
        progressBar.setIndeterminate(true);

        tempolimiteCheckin = 60;


        TimerTask atualizaUI = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        tempolimiteCheckin = tempolimiteCheckin - 1;

                        if (tempolimiteCheckin > 9) {
                            txtTempo.setText("0:" + String.valueOf(tempolimiteCheckin));
                        } else {
                            txtTempo.setText("0:0" + String.valueOf(tempolimiteCheckin));
                        }


                        if (tempolimiteCheckin == 0) {

                            checkin.setEstado(3); //ESTADO DE CHECKIN TEMPO ESGOTADO
                            checkin.AtualizaCheckin();
                            timer.cancel();


                        }

                    }
                });

            }
        };

        timer.schedule(atualizaUI, 1, 1000);

    }

    public void CheckinTempoEsgotado() {

        LinearLayout linearCheckinAceito = findViewById(R.id.CheckinAceito);
        LinearLayout linearAguardando = findViewById(R.id.AguardandoConfirmar);
        LinearLayout linearRecusado = findViewById(R.id.CheckinRecusado);
        LinearLayout linearTimedOut = findViewById(R.id.CheckinTimedOut);

        linearCheckinAceito.setVisibility(View.GONE);
        linearAguardando.setVisibility(View.GONE);
        linearRecusado.setVisibility(View.GONE);
        linearTimedOut.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public CoordinatorLayout getBottomsheetComanda() {
        return bottomsheetComanda;
    }

    public void setBottomsheetComanda(CoordinatorLayout bottomsheetComanda) {
        this.bottomsheetComanda = bottomsheetComanda;
    }
}