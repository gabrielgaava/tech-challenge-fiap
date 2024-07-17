package com.fiap.techchallenge.domain.product.usecase.impl;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.ProductRepositoryPort;
import com.fiap.techchallenge.domain.product.usecase.ICreateProductUseCase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class CreateProductUseCase implements ICreateProductUseCase {

  private final ProductRepositoryPort productRepository;

  public CreateProductUseCase(ProductRepositoryPort productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(Product product)
  {
    product.setId(UUID.randomUUID());
    product.setPrice(formatToTwoDecimalPlaces(product.getPrice()));

    if(productRepository.create(product) == 1)
      return product;

    else return null;
  }

  private BigDecimal formatToTwoDecimalPlaces(BigDecimal input)
  {
    if (input == null) {
      throw new IllegalArgumentException("Price cannot be null");
    }
    return input.setScale(2, RoundingMode.HALF_EVEN);
  }

}
