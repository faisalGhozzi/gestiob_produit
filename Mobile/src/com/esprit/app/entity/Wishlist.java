/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.entity;

public class Wishlist {
    private int id;
    private int user_id;
    private int transport_id;

    public Wishlist() {
    }

    public Wishlist(int id, int user_id, int transport_id) {
        this.id = id;
        this.user_id = user_id;
        this.transport_id = transport_id;
    }

    public Wishlist(int user_id, int transport_id) {
        this.user_id = user_id;
        this.transport_id = transport_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(int transport_id) {
        this.transport_id = transport_id;
    }

    @Override
    public String toString() {
        return "Wishlist{" + "id=" + id + ", user_id=" + user_id + ", transport_id=" + transport_id + '}';
    }
}
