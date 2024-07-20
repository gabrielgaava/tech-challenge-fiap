package com.fiap.techchallenge.domain.customer.usecase.impl;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.customer.CustomerRepositoryPort;
import com.fiap.techchallenge.domain.customer.usecase.IListAllCustomerUseCase;

import java.util.List;

public class ListAllCustomerUseCase implements IListAllCustomerUseCase {

  private final CustomerRepositoryPort customerRepository;

  public ListAllCustomerUseCase(CustomerRepositoryPort customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> execute() {
    return customerRepository.getAll();
  }

}
