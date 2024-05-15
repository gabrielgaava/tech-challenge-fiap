package com.fiap.techchallenge.domain.repository;

import com.fiap.techchallenge.domain.entity.Product;

import java.util.List;

public interface IProductRepository {

    int create(Product product);

    List<Product> getAll(Product.ProductFilters filters);

}
