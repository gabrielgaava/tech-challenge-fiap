package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepositoryPort {
    int create (Customer customer);
    Customer getByCpf(String cpf);
    Customer getByID(UUID id);
    List<Customer> getAll();
    int update(PutCustomerDTO customer, String cpf);
}
