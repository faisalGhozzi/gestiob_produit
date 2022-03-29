/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.entity;

import java.util.Date;


public class Tutorial {
    private int id;
    private String video;
    private String image;
    private Date dateTuto;
    private String category;
    private String titre;
    private String description;
    private double prix;

    public Tutorial(int id, String video, String image, Date dateTuto, String category, String titre, String description, double prix) {
        this.id = id;
        this.video = video;
        this.image = image;
        this.dateTuto = dateTuto;
        this.category = category;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
    }

    public Tutorial(String video, String image, Date dateTuto, String category, String titre, String description, double prix) {
        this.video = video;
        this.image = image;
        this.dateTuto = dateTuto;
        this.category = category;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
    }

    public Tutorial() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateTuto() {
        return dateTuto;
    }

    public void setDateTuto(Date dateTuto) {
        this.dateTuto = dateTuto;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    @Override
    public String toString() {
        return "Tutorial{" + "video=" + video + ", image=" + image + ", dateTuto=" + dateTuto + ", category=" + category + ", titre=" + titre + ", description=" + description + ", prix=" + prix + '}';
    }
    
}
