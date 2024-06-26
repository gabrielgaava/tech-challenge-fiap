package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateCustomerDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.CustomerDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.adapters.in.rest.mapper.CustomerMapper;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;
import com.fiap.techchallenge.domain.service.CustomerService;
import com.fiap.techchallenge.domain.usecase.ICustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Customer Controller")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ICustomerUseCase customerService;

    public CustomerController(DataSource dataSource) {
        this.customerService = new CustomerService(dataSource);
    }

    @Operation(summary = "Search for a customer by CPF")
    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> getCustomerByCpf(@PathVariable String cpf)
    {
        var customer = customerService.getCustomerByCpf(cpf);

        if(customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(CustomerMapper.fromDomain(customer));
    }

    @Operation(summary = "List all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers()
    {
        var customers = customerService.getAllCustomers();

        if(customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CustomerDTO> customersDTO = new ArrayList<>();
        customers.forEach((customer)->{
            if (customer != null){
                customersDTO.add(CustomerMapper.fromDomain(customer));
            }
        });

        return ResponseEntity.ok(customersDTO);
    }

    @Operation(summary = "Create a new customers")
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer (@Valid @RequestBody CreateCustomerDTO request) throws InvalidCpfException, EntityAlreadyExistException
    {
        Customer newCustomer = new Customer(null,request.getCpf(), request.getName(), request.getEmail());

        if(customerService.createCustomer(newCustomer) != null) {
          return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.fromDomain(newCustomer));
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update customers data")
    @PutMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> updateCustomerByCpf(@Valid @RequestBody PutCustomerDTO customerDTO, @PathVariable String cpf) throws EntityAlreadyExistException
    {

        if(customerService.updateCustomer(customerDTO, cpf) != null) {
            var retrievedCustomer = customerService.getCustomerByCpf(cpf);
            if (retrievedCustomer != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomerMapper.fromDomain(retrievedCustomer));
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
