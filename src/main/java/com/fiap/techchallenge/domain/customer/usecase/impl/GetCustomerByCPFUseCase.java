package com.fiap.techchallenge.domain.customer.usecase.impl;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.domain.customer.usecase.IGetCustomerByCPFUseCase;

public class GetCustomerByCPFUseCase implements IGetCustomerByCPFUseCase {

  private final CustomerGateway customerRepository;

  public GetCustomerByCPFUseCase(CustomerGateway customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(String cpf) {
    return customerRepository.getByCpf(cpf);
  }

}
