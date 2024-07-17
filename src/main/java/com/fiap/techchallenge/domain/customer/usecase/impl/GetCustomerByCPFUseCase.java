package com.fiap.techchallenge.domain.customer.usecase.impl;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.customer.CustomerRepositoryPort;
import com.fiap.techchallenge.domain.customer.usecase.IGetCustomerByCPFUseCase;

public class GetCustomerByCPFUseCase implements IGetCustomerByCPFUseCase {

  private final CustomerRepositoryPort customerRepository;

  public GetCustomerByCPFUseCase(CustomerRepositoryPort customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(String cpf) {
    return customerRepository.getByCpf(cpf);
  }

}
