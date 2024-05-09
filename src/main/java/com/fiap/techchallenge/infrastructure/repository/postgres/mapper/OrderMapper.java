package com.fiap.techchallenge.infrastructure.repository.postgres.mapper;

import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public abstract class OrderMapper {

    public static RowMapper<Order> listMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(UUID.fromString(rs.getString(1)));
            order.setOrderNumber(rs.getString(2));
            order.setCostumerId(UUID.fromString(rs.getString(3)));
            order.setCreatedAt(rs.getTimestamp(4).toLocalDateTime());
            order.setAmount(rs.getFloat(5));
            order.setStatus(OrderStatus.fromString(rs.getString(6)));

            return order;
        }
    };

}
