package com.fiap.techchallenge.adapters.out.database.postgress.mapper;

import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.enums.ProductCategory;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
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
