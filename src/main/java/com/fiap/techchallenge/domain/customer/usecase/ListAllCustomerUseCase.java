package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;

import java.util.List;

public class ListAllCustomerUseCase {

  private final CustomerGateway customerRepository;

  public ListAllCustomerUseCase(CustomerGateway customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> execute() {
    return customerRepository.getAll();
  }

}
