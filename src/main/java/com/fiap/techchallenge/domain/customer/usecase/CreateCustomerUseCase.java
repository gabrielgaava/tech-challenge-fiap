package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;

import java.util.UUID;
import java.util.regex.Pattern;

public class CreateCustomerUseCase  {

  private final CustomerGateway customerGateway;

  public CreateCustomerUseCase(CustomerGateway customerGateway) {
    this.customerGateway = customerGateway;
  }

  public Customer execute(Customer customer, CustomerGateway customerGateway) throws InvalidCpfException, EntityAlreadyExistException {
    customer.setId(UUID.randomUUID());

    // Validating CPF
    if(Pattern.matches("\\d{11}", customer.getCpf())) {
      int createFlag = customerGateway.create(customer);
      if (createFlag == 1) return customer;
      else if(createFlag == -1) throw new EntityAlreadyExistException("CPF or email already exist and must be unique");
    }

    else {
      throw new InvalidCpfException();
    }

    return null;
  }

}
