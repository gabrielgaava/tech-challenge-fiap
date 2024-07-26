package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;

public class GetCustomerByCPFUseCase  {

  private final CustomerGateway customerRepository;

  public GetCustomerByCPFUseCase(CustomerGateway customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(String cpf) {
    return customerRepository.getByCpf(cpf);
  }

}
