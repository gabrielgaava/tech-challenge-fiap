package com.fiap.techchallenge.domain.product.usecase;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.gateway.ProductGateway;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class CreateProductUseCase {

  private final ProductGateway productGateway;

  public CreateProductUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Product execute(Product product, ProductGateway productGateway)
  {
    product.setId(UUID.randomUUID());
    product.setPrice(formatToTwoDecimalPlaces(product.getPrice()));

    if(this.productGateway.create(product) == 1)
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
