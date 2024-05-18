package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Customer;

import java.util.List;

public interface ICustomerRepository {

    int create (Customer customer);

    List<Customer> getAll(String cpf);
}
