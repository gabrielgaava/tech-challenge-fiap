package com.fiap.techchallenge.drivers.postgresql;

import com.fiap.techchallenge.domain.customer.Customer;
import com.fiap.techchallenge.gateway.CustomerGateway;
import com.fiap.techchallenge.drivers.postgresql.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository("PGCustomerRepository")
public class CustomerPostgreDriver implements CustomerGateway {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerPostgreDriver(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
    public Customer getByID(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(
                sql,
                CustomerMapper.listMapper,
                id);
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
    public int update(Customer customer) {
        String sql = "UPDATE customer SET name = ?, email = ? WHERE cpf = ?";

        try {
          return jdbcTemplate.update(
              sql,
              customer.getName(),
              customer.getEmail(),
              customer.getCpf()
          );
        }
        catch (DuplicateKeyException e) {
          return -1;
        }
    }
}
