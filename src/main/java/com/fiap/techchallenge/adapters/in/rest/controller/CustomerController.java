package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //todo  Como retornar tudo nessa merda
    @GetMapping("/{cpf}")
    public ResponseEntity<List<Customer>> getCustomer(@PathVariable String cpf) {
        var customers = customerService.getAllCustomers(cpf);

        if(customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }   return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer (@RequestBody Customer customer) {
        if(customerService.createCustomer(customer) != null) {
          return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } else return ResponseEntity.badRequest().build();
    }
}
