package com.fiap.techchallenge.infrastructure.repository.postgres;

import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.repository.IOrderRepository;
import com.fiap.techchallenge.infrastructure.repository.postgres.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("PGOrderRepository")
public class OrderRepository implements IOrderRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getAll() {
        String sql = "select * from \"order\"";
        return jdbcTemplate.query(sql, OrderMapper.listMapper);
    }

    @Override
    public Order getOrder(UUID orderId) {
        String sql = "SELECT * FROM \"order\" WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, OrderMapper.listMapper, orderId);
    }

    @Override
    public int create(Order order) {
        String sql = "INSERT INTO \"order\" " +
                "(\"order_number\", \"customer_id\", \"created_at\", \"amount\", \"status\") " +
                "VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql,
            order.getOrderNumber(),
            order.getCostumerId(),
            order.getCreatedAt(),
            order.getAmount(),
            order.getStatus().toString()
        );
    }

}
