package com.paymentchain.customer.repository;

import com.paymentchain.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    
    
    @Query("SELECT c FROM Customer c where c.code= ?1")
    public Customer finByCode(String code);
    
    @Query("SELECT c FROM Customer c where c.iban= ?1")
    public Customer finByAccount(String iban);
    
}
