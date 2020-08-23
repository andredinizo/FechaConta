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
import com.google.android.gms.tasks.OnFailureListener;
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

        //Recupera os Adicionais do Banco de Dados.
        buscaAdicionais();

        return view;
    }



    /**
     * @Soneca
     *  Passa por cada item da Lista de ADICIONAIS
     *  e recupera os ADICIONAL's por meio do get() e
     *  seta o Tipo de Adicional e etc... Dentro de cada
     *  Snapshot verificamos se as listas estão completas
     *  e se sim chamamos o metodo @configurarAdapter()...
     *
     * @collectionGroup() : Retorna todos os documentos
     * de todas as coleções que tem o mesmo ID.
     *
     * * Não usei o Collection Group, pois ele recupera todos as coleçoes com
     *   mesmo ID desde da RAIZ ou seja recuperaria de dodos os pratos e restaurantes.
     *
     *  -> Verificamos se a lista está completa, garantindo que o metodo @configurarAdapter()
     *     seja chamdo apenas quando, o ultimo item de @param listAd for o mesmo que o o
     *     adicional do nosso for, ou seja se estamos no ultimo item da lista, em seguida
     *     verificamos por meio de um contador se estamos no ultimo item do nosso TASK. se sim
     *     Atualizamos a UI.
     *
     */
    private void buscaAdicional(final List<Adicionais> listAd) {

        final List<Adicional> listItem = new ArrayList<Adicional>();

        for (final Adicionais adicionais : listAd) {


            adicionais.getReference().collection("Adicional").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {

                        // Contador para saber quando é o
                        // ultimo ADICIONAL do respectivo ADICIONAIS.
                        int contador = 1; // 1 pois queremos comparar com
                        // o tamanho do nosso @task.getResult().

                        for (DocumentSnapshot document : task.getResult()) {

                            //Setamos os atributos do ADICIONAL
                            Adicional adicional = document.toObject(Adicional.class);
                            adicional.setReference(document.getReference());
                            adicional.setNomeItem(document.getId());
                            adicional.setNomeAd(adicionais.getNomeAd());
                            adicional.setTipoAd(adicionais.getTipoAd());
                            adicional.setLimite(adicionais.getLimite());
                            listItem.add(adicional);

                            //Acrescenta nosso contador.
                            //Respectivo ao indice que estamos.
                            contador++;

                            // Para garantir que o adapter sera chamado
                            // apenas no ultimo Snapshot do ultimo item de
                            // @listAd, seguimos com :
                            if (listAd.get(listAd.size() -1).getNomeAd().equals(adicionais.getNomeAd())){   // Se estivermos no ultimo ADICIONAIS

                                Log.d(TAG, "onComplete: ULTIMOS ADICIONAIS ==================> " + adicionais.getNomeAd());

                                if(contador == task.getResult().size()){     // e no ultimo ADICIONAL

                                    Log.d(TAG, "onComplete: ULTIMO ADICIONAL --------------------> " + adicional.getNomeItem() );

                                    configurarAdapter(listAd, listItem);            // Configuramos nosso Adapter

                                }
                                else{
                                    Log.d(TAG, "onComplete: ADCIONAL >>>>>> " + adicional.getNomeItem());
                                }

                            }
                            else{
                                Log.d(TAG, "onComplete: ADICIONAL >>>>> " + document.getData().toString());
                            }

                        }

                    }
                    else {
                        Log.d(TAG, "onComplete: Falha ao Carregar os Documentos ADIOCIONAL");

                    }
                }
            });

        }
    }

    /**
     * Atualiza a UI, setando os
     * adapters e etc...
     *
     */
    private void configurarAdapter(List<Adicionais> listAd, List<Adicional> listItem) {

        recyclerViewItems.setAdapter(new AdicionaisAdapter(listItem));

    }

    /**
     * Percorre por cada Documento de Adicionais e salva em uma lista de
     * adicionais, onde adiciona a cada objeto da lista sua referencia no
     * Firestore, depois chama @buscaAdicional()...
     */
    private void buscaAdicionais() {
        dishes.getReference().collection("Adicionais").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    List<Adicionais> listAd = new ArrayList<>();

                    for (DocumentSnapshot document : task.getResult()) {

                        //Setamos os atributos do ADICIONAIS.
                        listAd.add(document.toObject(Adicionais.class));
                        listAd.get(listAd.size() - 1).setReference(document.getReference());
                        listAd.get(listAd.size() - 1).setNomeAd(document.getId());

                    }

                    buscaAdicional(listAd);

                } else {

                }

            }
        });

    }


}



















































    // Codigo 1.0
    // Retornava pra de todos os pratos.
    /*   /**
     * @Soneca
     * Recupera os Items por meio do @collectionGroup
     * e seta o Tipo de Adicional dentro de cada Snapshot
     * buscando o getParent().getParent da referência do item,
     * ou seja volta pro documento de Adicionais, e seta o nosso item
     * com suas caracteristicas.
     *
     * @collectionGroup() : Retorna todos os documentos de todas as
     * coleções que tem o mesmo ID.
     *
     *  -> Depois chamamos o Recycler e setamos o Adapter, e garantimos
     *    que não vamos chamalo mais de uma vez, verificando se o numero
     *    de item na lista equivale ao de resultados.
     *//*
    private void buscaItems() {


        FirebaseFirestore.getInstance().collectionGroup("Adicional").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    final List<Adicional> listItem = new ArrayList<>();
                    final List<Adicionais> listAdicionais = new ArrayList<>();

                    for(final DocumentSnapshot documentSnapshot : task.getResult()){

                        Adicional adicional = documentSnapshot.toObject(Adicional.class);

                        // Garantimos que adicional != null, @Warning
                        if (adicional != null) {
                            adicional.setNomeItem(documentSnapshot.getId());
                            adicional.setReference(documentSnapshot.getReference());
                            adicional.setNomeAd(documentSnapshot.getReference().getParent().getParent().getId());
                            listItem.add(adicional);
                        }

                        // Verificação:
                        Log.d(TAG, "onComplete: ADICIONADO A LISTA: " + documentSnapshot.getData().toString());
                        Log.d(TAG, "onComplete: LISTA ============> " + listItem.get(listItem.size() -1).isIsGratis());

                        // Vamos resgatar os Atributos Mãe do nosso objeto, ou seja as
                        //as variaveis referente ao tipo de adicional, para isso, voltamos
                        //até o ducumento de ADICIONAIS, e resgatamos os dados.
                        documentSnapshot.getReference().getParent().getParent().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot document) {

                                // Setamos os dados Resgatados ao Coleção PAI.
                                Adicionais adicionais = document.toObject(Adicionais.class);
                                listAdicionais.add(adicionais);
                                listItem.get(listItem.size() -1).setTipoAd(adicionais.getTipoAd());
                                listItem.get(listItem.size() -1).setLimite(adicionais.getLimite());


                                //  Verificaremos se esse é o ultimo documento, e Atualizamos
                                // a UI, assim garantimos que não chamaremos o adapter mais de
                                // uma vez.
                                if(listAdicionais.size() == task.getResult().size()){                     // Se o tamanho da nossa lista for o mesmo
                                    recyclerViewItems.setAdapter(new AdicionaisAdapter(listItem));  //do numero de resultados da TASK esse é o
                                    // ultimo item da lista de items.
                                    // Então podemos chamar o adapter.

                                    Log.d(TAG, "onSuccess: ULTIMO ITEM ===> Adicionais: " + document.getId());

                                }else

                                    Log.d(TAG, "onSuccess: ADICIONAIS: " + document.getData().toString());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.d(TAG, "onFailure: Falha ao recuperar TIPO DE ADICIONAL!!! ===> " + documentSnapshot.getId());

                            }
                        });


                    }

                }
                else {
                    Log.d(TAG, "onComplete: Falha ao Resgatar! ");
                }
            }
        });

    }


}*/






























