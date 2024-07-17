package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;

public interface IUpdateCustomerUseCase {

  public Customer execute(Customer customer) throws EntityAlreadyExistException;

}
