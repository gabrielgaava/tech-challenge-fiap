package com.fiap.techchallenge.domain.product.usecase.impl;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.ProductRepositoryPort;
import com.fiap.techchallenge.domain.product.usecase.IListAllProductsUseCase;

import java.util.List;

public class ListAllProductsUseCase implements IListAllProductsUseCase {

  private final ProductRepositoryPort productRepository;

  public ListAllProductsUseCase(ProductRepositoryPort productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> execute(Product.ProductFilters filters)
  {
    return productRepository.getAll(filters);
  }

}
