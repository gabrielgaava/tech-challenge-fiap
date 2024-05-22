package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;

import java.util.List;

public interface ICustomerUseCase {

    Customer createCustomer(Customer customer) throws InvalidCpfException;

    Customer getCustomerByCpf(String cpf);

    List<Customer> getAllCustomers();

    PutCustomerDTO updateCustomer(PutCustomerDTO customer, String cpf);

    Boolean deleteCustomer(String cpf);
}