package com.fiap.techchallenge.gateway;

import com.fiap.techchallenge.domain.product.Product;

import java.util.List;
import java.util.UUID;

public interface ProductGateway {

    int create(Product product);

    List<Product> getAll(Product.ProductFilters filters);

    Product getById(UUID id);

    int delete(UUID id);
}
