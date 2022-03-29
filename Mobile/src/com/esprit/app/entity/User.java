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
    private List<String> roles;
    private String password;
    private String firstname;
    private String lastname;
    private boolean isVerified;
    
    public User(){
    }
    
    public User(int id, String email, List<String> roles, String password, String firstname, String lastname, boolean isVerified) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isVerified = isVerified;
    }

    public User(String email, List<String> roles, String password, String firstname, String lastname) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isVerified = false;
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

    public List<String> getRoles() {
        if(roles.isEmpty()){
            roles.add("ROLE_USER");
        }
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", roles=" + roles + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + '}';
    }
    
    
    
    
}
