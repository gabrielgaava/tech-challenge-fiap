package com.fiap.techchallenge.domain.customer.usecase.impl;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.domain.customer.usecase.IListAllCustomerUseCase;

import java.util.List;

public class ListAllCustomerUseCase implements IListAllCustomerUseCase {

  private final CustomerGateway customerRepository;

  public ListAllCustomerUseCase(CustomerGateway customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> execute() {
    return customerRepository.getAll();
  }

}
