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
public class Driver implements Serializable {

    private Long id;

    private String email;
    private String password;
    private Long atualLatitude;
    private Long atualLongitude;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAtualLatitude() {
        return atualLatitude;
    }

    public void setAtualLatitude(Long atualLatitude) {
        this.atualLatitude = atualLatitude;
    }

    public Long getAtualLongitude() {
        return atualLongitude;
    }

    public void setAtualLongitude(Long atualLongitude) {
        this.atualLongitude = atualLongitude;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCar(String car) {
    }

    public void setAccount(String account) {
    }

    public Coordinate getAtualCoordinate() {
        return new Coordinate(atualLongitude, atualLatitude);
    }

}
