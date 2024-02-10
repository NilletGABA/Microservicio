/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author Nillet Gamboa
 */
@Data
@Entity
public class CustomerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long productId;
    @Transient
    private String productName;
    @JsonIgnore//Cuando el framework va a recuperar la informacion de la base de datos
    // de un customer que tiene un listado (CustomerProduct) se forma ciclos infinitos para ello se evita con JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Customer.class)
    @JoinColumn(name="customerId",nullable=true)
    private Customer customer;
}
