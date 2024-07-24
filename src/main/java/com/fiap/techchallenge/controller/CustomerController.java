package com.fiap.techchallenge.controller;


import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.customer.usecase.impl.CreateCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.GetCustomerByCPFUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.ListAllCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.UpdateCustomerUseCase;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;

import java.util.List;

public class CustomerController {
    private final GetCustomerByCPFUseCase getCustomerByCPFUseCase;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final ListAllCustomerUseCase listAllCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;


    public CustomerController(
            GetCustomerByCPFUseCase getCustomerByCPFUseCase,
            CreateCustomerUseCase createCustomerUseCase,
            ListAllCustomerUseCase listAllCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase
    ){
        this.getCustomerByCPFUseCase = getCustomerByCPFUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.listAllCustomerUseCase = listAllCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
    }

    public Customer getCustomerByCpf(String cpf){
        return getCustomerByCPFUseCase.execute(cpf);
    }

    public List<Customer> getAllCustomers(){
        return listAllCustomerUseCase.execute();
    }

    public Customer createCustomer(Customer customer) throws EntityAlreadyExistException, InvalidCpfException {
        return createCustomerUseCase.execute(customer);
    }

    public Customer updateCustomerByCpf(Customer customer, String cpf) throws EntityAlreadyExistException {
        if (updateCustomerUseCase.execute(customer) != null) {
            return getCustomerByCPFUseCase.execute(cpf);
        }
        return null;
    }
}
