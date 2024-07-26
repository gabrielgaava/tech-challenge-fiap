package com.fiap.techchallenge.domain.product.usecase;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.gateway.ProductGateway;

import java.util.List;

public class ListAllProductsUseCase{

  private final ProductGateway productGateway;

  public ListAllProductsUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public List<Product> execute(Product.ProductFilters filters, ProductGateway productGateway)
  {
    return this.productGateway.getAll(filters);
  }
}
