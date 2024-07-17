package com.fiap.techchallenge.domain.customer.usecase;

import com.fiap.techchallenge.domain.customer.Customer;

public interface IGetCustomerByCPFUseCase {

  Customer execute(String cpf);

}
