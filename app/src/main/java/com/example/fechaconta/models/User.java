package com.example.fechaconta.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *  = Funcões do Usuario =
 *
 * - Type  retirn  nome             : description
 * -       void    fazCheckin       : em um restaurante e mesa
 * - staic boolean verificaCheckin  : verifica se o usuario fez checkIn e Valida
 *
 */
public class User {

    //Atributos
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;




    //Metodos

    /**
     *
     * @param restaurante
     * @param mesa
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void FazCheckin(Restaurant restaurante, Mesa mesa){

        String restauranteId;
        String mesaId;
        String data;
        String hora;

        DateTimeFormatter dataformato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaformato = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime dateTime = LocalDateTime.now();

        data = dateTime.format(dataformato);
        hora = dateTime.format(horaformato);

        restauranteId = restaurante.getID_restaurante();
        mesaId = mesa.getNu_mesa();

                                                                        //ID USUARIO
        CheckIn Checkin = new CheckIn(restauranteId, mesaId, hora, data, this.id);

    }

    //Getter e Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    static class CheckIn{

        private String restaurante;
        private String mesa;
        private String hora;
        private String data;
        private String id;
        private String userId;

        public CheckIn(String restaurante, String mesa, String hora, String data, String userId) {
            this.restaurante = restaurante;
            this.mesa = mesa;
            this.hora = hora;
            this.data = data;
            this.userId = userId;
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
