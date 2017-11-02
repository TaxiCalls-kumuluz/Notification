/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxicalls.notification.resources;

/**
 *
 * @author romulo
 */
public class CheckNotificationsRequest {

    private String entity;
    private Long id;

    public CheckNotificationsRequest() {
    }

    public String getEntity() {
        return entity;
    }

    public Long getId() {
        return id;
    }

}
