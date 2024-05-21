package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.usecase.ICustomerUseCase;
import com.fiap.techchallenge.infrastructure.repository.postgres.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService implements ICustomerUseCase {

    @Autowired
    @Qualifier("PGCustomerRepository")
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setId(UUID.randomUUID());

        if(customerRepository.create(customer) == 1) return customer;
        return null;
    }

    @Override
    public List<Customer> getCustomerByCpf(String cpf) {
        return customerRepository.getByCpf(cpf);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAll();
    }

    @Override
    public PutCustomerDTO updateCustomer(PutCustomerDTO customer, String cpf) {
        if(customerRepository.update(customer, cpf) == 1) return customer;
        return null;
    }
}
