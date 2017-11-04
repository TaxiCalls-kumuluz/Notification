/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxicalls.notification.model;

import java.io.Serializable;

/**
 *
 * @author romulo
 */
public class Passenger implements Serializable {

    private Long id;
    private String email;
    private String password;

    protected Passenger() {
    }

    public Passenger(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
