package com.fiap.techchallenge.adapters.out.database.postgress;

import com.fiap.techchallenge.adapters.in.rest.dto.PutCustomerDTO;
import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.repository.CustomerRepositoryPort;
import com.fiap.techchallenge.adapters.out.database.postgress.mapper.CustomerMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Getter
@Repository("PGCustomerRepository")
public class CustomerRepository implements CustomerRepositoryPort {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create (Customer customer) {
        String sql = "INSERT INTO public.customer (id, cpf, name, email) VALUES (?, ?, ?, ?)";

        try {
          return jdbcTemplate.update(
              sql,
              customer.getId(),
              customer.getCpf(),
              customer.getName(),
              customer.getEmail()
          );
        }
        catch (DuplicateKeyException e) {
          return -1;
        }
    }

    @Override
    public Customer getByCpf(String cpf) {
        String sql = "SELECT * FROM customer WHERE cpf = ?";

        try {
          return jdbcTemplate.queryForObject(
            sql,
            CustomerMapper.listMapper,
            cpf);
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT * FROM customer ORDER BY name";
        return jdbcTemplate.query(
                sql,
                CustomerMapper.listMapper);
    }

    @Override
    public int update(PutCustomerDTO customer, String cpf) {
        String sql = "UPDATE customer SET name = ?, email = ? WHERE cpf = ?";

        try {
          return jdbcTemplate.update(
              sql,
              customer.getName(),
              customer.getEmail(),
              cpf
          );
        }
        catch (DuplicateKeyException e) {
          return -1;
        }
    }
}
