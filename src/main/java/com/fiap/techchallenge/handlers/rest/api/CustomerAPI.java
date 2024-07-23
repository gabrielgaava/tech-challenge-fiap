package com.fiap.techchallenge.handlers.rest.api;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.customer.usecase.impl.CreateCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.GetCustomerByCPFUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.ListAllCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.UpdateCustomerUseCase;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;
import com.fiap.techchallenge.handlers.rest.dto.CreateCustomerDTO;
import com.fiap.techchallenge.handlers.rest.dto.CustomerDTO;
import com.fiap.techchallenge.handlers.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.presenters.CustomerPresenter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Customer API")
@RestController
@RequestMapping("/customers")
public class CustomerAPI {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByCPFUseCase getCustomerByCPFUseCase;
    private final ListAllCustomerUseCase listAllCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    public CustomerAPI(
            CreateCustomerUseCase createCustomerUseCase,
            GetCustomerByCPFUseCase getCustomerByCPFUseCase,
            ListAllCustomerUseCase listAllCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerByCPFUseCase = getCustomerByCPFUseCase;
        this.listAllCustomerUseCase = listAllCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
    }

    @Operation(summary = "Search for a customer by CPF")
    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> getCustomerByCpf(@PathVariable String cpf)
    {
        var customer = getCustomerByCPFUseCase.execute(cpf);

        if(customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(CustomerPresenter.fromDomain(customer));
    }

    @Operation(summary = "List all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers()
    {
        var customers = listAllCustomerUseCase.execute();

        if(customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CustomerDTO> response = customers.stream()
                .map(CustomerPresenter::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new customers")
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer (@Valid @RequestBody CreateCustomerDTO request) throws InvalidCpfException, EntityAlreadyExistException
    {
        Customer newCustomer = new Customer(null,request.getCpf(), request.getName(), request.getEmail());

        if(createCustomerUseCase.execute(newCustomer) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(CustomerPresenter.fromDomain(newCustomer));
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update customers data")
    @PutMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> updateCustomerByCpf(@Valid @RequestBody PutCustomerDTO customerDTO, @PathVariable String cpf) throws EntityAlreadyExistException
    {

        Customer customer = CustomerPresenter.toDomain(customerDTO, cpf);

        if(updateCustomerUseCase.execute(customer) != null) {
            var retrievedCustomer = getCustomerByCPFUseCase.execute(cpf);
            if (retrievedCustomer != null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(CustomerPresenter.fromDomain(retrievedCustomer));
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
