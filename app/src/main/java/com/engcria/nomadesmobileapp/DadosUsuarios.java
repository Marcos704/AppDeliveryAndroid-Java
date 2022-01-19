package com.engcria.nomadesmobileapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DadosUsuarios {
   private String nomeUser;
   private String cpfUser;
   private String celularUser;
   private int    cidadeUser;
   private String ruaUser;
   private String numeroCasaUser;
   private String bairroUser;
   private String pontoRefUser;
   private String uidUsuario;

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public DadosUsuarios() {
    }
    public void salvar(){
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        firebase.child("dadosUsuarios").child(this.uidUsuario).setValue(this);
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getCpfUser() {
        return cpfUser;
    }

    public void setCpfUser(String cpfUser) {
        this.cpfUser = cpfUser;
    }

    public String getCelularUser() {
        return celularUser;
    }

    public void setCelularUser(String celularUser) {
        this.celularUser = celularUser;
    }

    public int getCidadeUser() {
        return cidadeUser;
    }

    public void setCidadeUser(int cidadeUser) {
        this.cidadeUser = cidadeUser;
    }

    public String getRuaUser() {
        return ruaUser;
    }

    public void setRuaUser(String ruaUser) {
        this.ruaUser = ruaUser;
    }

    public String getNumeroCasaUser() {
        return numeroCasaUser;
    }

    public void setNumeroCasaUser(String numeroCasaUser) {
        this.numeroCasaUser = numeroCasaUser;
    }

    public String getBairroUser() {
        return bairroUser;
    }

    public void setBairroUser(String bairroUser) {
        this.bairroUser = bairroUser;
    }

    public String getPontoRefUser() {
        return pontoRefUser;
    }

    public void setPontoRefUser(String pontoRefUser) {
        this.pontoRefUser = pontoRefUser;
    }
}
