package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepositoryPort {

    int create(Product product);

    List<Product> getAll(Product.ProductFilters filters);

    Product getById(UUID id);

    int delete(String id);
}
