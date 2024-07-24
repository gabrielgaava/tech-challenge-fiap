package com.fiap.techchallenge.domain.product.usecase.impl;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.gateway.ProductGateway;
import com.fiap.techchallenge.domain.product.usecase.IListAllProductsUseCase;

import java.util.List;

public class ListAllProductsUseCase implements IListAllProductsUseCase {

  private final ProductGateway productRepository;

  public ListAllProductsUseCase(ProductGateway productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> execute(Product.ProductFilters filters)
  {
    return productRepository.getAll(filters);
  }

}
