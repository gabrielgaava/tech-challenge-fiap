package com.fiap.techchallenge.presenters;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.handlers.rest.dto.CustomerDTO;
import com.fiap.techchallenge.handlers.rest.dto.PutCustomerDTO;

public abstract class CustomerPresenter {

  public static CustomerDTO fromDomain(final Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(customer.getId());
    customerDTO.setCpf(customer.getCpf());
    customerDTO.setName(customer.getName());
    customerDTO.setEmail(customer.getEmail());
    return  customerDTO;
  }

  public static Customer toDomain(final PutCustomerDTO dto, String cpf) {
    Customer customer = new Customer();
    customer.setName(dto.getName());
    customer.setEmail(dto.getEmail());
    customer.setCpf(cpf);

    return  customer;
  }

}
