/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.entity;

public class Categorie {
    private int id;
    private String nom;
    private String boitevitesse;

    public Categorie(int id, String nom, String boitevitesse) {
        this.id = id;
        this.nom = nom;
        this.boitevitesse = boitevitesse;
    }

    public Categorie(String nom, String boitevitesse) {
        this.nom = nom;
        this.boitevitesse = boitevitesse;
    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBoitevitesse() {
        return boitevitesse;
    }

    public void setBoitevitesse(String boitevitesse) {
        this.boitevitesse = boitevitesse;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", nom=" + nom + ", boitevitesse=" + boitevitesse + '}';
    }
    
    
}
