package com.paymentchain.billing.controller;

import com.paymentchain.billing.entities.Invoice;
import com.paymentchain.billing.respository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class InvoiceRestController {

    @Autowired
    InvoiceRepository invoiceRepository;

    @GetMapping()
    public List<Invoice> list(){
        return invoiceRepository.findAll();
    }
    @GetMapping("/{id}")
    public Invoice get(@PathVariable String id){
        return null;
    }
    @PutMapping("{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Invoice input){
        return null;
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> post(@RequestBody Invoice input){
        Invoice save = invoiceRepository.save(input);
        return ResponseEntity.ok(save);
    }
    @DeleteMapping("/id")
    public ResponseEntity<?> delete(@PathVariable String id){
        return null;
    }

}
