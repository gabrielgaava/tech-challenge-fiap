package com.fiap.techchallenge.adapters.out.database.postgress;

import com.fiap.techchallenge.adapters.out.database.postgress.mapper.ProductMapper;
import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.repository.ProductRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository("PGProductRepository")
public class ProductRepository implements ProductRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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

    @Override
    public int delete(UUID id) {
        String sql = "DELETE FROM public.product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
