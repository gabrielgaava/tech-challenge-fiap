package com.fiap.techchallenge.infrastructure.repository.postgres;

import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.repository.IProductRepository;
import com.fiap.techchallenge.infrastructure.repository.postgres.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

        String sql = "INSERT INTO public.product " +
                "(id, name, description, image_url, price, category) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql,
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getImageUrl(),
            product.getPrice(),
            product.getCategory().toString()
        );
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

    @Override
    public Product getById(UUID id) {
        if(id == null) return null;

        String sql = "select * from product where id = ?";
        List<Product> data = jdbcTemplate.query(sql, ProductMapper.listMapper, id);

        if(data.isEmpty()) return null;

        else return data.getFirst();
    }

}
