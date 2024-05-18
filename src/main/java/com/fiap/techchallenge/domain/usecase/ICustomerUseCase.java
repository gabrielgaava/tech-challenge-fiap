package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.domain.entity.Customer;

import java.util.List;

public interface ICustomerUseCase {

    Customer createCustomer (Customer customer);

    List<Customer> getAllCustomers (String cpf);
}
