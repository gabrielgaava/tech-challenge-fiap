package com.fiap.techchallenge.domain.customer.usecase.impl;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.customer.CustomerRepositoryPort;
import com.fiap.techchallenge.domain.customer.usecase.IUpdateCustomerUseCase;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;


public class UpdateCustomerUseCase implements IUpdateCustomerUseCase {

  private final CustomerRepositoryPort customerRepository;

  public UpdateCustomerUseCase(CustomerRepositoryPort customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(Customer customer) throws EntityAlreadyExistException {
    int updateFlag = customerRepository.update(customer);

    if (updateFlag == 1)
      return customer;

    else if(updateFlag == -1)
      throw new EntityAlreadyExistException("CPF or email already exist and must be unique");

    return null;
  }

}
