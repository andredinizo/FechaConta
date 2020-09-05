package com.example.fechaconta.models;

import com.google.firebase.firestore.FirebaseFirestore;

public class Usuario {

    //Atributos
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String telefone;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public Usuario() {}

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

        db.collection("User").add(usuario);
    }
}
