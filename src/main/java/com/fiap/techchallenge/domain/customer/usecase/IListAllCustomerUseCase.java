package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;

import java.util.List;

public interface IListAllCustomerUseCase {

  List<Customer> execute();

}
