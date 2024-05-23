package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.exception.EntityAlreadyExistException;
import com.fiap.techchallenge.domain.exception.InvalidCpfException;
import com.fiap.techchallenge.domain.usecase.ICustomerUseCase;
import com.fiap.techchallenge.adapters.out.database.postgress.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CustomerService implements ICustomerUseCase {

    @Autowired
    @Qualifier("PGCustomerRepository")
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) throws InvalidCpfException, EntityAlreadyExistException {
        customer.setId(UUID.randomUUID());

       if(Pattern.matches("\\d{11}", customer.getCpf())) {
           int createFlag = customerRepository.create(customer);
           if (createFlag == 1) return customer;
           else if(createFlag == -1) throw new EntityAlreadyExistException("CPF or email already exist and must be unique");
       } else {
           throw new InvalidCpfException();
       }

        return null;
    }

    @Override
    public Customer getCustomerByCpf(String cpf) {
        return customerRepository.getByCpf(cpf);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAll();
    }

    @Override
    public PutCustomerDTO updateCustomer(PutCustomerDTO customer, String cpf) throws EntityAlreadyExistException {
        int updateFlag = customerRepository.update(customer, cpf);

        if (updateFlag == 1)
            return customer;

        else if(updateFlag == -1)
            throw new EntityAlreadyExistException("CPF or email already exist and must be unique");

        return null;
    }
}
