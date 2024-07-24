package com.fiap.techchallenge.domain.customer.usecase.impl;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.domain.customer.usecase.ICreateCustomerUseCase;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;

import java.util.UUID;
import java.util.regex.Pattern;

public class CreateCustomerUseCase implements ICreateCustomerUseCase {

  private final CustomerGateway customerRepository;

  public CreateCustomerUseCase(CustomerGateway customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(Customer customer) throws InvalidCpfException, EntityAlreadyExistException {
    customer.setId(UUID.randomUUID());

    // Validating CPF
    if(Pattern.matches("\\d{11}", customer.getCpf())) {
      int createFlag = customerRepository.create(customer);
      if (createFlag == 1) return customer;
      else if(createFlag == -1) throw new EntityAlreadyExistException("CPF or email already exist and must be unique");
    }

    else {
      throw new InvalidCpfException();
    }

    return null;
  }

}
