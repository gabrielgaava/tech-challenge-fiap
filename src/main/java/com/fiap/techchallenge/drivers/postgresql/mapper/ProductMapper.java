package com.fiap.techchallenge.drivers.postgresql.mapper;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.ProductCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class ProductMapper {

    public static RowMapper<Product> listMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(UUID.fromString(rs.getString("id")));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setImageUrl(rs.getString("image_url"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setCategory(ProductCategory.valueOf(rs.getString("category")));
            return product;
        }
    };

}
