package com.fiap.techchallenge.domain.usecase;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateProductDTO;
import com.fiap.techchallenge.domain.entity.Product;

import java.util.List;

public interface IProductUseCase {

    Product createProduct(CreateProductDTO createProductDTO);

    List<Product> getAllProducts(Product.ProductFilters filters);

}
