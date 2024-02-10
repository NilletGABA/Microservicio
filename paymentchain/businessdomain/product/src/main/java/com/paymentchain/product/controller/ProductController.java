/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.product.controller;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nillet Gamboa
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductRepository productRepository;
    
    @GetMapping("/list")
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    @GetMapping("/{id}")
    public Product get(@PathVariable long id){
        return productRepository.findById(id).get();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Product input){
        Product find = productRepository.findById(id).get();
        if(find!=null){
            find.setCode(input.getCode());
            find.setName(input.getName());
        }
        Product save = productRepository.save(find);
        return ResponseEntity.ok(save);
    }
    @PostMapping("/create")
    public ResponseEntity<?> post(@RequestBody Product input){
        //Defini la introduccion para guardar (input es la respuesta cuando se invoca)
        //devuelve una entidad tipo customer
        Product save = productRepository.save(input);
        //Responda con un ok que es una respuesta http
        return ResponseEntity.ok(save);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        Optional<Product> findById = productRepository.findById(id);
        if(findById.get()!=null){
            productRepository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }
    
}
