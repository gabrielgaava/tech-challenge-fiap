package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;

public class GetCustomerByCPFUseCase  {

  private final CustomerGateway customerGateway;

  public GetCustomerByCPFUseCase(CustomerGateway customerGateway) {
    this.customerGateway = customerGateway;
  }

  public Customer execute(String cpf, CustomerGateway customerGateway) {
    return customerGateway.getByCpf(cpf);
  }

}
