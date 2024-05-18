package com.fiap.techchallenge.infrastructure.repository.postgres;

import com.fiap.techchallenge.domain.entity.Customer;
import com.fiap.techchallenge.domain.repository.ICustomerRepository;
import com.fiap.techchallenge.infrastructure.repository.postgres.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PGCustomerRepository")
public class CustomerRepository implements ICustomerRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create (Customer customer) {

        String sql = "INSERT INTO public.customer " +
                "(id, cpf, name, email) " +
                "VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                customer.getId(),
                customer.getCpf(),
                customer.getName(),
                customer.getEmail()
        );
    }

    public List<Customer> getAll(String cpf) {
        String sql = "SELECT * FROM customer";

        if(cpf != null && !cpf.isEmpty()) {
            sql += " WHERE cpf = ?";
            return jdbcTemplate.query(sql, CustomerMapper.listMapper, cpf);
        }   return jdbcTemplate.query(sql, CustomerMapper.listMapper);
    }
}
