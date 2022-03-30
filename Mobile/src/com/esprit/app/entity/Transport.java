/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.entity;

public class Transport {
    private int id;
    private String matricule;
    private String marque;
    private String model;
    private int nbsiege;
    private String image;
    private float prix;
    private int categorie;
    private int user;

    public Transport() {
    }

    public Transport(int id, String matricule, String marque, String model, int nbsiege, String image, float prix, int categorie, int user) {
        this.id = id;
        this.matricule = matricule;
        this.marque = marque;
        this.model = model;
        this.nbsiege = nbsiege;
        this.image = image;
        this.prix = prix;
        this.categorie = categorie;
        this.user = user;
    }

    public Transport(String matricule, String marque, String model, int nbsiege, String image, float prix, int categorie, int user) {
        this.matricule = matricule;
        this.marque = marque;
        this.model = model;
        this.nbsiege = nbsiege;
        this.image = image;
        this.prix = prix;
        this.categorie = categorie;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNbsiege() {
        return nbsiege;
    }

    public void setNbsiege(int nbsiege) {
        this.nbsiege = nbsiege;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Transport{" + "id=" + id + ", matricule=" + matricule + ", marque=" + marque + ", model=" + model + ", nbsiege=" + nbsiege + ", image=" + image + ", prix=" + prix + ", categorie=" + categorie + ", user=" + user + '}';
    }
    
    
}
