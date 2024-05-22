package com.fiap.techchallenge.infrastructure.repository.postgres;

import com.fiap.techchallenge.domain.entity.Order;
import com.fiap.techchallenge.domain.entity.OrderFilters;
import com.fiap.techchallenge.domain.entity.OrderHistory;
import com.fiap.techchallenge.domain.entity.ProductAndQuantity;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.repository.IOrderRepository;
import com.fiap.techchallenge.infrastructure.repository.postgres.mapper.OrderMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Repository("PGOrderRepository")
public class OderRepository implements IOrderRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getAll(OrderFilters filters) {
        String sql = "SELECT *, id as order_id FROM public.order";

        if(filters.getStatus() != null) {
            sql += " WHERE status = '" + filters.getStatus() + "'";
        }

        if(filters.getOrderBy() != null) {
            sql += " ORDER BY " + filters.getOrderBy().toString().toLowerCase() + " " + filters.getDirection();
        }

        return jdbcTemplate.query(sql, OrderMapper.listMapper);
    }

    @Override
    public List<OrderHistory> getOrderHistoryByOrderId(UUID orderId) {
        String sql = "SELECT * " +
                "FROM public.order_history " +
                "WHERE order_id = ?";

        return jdbcTemplate.query(sql, OrderMapper.historyMapper, orderId);
    }

    @Override
    public Order getByIdWithProducts(UUID id) {
        String sql = "SELECT op.order_id, op.product_id, o.amount, o.created_at, o.status, o.order_number, op.quantity, o.customer_id, p.name, p.price, p.category, p.description, p.image_url " +
                "FROM public.order o " +
                "LEFT JOIN public.order_products op ON op.order_id = o.id " +
                "LEFT JOIN public.product p ON p.id = op.product_id " +
                "WHERE o.id = ?";

        var data = jdbcTemplate.query(sql, OrderMapper.listWithProducts, id);
        if(data.isEmpty()) return null;

        return data.getFirst();
    }

    @Override
    public int create(Order order) {
        String createOrderSQL = "INSERT INTO public.order " +
                "(id, customer_id, created_at, amount, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        String createOrderProductRelationSQL = "INSERT INTO public.order_products " +
                "(order_id, product_id, quantity) " +
                "VALUES (?,?,?)";

        // Storage in Order Table
        int isOrderCreated = jdbcTemplate.update(
            createOrderSQL,
            order.getId(),
            order.getCreatedAt(),
            order.getAmount(),
            order.getStatus().toString()
        );

        // Insert in the Relational Table
        for(ProductAndQuantity product : order.getProducts()) {
             jdbcTemplate.update(
                createOrderProductRelationSQL,
                order.getId(),
                product.getProduct().getId(),
                product.getQuantity()
            );
        }

        return isOrderCreated;

    }

    @Override
    public int updateStatus(UUID id, OrderStatus newStatus, OrderStatus previousStatus) {
        LocalDateTime now = LocalDateTime.now();
        String updateStatusSQL = "UPDATE public.order SET status = ? WHERE id = ?";
        String relationInsertSQL = "INSERT INTO public.order_history (order_id, previous_status, new_status, moment) VALUES (?, ?, ?, ?)";

        int updated = jdbcTemplate.update(updateStatusSQL, newStatus.toString(), id);
        int relationInsertResult = jdbcTemplate.update(
                relationInsertSQL,
                id,
                previousStatus.toString(),
                newStatus.toString(),
                now
        );

        return updated + relationInsertResult;
    }

    @Override
    public Order getById(UUID id) {
        String sql = "SELECT *, id as order_id " +
                "FROM public.order o " +
                "WHERE id = ?";

        var data = jdbcTemplate.query(sql, OrderMapper.listMapper, id);
        if(data.isEmpty()) return null;

        return data.getFirst();
    }

}
