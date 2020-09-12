package com.example.fechaconta.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fechaconta.MainActivity;
import com.example.fechaconta.R;
import com.example.fechaconta.adapter.AdicionaisAdapter;
import com.example.fechaconta.models.Adicionais;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.models.Usuario;
import com.example.fechaconta.utilitys.Aplotoso;
import com.example.fechaconta.utilitys.StringStuff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

/**
 * Pendencias e Conflitos:
 * <p>
 * -> CheckIn : Verificação de Checkin está sendo
 * feita pela variavel MainActivity.Check
 * e dou gone no botão de checkIn, entretanto não
 * acho que seja o lugar certo.
 */
public class ItemsFragment extends Fragment {
    public static final String TAG = "ITEMS_FRAGMENT";



    private Dishes dishes;

    private MaterialButton plussButton;
    private MaterialButton lessButton;
    private TextView textViewQuantidade;
    private TextView textViewItem;
    private TextView textViewItemDescr;
    private TextView textViewItemValor;
    private TextView textViewItemTempo;
    private TextView textViewItemTotal;
    private ImageView imageViewItem;
    private RecyclerView recyclerViewItems;
    private Toolbar toolbar;
    private BottomAppBar bottomBar;
    private LinearLayout linearLayoutRecycler;
    private LinearLayout buttonAddItem;

    public ItemsFragment(Dishes dishes) {
        this.dishes = dishes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        bottomBar = view.findViewById(R.id.bottombar);
        MainActivity mainActivity = (MainActivity) getActivity();

        textViewQuantidade = view.findViewById(R.id.textview_quantidade_item);
        linearLayoutRecycler = view.findViewById(R.id.linearlayout_item_recycler);
        textViewItemDescr = view.findViewById(R.id.textview_itemdescr);
        textViewItemTempo = view.findViewById(R.id.textview_itemtempo);
        textViewItemTotal = view.findViewById(R.id.textview_item_total);
        textViewItemValor = view.findViewById(R.id.textview_itemvalor);
        recyclerViewItems = view.findViewById(R.id.recycler_itens);
        imageViewItem = view.findViewById(R.id.imagem_item);
        buttonAddItem = view.findViewById(R.id.btn_additem);
        textViewItem = view.findViewById(R.id.textview_item);
        plussButton = view.findViewById(R.id.button_pluss_item);
        lessButton = view.findViewById(R.id.button_less_item);
        toolbar = view.findViewById(R.id.toolbar_item);

        //Configura o Total do Botão de Adicionar pedido, a primeira vez.
        textViewItemTotal.setText(StringStuff.converterString(dishes.getValue(), StringStuff.FORMATAR_VALOR));

        //Seta o text view da quantidade pra mostrar a quantidade, ao iniciar a fragment
        textViewQuantidade.setText(String.valueOf(dishes.getQuantidade()));

        //Ajusta o layout segundo,
        //a variavel CheckIn.

        if (Usuario.CheckIn.verificaCheckIn()) {
            if(Usuario.CheckIn.getInstance().getRestaurante().equals(dishes.getID_restaurante())) {
                bottomBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getBottomsheetComanda().setVisibility(View.GONE);
            }else {
                bottomBar.setVisibility(View.GONE);
            }
        } else {
            bottomBar.setVisibility(View.GONE);

        }

        //Configuração dos botões de Quantidade
        //Botão de Adicionar
        plussButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishes.altQuantidade(Dishes.ADICIONAR);
                textViewQuantidade.setText(String.valueOf(dishes.getQuantidade()));
                textViewItemTotal.setText(StringStuff.converterString(dishes.calcularTotal(), StringStuff.FORMATAR_VALOR));
            }
        });
        //botão de Retirar
        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishes.altQuantidade(Dishes.RETIRAR);
                textViewQuantidade.setText(String.valueOf(dishes.getQuantidade()));
                textViewItemTotal.setText(StringStuff.converterString(dishes.calcularTotal(), StringStuff.FORMATAR_VALOR));
            }
        });


        // Carrega a imagem.
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagem = storage.getReference()
                .child("Restaurantes/Pratos/" + dishes.getUrlImagem());

        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageViewItem);
            }
        });

        // Seta os textViews
        textViewItem.setText(dishes.getName());
        textViewItemDescr.setText(dishes.getDescription());
        textViewItemValor.setText(numberFormat.format(dishes.getValue()));

        // Configura o Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewItems.setLayoutManager(layoutManager);

        // Recupera os Adicionais do Banco de Dados.
        buscaAdicionais();

        // Configura o botão de voltar da NavigationBar.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });


        // Dado CheckIn...

        // Listener Botão addItem
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dishes.adicionarComanda();

                // Colocar uma verificação que a ação foi concluida
                finaliza(view);



            }
        });

        return view;
    }

    private void finaliza(View view) {
        //Verificar se todas as ações foram bem sussedidas
        if (true){
            getActivity().getSupportFragmentManager().popBackStack();
            ((MainActivity) getActivity()).getBottomsheetComanda().setVisibility(View.VISIBLE);
        }

    }

    /**
     * ~Soneca~
     * Passa por cada item da Lista de ADICIONAIS
     * e recupera os ADICIONAL's por meio do get() e
     * seta o Tipo de Adicional e etc... Dentro de cada
     * Snapshot verificamos se as listas estão completas
     * e se sim chamamos o metodo @configurarAdapter()...
     * <p>
     * collectionGroup() : Retorna todos os documentos
     * de todas as coleções que tem o mesmo ID.
     * <p>
     * * Não usei o Collection Group, pois ele recupera todos as coleçoes com
     * mesmo ID desde da RAIZ ou seja recuperaria de dodos os pratos e restaurantes.
     * <p>
     * -> Verificamos se a lista está completa, garantindo que o metodo @configurarAdapter()
     * seja chamdo apenas quando, o ultimo item de @param listAd for o mesmo que o o
     * adicional do nosso for, ou seja se estamos no ultimo item da lista, em seguida
     * verificamos por meio de um contador se estamos no ultimo item do nosso TASK. se sim
     * Atualizamos a UI.
     */
    private void buscaAdicional(@NotNull final List<Adicionais> listAd) {


        for (final Adicionais adicionais : listAd) {


            adicionais.getReference()
                    .collection("Adicional")
                    .orderBy("idxItem", Query.Direction.ASCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) { // Aqui a tarefa já ta concluida, ou seja
                        //o codigo aqui dentro é sicrono.

                        // Assim  colocamos a nossa lita na @ListaAd
                        //
                        listAd.get(listAd.indexOf(adicionais)).setAdicionals(Aplotoso.pullAdicionals(task, adicionais));

                        // Para garantir que o adapter sera chamado
                        // apenas no ultimo item de
                        // @listAd, seguimos com :
                        if (listAd.size() - 1 == listAd.lastIndexOf(adicionais)) {   // Se estivermos no ultimo ADICIONAIS

                            configurarAdapter(listAd);            // Configuramos nosso Adapter


                        }


                    } else {
                        Log.d(TAG, "onComplete: Falha ao Carregar os Documentos ADIOCIONAL");

                    }
                }
            });


        }
    }

    /**
     * Atualiza a UI, setando os
     * adapters e etc...
     */
    private void configurarAdapter(List<Adicionais> listAd) {

        dishes.setAdicionais(listAd);
        recyclerViewItems.setAdapter(new AdicionaisAdapter(dishes, textViewItemTotal));
    }

    /**
     * Percorre por cada Documento de Adicionais e salva em uma lista de
     * adicionais, onde adiciona a cada objeto da lista sua referencia no
     * Firestore, depois chama @buscaAdicional()...
     */
    private void buscaAdicionais() {
        dishes.getReference()
                .collection("Adicionais")
                .orderBy("index", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    buscaAdicional(Aplotoso.pullAdicionais(task));

                }

            }
        });

    }

    public Dishes getDishes() {
        return dishes;
    }

    public void setDishes(Dishes dishes) {
        this.dishes = dishes;
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






























