package com.fiap.techchallenge.infrastructure.repository.postgres;

import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.repository.IProductRepository;
import com.fiap.techchallenge.infrastructure.repository.postgres.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PGProductRepository")
public class ProductRepository implements IProductRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(Product product) {
        return 0;
    }

    @Override
    public List<Product> getAll(Product.ProductFilters filters) {
        String sql = "select * from product";

        if(filters.getCategory() != null) {
            sql += " where category = ?";
            return jdbcTemplate.query(sql, ProductMapper.listMapper, filters.getCategory().toString());
        }

        return jdbcTemplate.query(sql, ProductMapper.listMapper);
    }

}
