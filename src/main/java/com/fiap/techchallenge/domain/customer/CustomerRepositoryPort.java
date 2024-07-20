package com.fiap.techchallenge.domain.customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepositoryPort {
    int create (Customer customer);
    Customer getByCpf(String cpf);
    Customer getByID(UUID id);
    List<Customer> getAll();
    int update(Customer customer);
}
