package com.example.fechaconta.fragments;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.R;
import com.example.fechaconta.adapter.AdicionaisAdapter;
import com.example.fechaconta.interfaces.Observador;
import com.example.fechaconta.models.Adicionais;
import com.example.fechaconta.models.Adicional;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.utilitys.MyListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemsFragment extends Fragment {
    public static final String TAG = "ITEMS_FRAGMENT";
    private Dishes dishes;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private int contador;

    private TextView textViewItem;
    private TextView textViewItemDescr;
    private TextView textViewItemValor;
    private TextView textViewItemTempo;
    private ImageView imageViewItem;
    private RecyclerView recyclerViewItems;


    public ItemsFragment(Dishes dishes) {
        this.dishes = dishes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        textViewItem = view.findViewById(R.id.textview_item);
        textViewItemDescr = view.findViewById(R.id.textview_itemdescr);
        textViewItemTempo = view.findViewById(R.id.textview_itemtempo);
        textViewItemValor = view.findViewById(R.id.textview_itemvalor);
        recyclerViewItems = view.findViewById(R.id.recycler_itens);

        textViewItem.setText(dishes.getName());
        textViewItemDescr.setText(dishes.getDescription());
        textViewItemValor.setText(numberFormat.format(dishes.getValue()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewItems.setLayoutManager(layoutManager);

        //Recupera os Adicionais do Banco de Dados
        buscaItems();


        return view;
    }

    /**
     * Recupera os Items por meio do @collectionGroup
     * e seta o Tipo de Adicional dentro de cada Snapshot
     * buscando o getParent().getParent da referência do item,
     * ou seja volta pro documento de Adicionais, e seta o nosso item
     * com suas caracteristicas.
     *  Depois chamamos o Recycler fora mesmo, da ultima task
     */
    private void buscaItems() {

        FirebaseFirestore.getInstance().collectionGroup("Adicional").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    final List<Adicional> listItem = new ArrayList<>();
                    MyListener myListener0 = new MyListener();


                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        listItem.add(documentSnapshot.toObject(Adicional.class));
                        listItem.get(listItem.size() - 1).setNomeItem(documentSnapshot.getId());
                        listItem.get(listItem.size() - 1).setReference(documentSnapshot.getReference());
                        listItem.get(listItem.size() - 1).setNomeAd(documentSnapshot.getReference().getParent().getParent().getId());

                        final MyListener myListener1 = new MyListener();
                        documentSnapshot.getReference().getParent().getParent().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Adicionais adicionais = documentSnapshot.toObject(Adicionais.class);
                                listItem.get(listItem.size() -1).setTipoAd(adicionais.getTipoAd());
                                listItem.get(listItem.size() -1).setLimite(adicionais.getLimite());
                                myListener1.setGatilho(true);

                            }
                        });

                        myListener1.adicionarObservador(new Observador() {
                            @Override
                            public void notificar(MyListener myListener) {

                            }
                        });

                    }


                    recyclerViewItems.setAdapter(new AdicionaisAdapter(listItem));

                }
            }
        });

    }


}


























/*    public void contador (){
        contador++;
    }

    *//**
     * Percorre porcada Tipo de Adicional e resgata no Firestore
     * os documentos de cada Tipo, e salva em uma lista de items,
     * e no final de cada busca, quando task.isSuccessful acresentamos
     * nosso @contador, e notificamos nosso
     * @Observador, com o metodo
     * @setGatilho(true) que verifica se o
     * @contador bateu o total de Tipos de Adicionais, ou seja,
     * se todos os items foram recuperados,
     * se sim ele atualiza atualiza a UI chamando o metodo
     * @configurarAdapter(listAd,listItem)...
     *
     * @param listAd
     *//*
    private void buscaAdicional (final List<Adicionais> listAd){
        final List <Adicional> listItem = new ArrayList<Adicional>();

        final MyListener myListener = new MyListener();
        myListener.adicionarObservador(new Observador() {
            @Override
            public void notificar(MyListener myListener) {
                if (contador == listAd.size()){
                    //Configura o Adapter
                    configurarAdapter(listAd, listItem);
                    myListener.removerObservadore(this);

                }else{
                    Log.d(TAG, "Carregou a Lista: " + contador + " --- > " + listAd.get(contador));
                }

            }
        });

        for (final Adicionais adicionais : listAd){


            adicionais.getReference().collection("Adicional").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for( DocumentSnapshot document : task.getResult()){
                            listItem.add(document.toObject(Adicional.class));
                            listItem.get(listItem.size() -1).setReference(document.getReference());
                            listItem.get(listItem.size() -1).setNomeItem(document.getId());
                            listItem.get(listItem.size() -1).setNomeAd(adicionais.getNomeAd());
                            listItem.get(listItem.size() -1).setTipoAd(adicionais.getTipoAd());
                            listItem.get(listItem.size() -1).setLimite(adicionais.getLimite());
                        }

                        contador();
                        myListener.setGatilho(true);
                    }
                    else{

                    }
                }
            });

        }
    }

    *//**
     * Atualiza a UI, setando os
     * adapters e etc...
     * @param listAd
     * @param listItem
     *//*
    private void configurarAdapter(List<Adicionais> listAd, List<Adicional> listItem) {
        recyclerViewItems.setAdapter(new AdicionaisAdapter(listItem));
    }

    *//**
     * Percorre por cada Documento de Adicionais e salva em uma lista de
     * adicionais, onde adiciona a cada objeto da lista sua referencia no
     * Firestore, depois chama @buscaAdicional()...
     *//*
    private void buscaAdicionais (){
        dishes.getReference().collection("Adicionais").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List <Adicionais> listAd = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()){
                        listAd.add(document.toObject(Adicionais.class));
                        listAd.get(listAd.size() -1).setReference(document.getReference());
                        listAd.get(listAd.size() -1).setNomeAd(document.getId());
                    }

                    buscaAdicional(listAd);

                }
                else{

                }

            }
        });

    }*/



