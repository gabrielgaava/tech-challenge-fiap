package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer (@RequestBody Customer customer) {
        if(customerService.createCustomer(customer) != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);

        else return ResponseEntity.badRequest().build();
    }
}
