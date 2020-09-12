package com.example.fechaconta.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fechaconta.utilitys.Aplotoso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class Usuario {

    //Atributos
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String telefone;
    private String photoUrl;
    private DatabaseReference dbrealtime = FirebaseDatabase.getInstance().getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public Usuario() {
    }


    //METODOS


    public void FazCheckin(Restaurant restaurante, Mesa mesa) {


        //CRIA INSTANCIA DE CHECK-IN
        String restauranteId;
        String nmesa;
        String data;
        String hora;



        if (Build.VERSION.SDK_INT >= 26){
            DateTimeFormatter dataformato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter horaformato = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime dateTime = LocalDateTime.now();
            data = dateTime.format(dataformato);
            hora = dateTime.format(horaformato);
        } else
        {
            SimpleDateFormat dataformato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaformato = new SimpleDateFormat("HH:mm");

            data = dataformato.format(Calendar.getInstance().getTime());
            hora = horaformato.format(Calendar.getInstance().getTime());
        }


        restauranteId = restaurante.getID_restaurante();
        nmesa = mesa.getNu_mesa();

        //ID USUARIO

        if (CheckIn.getInstance() == null) {
            Usuario.CheckIn checkin = new Usuario.CheckIn(restauranteId, nmesa, hora, data, user.getUid(), mesa.getUser_id(), mesa.getMesaId());
        } else {

            Usuario.CheckIn checkin = CheckIn.getInstance();
            checkin.setRestaurante(restauranteId);
            checkin.setMesa(nmesa);
            checkin.setHora(hora);
            checkin.setData(data);
            checkin.setUserId(user.getUid());
            checkin.setListaUsuarioMesa(mesa.getUser_id());
            checkin.setMesaId(mesa.getMesaId());

        }

        //CRIA INSTANCIA DE CHECKIN FIREBASE REALTIME COM ID DO USUARIO

        dbrealtime.child("checkin").child(user.getUid()).setValue(CheckIn.getInstance());

        List<String> listaUsuariosMesaAtualizada = mesa.getUser_id();
        listaUsuariosMesaAtualizada.add(user.getUid());
        //ATUALIZA LISTA DE USUARIO NA MESA FIRESTORE
        db.collection("Restaurant").document(restauranteId).collection("Mesas").document(mesa.getMesaId()).update("user_id",listaUsuariosMesaAtualizada);

        //Atualiza respectivos Check-ins no realTime
        CheckIn.getInstance().setListaUsuarioMesa(listaUsuariosMesaAtualizada);

        for(int position = 0 ;position < listaUsuariosMesaAtualizada.size(); position++){

            dbrealtime.child("checkin").child(listaUsuariosMesaAtualizada.get(position)).setValue(CheckIn.getInstance());

        }


    }

    //Getter e Setter

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void criarUsuarioBD(Usuario usuario) {

        db.collection("User").document(user.getUid()).set(usuario);
    }

    public static class CheckIn {

        static private String restaurante;
        static private String mesa;
        static private String hora;
        static private String data;
        static private String id;
        static private String mesaId;
        static private String userId;
        static private List<String> listaUsuarioMesa;
        static private int estado; // 0 - Esperando confirmação; 1- Check-in Aceito ; 2- Check-in Recusado; 3 - Tempo de Checkin esgotado ; 4- Check-in Concluido c/ Pagamento; 5 - Checkin concluido s/ pagamento

        static private DatabaseReference dbrealtime = FirebaseDatabase.getInstance().getReference();
        static private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        static private volatile CheckIn sCheckin;

        private CheckIn() {/*CONSTRUTOR VAZIO PARA CRIAR OBJETO A PRTIR DO FIREBASE*/}

        private CheckIn(String restaurante, String mesa, String hora, String data, String userId, List<String> listuserId, String mesaId) {

            CheckIn.restaurante = restaurante;
            CheckIn.mesa = mesa;
            CheckIn.hora = hora;
            CheckIn.data = data;
            CheckIn.userId = userId;
            CheckIn.estado = 0;
            CheckIn.listaUsuarioMesa = listuserId;
            CheckIn.mesaId = mesaId;

            if (sCheckin != null) {
                throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
            }

        }

        public static CheckIn getInstance() {
            if (sCheckin == null) {

                synchronized (CheckIn.class) {
                    if (sCheckin == null) {
                        sCheckin = new CheckIn();

                    }

                }

            }

            return sCheckin;
        }

        protected CheckIn readResolve() {
            return getInstance();
        }

        public void AtualizaCheckin() {

            assert user != null;
            dbrealtime.child("checkin").child(user.getUid()).setValue(this);

        }

        public static void adicionarPratoComanda(Dishes dishes){
            if(CheckIn.verificaCheckIn())
                if(CheckIn.getInstance().getRestaurante().equals(dishes.getID_restaurante()))
                    Aplotoso.pushPedido(user.getUid(), dishes);
            }

        /** Verifica se posso ou não prosseguir
         * segundo o estado do CheckIn.
         *
         *  =-=-+ Estados +-=-=
         * 0 - Esperando confirmação;
         * 1 - Check-in Aceito;
         * 2 - Check-in Recusado;
         * 3 - Tempo de Checkin esgotado;
         * 4 - Check-in Concluido c/ Pagamento;
         * 5 - Checkin concluido s/ pagamento
         *
         * @return - True - False.
         */
        public static boolean verificaCheckIn () {

            switch (CheckIn.estado) {

                case 0 :
                case 2 :
                case 3 :
                case 4 :
                case 5 :
                    return false;

                case 1 :
                    return true;


            }

            return false;
        }

        //Getter and Setter


        public String getMesaId() {
            return mesaId;
        }

        public void setMesaId(String mesaId) {
            CheckIn.mesaId = mesaId;
        }

        public List<String> getListaUsuarioMesa() {
            return listaUsuarioMesa;
        }

        public void setListaUsuarioMesa(List<String> listaUsuarioMesa) {
            CheckIn.listaUsuarioMesa = listaUsuarioMesa;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            CheckIn.estado = estado;
        }

        public String getRestaurante() {
            return restaurante;
        }

        public void setRestaurante(String restaurante) {
            CheckIn.restaurante = restaurante;
        }

        public String getMesa() {
            return mesa;
        }

        public void setMesa(String mesa) {
            CheckIn.mesa = mesa;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            CheckIn.hora = hora;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            CheckIn.data = data;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            CheckIn.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            CheckIn.userId = userId;
        }
    }

}
