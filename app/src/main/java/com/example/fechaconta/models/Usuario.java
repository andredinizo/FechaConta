package com.example.fechaconta.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Usuario {

    //Atributos
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String telefone;
    private DatabaseReference dbrealtime = FirebaseDatabase.getInstance().getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public Usuario() {}


    //METODOS

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void FazCheckin(Restaurant restaurante, Mesa mesa){


        //CRIA INSTANCIA DE CHECK-IN
        String restauranteId;
        String mesaId;
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
        mesaId = mesa.getNu_mesa();

        //ID USUARIO
        Usuario.CheckIn checkin = new Usuario.CheckIn(restauranteId, mesaId, hora, data, user.getUid());


        //CRIA INSTANCIA DE CHECKIN FIREBASE REALTIME COM ID DO USUARIO

        dbrealtime.child("checkin").child(user.getUid()).setValue(checkin);




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


    public void criarUsuarioBD (Usuario usuario){

        db.collection("User").document(user.getUid()).set(usuario);
    }

    public static class CheckIn{

        private String restaurante;
        private String mesa;
        private String hora;
        private String data;
        private String id;
        private String userId;
        private int estado; // 0 - Esperando confirmação; 1- Check-in Aceito ; 2- Check-in Recusado; 3 - Tempo de Checkin esgotado ; 4- Check-in Concluido c/ Pagamento; 5 - Checkin concluido s/ pagamento

        public CheckIn(){/*CONSTRUTOR VAZIO PARA CRIAR OBJETO A PRTIR DO FIREBASE*/}

        public CheckIn(String restaurante, String mesa, String hora, String data, String userId) {
            this.restaurante = restaurante;
            this.mesa = mesa;
            this.hora = hora;
            this.data = data;
            this.userId = userId;
            this.estado = 0;
        }

        public void AtualizaCheckin(CheckIn checkIn){
             DatabaseReference dbrealtime = FirebaseDatabase.getInstance().getReference();
             FirebaseFirestore db = FirebaseFirestore.getInstance();
             FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            dbrealtime.child("checkin").child(user.getUid()).setValue(checkIn);



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

            switch (CheckIn.getEstado()) {

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


        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        public String getRestaurante() {
            return restaurante;
        }

        public void setRestaurante(String restaurante) {
            this.restaurante = restaurante;
        }

        public String getMesa() {
            return mesa;
        }

        public void setMesa(String mesa) {
            this.mesa = mesa;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

}
