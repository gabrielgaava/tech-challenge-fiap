package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateCustomerDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Customer Controller")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Search for a customer by ID")
    @GetMapping("/{cpf}")
    public ResponseEntity<Customer> getCustomerByCpf(@PathVariable String cpf)
    {
        var customers = customerService.getCustomerByCpf(cpf);

        if(customers == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "List all customers")
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers()
    {
        var customers = customerService.getAllCustomers();

        if(customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }   return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Create a new customers")
    @PostMapping
    public ResponseEntity<Customer> createCustomer (@RequestBody CreateCustomerDTO request)
    {
        Customer customer = new Customer(null,request.getCpf(), request.getName(), request.getEmail());

        if(customerService.createCustomer(customer) != null) {
          return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update customers data")
    @PutMapping("/{cpf}")
    public ResponseEntity<Customer> updateCustomerByCpf(@RequestBody PutCustomerDTO customerDTO, @PathVariable String cpf)
    {
        if(customerService.updateCustomer(customerDTO, cpf) != null) {
            var getCustomer = customerService.getCustomerByCpf(cpf);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(getCustomer);
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Delete a customers from database")
    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String cpf)
    {
        if(customerService.deleteCustomer(cpf)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }
}
