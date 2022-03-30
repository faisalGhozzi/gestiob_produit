/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.entity;

import java.util.List;

public class User {
    private int id;
    private String email;
    private String nom;
    private String prenom;
    
    public User(){
    }

    public User(int id, String email, String nom, String prenom) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(String email, String nom, String prenom) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNomComplet(){
        return this.nom+" "+this.prenom;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + '}';
    }
    
    
}
