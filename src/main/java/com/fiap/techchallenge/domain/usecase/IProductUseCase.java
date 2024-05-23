package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.domain.entity.Product;

import java.util.List;

public interface IProductUseCase {

    Product createProduct(Product product);

    List<Product> getAllProducts(Product.ProductFilters filters);

    Boolean deleteProduct(String id);
}
