package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;

import java.util.List;

public class ListAllCustomerUseCase {

  private final CustomerGateway customerGateway;

  public ListAllCustomerUseCase(CustomerGateway customerGateway) {
    this.customerGateway = customerGateway;
  }

  public List<Customer> execute(CustomerGateway customerGateway) {
    return customerGateway.getAll();
  }

}
