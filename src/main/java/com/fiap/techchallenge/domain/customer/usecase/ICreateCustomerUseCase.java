package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;

public interface ICreateCustomerUseCase {

  Customer execute(Customer customer) throws InvalidCpfException, EntityAlreadyExistException;

}
