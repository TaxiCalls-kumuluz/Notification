/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxicalls.notification.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author romulo
 */
@Entity
@NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date sentTime;
    private String fromEntity;
    private Integer fromId;
    private String toEntity;
    private Integer toId;
    private String content;

    public Notification() {
    }

    public Notification(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
