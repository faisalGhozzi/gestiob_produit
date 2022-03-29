/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.entity;

public class Panier {
    private int id;
    private int qte;
    private double total;
    private Course course;
    private Tutorial tutorial;
    private User user;

    /**
     *
     * @param id
     * @param qte
     * @param total
     * @param course
     */
    public Panier(int id, int qte, double total, User user) {
        this.id = id;
        this.qte = qte;
        this.total = total;
        this.course = null;
        this.tutorial = null;
        this.user = user;
    }

    public Panier(int qte, double total, User user) {
        this.qte = qte;
        this.total = total;
        this.course = null;
        this.tutorial = null;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Panier{" + "qte=" + qte + ", total=" + total + ", course=" + course + ", tutorial=" + tutorial + ", user=" + user + '}';
    } 
}
