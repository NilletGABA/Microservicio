package com.paymentchain.customer.entities;


import java.util.List;
import javax.persistence.CascadeType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String name;
    private String phone;
    private String iban;
    private String surname;
    private String address;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "customer", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<CustomerProduct> products;
    @Transient
    private List<?> transactions;
}
