package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerGateway {
    int create (Customer customer);
    Customer getByCpf(String cpf);
    Customer getByID(UUID id);
    List<Customer> getAll();
    int update(Customer customer);
}
