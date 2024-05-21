package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;
import java.util.List;

public interface ICustomerRepository {
    int create (Customer customer);
    Customer getByCpf(String cpf);
    List<Customer> getAll();
    int update(PutCustomerDTO customer, String cpf);
    int delete(String cpf);
}
