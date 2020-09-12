package com.example.fechaconta.utilitys;

import android.util.Log;

import com.example.fechaconta.models.Adicionais;
import com.example.fechaconta.models.Adicional;
import com.example.fechaconta.models.Dishes;
import com.example.fechaconta.models.Restaurant;
import com.example.fechaconta.models.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * ~ τόσο απλό ~
 *
 *
 * classe utilitaria, cujo os
 * metodos padronizem a forma
 * de converter os dados
 * do Firestore em Objetos.
 *
 * podemos trabalhar com a task, executando o for dentro
 * do metodo, que retorna a lista e os dados nescessarios.
 * já que atualizamos a ui fora do for não seria problema em
 * nenhum dos casos.
 *
 *
 */

public class Aplotoso {

    public static final String TAG = "APLOTOSO";

    public static final int NO_FILTER = 0; /*Default*/

    //Filtros Restaurantes
    public static final int /* Para filtrar por uma Categoria */ RESTAURANT_CATEGORIA = 1 /* text : categoria a ser filtrada */;

     /*================================================================================< Adicionail >
     * Objeto
     *  - Firestore
     *      * valorItem
     *      * nomeItem [ID]
     *      * gratis
     *
     *  - Ambiente
     *      * reference
     *      * include [!get]
     *
     *
     *
     * [!get] = não recupera, pelo menos não aqui!
     *
      */

    /**
     * Seta uma Lista de ADICIONAL's de um
     * mesmo ADICIONAIS.
     * @param task - task a ser percorrida
     * @param adicionais - ADICIONAIS pai.
     * @return - retorna uma lista de Adicional
     */
    public static List<Adicional> pullAdicionals (Task<QuerySnapshot> task, Adicionais adicionais) {

        List <Adicional> adicionalList = new ArrayList<>();

        for (DocumentSnapshot documentSnapshot : task.getResult()){

            //Firestore
            Adicional adicional = documentSnapshot.toObject(Adicional.class);
            adicional.setNomeItem(documentSnapshot.getId());

            //Ambiente
            adicional.setReference(documentSnapshot.getReference());

            //addList
            adicionalList.add(adicional);

            Log.d(TAG, "pullAdicionals:  =====>" + documentSnapshot.getData());

        }

        return adicionalList;
    }

     /*================================================================================< Adicionail >
      */

    /*================================================================================< Adicionais >
     * Objeto
     *  - Firestore
     *      * nomeAd ----[Id do documento!]
     *      * limite
     *      * tipoAd
     *
     *  - Ambiente
     *      * reference
     *      * adicionals [!get]
     *
     * [!get] = não recupera, pelo menos não aqui!
     */

     /**
      * Seta uma lista de ADICIONAIS.
      *
      * não seta a lista de ADICIONAL's,
      * pois isto é feito geralmente dentro
      * de uma outra Task.
      *
      * @param task - Task a ser percorrida.
      * @return - retorna uma lista de ADICIONAIS.
      */
    public static  List<Adicionais> pullAdicionais (@NotNull Task<QuerySnapshot> task) {

        List <Adicionais> adicionaisList = new ArrayList<>();

        for (DocumentSnapshot documentSnapshot : task.getResult()){

            //Firestore
            Adicionais adicionais = documentSnapshot.toObject(Adicionais.class);
            adicionais.setNomeAd(documentSnapshot.getId());

            //Ambiente
            adicionais.setReference(documentSnapshot.getReference());

            //addList
            adicionaisList.add(adicionais);

            Log.d(TAG, "pullAdicionais: =====>" + documentSnapshot.getData());

        }

        return adicionaisList;

    }/*
     ===============================================================================</ Adicionais>*/

    /*===============================================================================< Dishes >

       -Firestore
           * ID --- [Document ID]
           * name
           * description
           * isVegan
           * isVeg
           * isGlutenFree
           * value
           * category
           * avgrating
           * numratings
           * isHighlight
           * urlImagem

       -Ambiente
           * reference
           * adicionais [Não Incluir]
           * quantidade [Não Incluir]

       -Parent
          * categoria
          * cidade
          * des
          * ender
          * estado
          * id_restaurante --- [Id do documento!]
          * media
          * nome
          * taval
          * urlheader
          * urlicon

    */

    public static final int DISHES_CARDAPIO_LIST        = 0;
    public static final int DISHES_HIGHLIGHTS_LIST      = 1;
    public static final int DISHES_CATEGORIAS_LIST      = 2;
    public static final int DISHES_INDEXCATEGORIAS_LIST = 3;



    public List<Dishes> pullDishesCardapio (Task<QuerySnapshot> task, Dishes restaurant) {

        //Lista Pricipal
        List<Dishes> cardapio = new ArrayList<>();

        //Posição do for.
        int position = 0;

        for (DocumentSnapshot document : task.getResult()) {

            //Nosso Objeto editavel de Dishes.
            Dishes dishes = document.toObject(Dishes.class);
            dishes.setID(document.getId());

            //Ambiente
            dishes.setReference(document.getReference());

            //Parente
            dishes.setCategoria(restaurant.getCategoria());
            dishes.setCidade(restaurant.getCidade());
            dishes.setDes(restaurant.getDes());
            dishes.setCidade(restaurant.getCidade());
            dishes.setEnder(restaurant.getEnder());
            dishes.setEstado(restaurant.getEstado());
            dishes.setID_restaurante(restaurant.getID_restaurante());
            dishes.setMedia(restaurant.getMedia());
            dishes.setNome(restaurant.getNome());
            dishes.setTaval(restaurant.getTaval());
            dishes.setUrlheader(restaurant.getUrlheader());
            dishes.setUrlicon(restaurant.getUrlicon());

            //Adiciona a Lista Principal
            cardapio.add(dishes);

            /*switch (qualList){

                case 0 :
                    return cardapio;
                case 1 :
                    return highlights;
                case 2 :
                    return categorias;
                case 3 :
                    return indexCategorias;

            }*/

        }

        return cardapio;
    }

    /*public List<String> pullDishes (Task<QuerySnapshot> task, Dishes restaurant) {

        //Lista Pricipal
        List<Dishes> cardapio = new ArrayList<>();

        //Lista de Highlights
        List<Dishes> highlights = new ArrayList<>();

        //Lista de Categorias
        List<String> categorias = new ArrayList<>();

        //Lista de Index, dos primeiros pratos de uma categoria.
        List<Integer> indexCategorias = new ArrayList<>();

        //Posição do for.
        int position = 0;

        for (DocumentSnapshot document : task.getResult()) {

            //Nosso Objeto editavel de Dishes.
            Dishes dishes = document.toObject(Dishes.class);
            dishes.setID(document.getId());

            //Ambiente
            dishes.setReference(document.getReference());

            //Parente
            dishes.setCategoria(restaurant.getCategoria());
            dishes.setCidade(restaurant.getCidade());
            dishes.setDes(restaurant.getDes());
            dishes.setCidade(restaurant.getCidade());
            dishes.setEnder(restaurant.getEnder());
            dishes.setEstado(restaurant.getEstado());
            dishes.setID_restaurante(restaurant.getID_restaurante());
            dishes.setMedia(restaurant.getMedia());
            dishes.setNome(restaurant.getNome());
            dishes.setTaval(restaurant.getTaval());
            dishes.setUrlheader(restaurant.getUrlheader());
            dishes.setUrlicon(restaurant.getUrlicon());

            //Adiciona a Lista Principal
            cardapio.add(dishes);

            //Se for o primeiro resultado
            if (position==0){

                //Adiciona a Categora do prato a lista de categoria.
                categorias.add(cardapio.get(position).getCategory());
                //Adiciona a posição do primeiro prato da categoria na lista de index.
                indexCategorias.add(position);

            //Senão, Verifica se a cadegoria do prato atual, é diferente da anterior.
            } else if (!cardapio.get(position).getCategory().equals(cardapio.get(position - 1).getCategory())) {

                //Se o for, adiciona a nova categoria de prato a lista.
                categorias.add(cardapio.get(position).getCategory());
                //Salva na lista o index deste primeiro prato de uma nova categoria.
                indexCategorias.add(position);

            }

            //Se o item do cardapio for HighLight
            if (cardapio.get(cardapio.size() - 1).getIsHighlight() == 1) {

                //Adiciona ele a lista de HighLight.
                highlights.add(cardapio.get(cardapio.size() - 1));

                Log.d("DISHESsize", "onComplete: " + highlights.size());

                //Encerra-se o for, e portanto acrescentamos a Posição do for.
                position++;
            }
        }

        //Nosso Interpolador de Qual lista será Mandado.

    }

    public List<Integer> pullDishes (Task<QuerySnapshot> task, Dishes restaurant) {

        //Lista Pricipal
        List<Dishes> cardapio = new ArrayList<>();

        //Lista de Highlights
        List<Dishes> highlights = new ArrayList<>();

        //Lista de Categorias
        List<String> categorias = new ArrayList<>();

        //Lista de Index, dos primeiros pratos de uma categoria.
        List<Integer> indexCategorias = new ArrayList<>();

        //Posição do for.
        int position = 0;

        for (DocumentSnapshot document : task.getResult()) {

            //Nosso Objeto editavel de Dishes.
            Dishes dishes = document.toObject(Dishes.class);
            dishes.setID(document.getId());

            //Ambiente
            dishes.setReference(document.getReference());

            //Parente
            dishes.setCategoria(restaurant.getCategoria());
            dishes.setCidade(restaurant.getCidade());
            dishes.setDes(restaurant.getDes());
            dishes.setCidade(restaurant.getCidade());
            dishes.setEnder(restaurant.getEnder());
            dishes.setEstado(restaurant.getEstado());
            dishes.setID_restaurante(restaurant.getID_restaurante());
            dishes.setMedia(restaurant.getMedia());
            dishes.setNome(restaurant.getNome());
            dishes.setTaval(restaurant.getTaval());
            dishes.setUrlheader(restaurant.getUrlheader());
            dishes.setUrlicon(restaurant.getUrlicon());

            //Adiciona a Lista Principal
            cardapio.add(dishes);

            //Se for o primeiro resultado
            if (position==0){

                //Adiciona a Categora do prato a lista de categoria.
                categorias.add(cardapio.get(position).getCategory());
                //Adiciona a posição do primeiro prato da categoria na lista de index.
                indexCategorias.add(position);

            //Senão, Verifica se a cadegoria do prato atual, é diferente da anterior.
            } else if (!cardapio.get(position).getCategory().equals(cardapio.get(position - 1).getCategory())) {

                //Se o for, adiciona a nova categoria de prato a lista.
                categorias.add(cardapio.get(position).getCategory());
                //Salva na lista o index deste primeiro prato de uma nova categoria.
                indexCategorias.add(position);

            }

            //Se o item do cardapio for HighLight
            if (cardapio.get(cardapio.size() - 1).getIsHighlight() == 1) {

                //Adiciona ele a lista de HighLight.
                highlights.add(cardapio.get(cardapio.size() - 1));

                Log.d("DISHESsize", "onComplete: " + highlights.size());

                //Encerra-se o for, e portanto acrescentamos a Posição do for.
                position++;
            }
        }

        //Nosso Interpolador de Qual lista será Mandado.

    }
*/
    /*===============================================================================< Dishes >
    */


     /*===============================================================================< Restaurant >
     * Objeto
     *
     *   - FireStore
     *      * categoria
     *      * cidade
     *      * des
     *      * ender
     *      * estado
     *      * id_restaurante --- [Id do documento!]
     *      * media
     *      * nome
     *      * taval
     *      * urlheader
     *      * urlicon
     *
     *    - Ambiente
     *      * restaurantReference
     */

    /**
     * Seta uma lista de restaurantes, filtrada
     * em um campo por um texto.
     *
     * @param task - Task a ser percorrida.
     * @param filtro - Constante referente ao campo,
     *               ou tipo de filtro, por padrão é
     *               setado NO_FILTER.
     * @param text - texto geralmente a ser filtrado,
     *             pode variar sua função com o filtro.
     * @return - retorna a lista filtrada.
     */
    public static List<Restaurant> pullRestaurats (@NotNull Task<QuerySnapshot> task, int filtro, String text) {

        List<Restaurant> restaurantList = new ArrayList<>();

        for (DocumentSnapshot documentSnapshot : task.getResult()){

            //Firestore
            Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
            restaurant.setID_restaurante(documentSnapshot.getId());

            //Ambiente
            restaurant.setRestaurantReference(documentSnapshot.getReference());

            //addList
            restaurantList.add(restaurant);

            Log.d(TAG, "onComplete:  =====> " + documentSnapshot.getData());
        }

        //Caso Filtro
        switch (filtro){

            //Categoria
            case 1 :

                List<Restaurant> restaurantList1 = new ArrayList<>();
                for (Restaurant restaurant : restaurantList) if (restaurant.getCategoria().toLowerCase()
                        .contains(text.toLowerCase())) restaurantList1.add(restaurant);

                //Lista Filtrada.
                return restaurantList1;


        }

        return  restaurantList;
    }

    /**
     * Seta uma Lista de Restaurantes,
     * sem filtro.
     * @param task - Task a ser Percorrida
     * @return - Retorna a lista de restaurantes
     */
    @NotNull
    public static List<Restaurant> pullRestaurats (Task<QuerySnapshot> task) {
        return pullRestaurats(task, NO_FILTER, null);
    }

    /*
      =============================================================================</ Restaurant > */

    //Push

    //Constantes de Push
    public static final int /* Faz funcção no Firestore  */FIRESTORE = 0;
    public static final int /* Faz função no RealTime DB */REALTIME  = 1;

    private static DatabaseReference dbrealtime = FirebaseDatabase.getInstance().getReference();

    /**
     * String restaurante;
     * String mesa;
     * String hora;
     * String data;
     * String userId;
     *
     * itn estado = 0;
     */
    public static void pushCheckin(int onde, String restaurante, String mesa, String hora, String data, String userId){

        switch (onde) {

            case 0 :

                break;
            case 1 :
                Map<String, Object> pushValues = new HashMap<>();
                pushValues.put("restaurante", restaurante);
                pushValues.put("mesa", mesa);
                pushValues.put("hora", hora);
                pushValues.put("data", data);
                pushValues.put("userId", userId);
                pushValues.put("estado", 0);

                dbrealtime.child("checkin").child(userId).setValue(pushValues);
                break;

        }
    }



}
