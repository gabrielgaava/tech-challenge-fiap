package com.fiap.techchallenge.domain.product.usecase;

import com.fiap.techchallenge.gateway.ProductGateway;

import java.util.UUID;

public class DeleteProductUseCase {

  private final ProductGateway productGateway;

  public DeleteProductUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Boolean execute(String id, ProductGateway productGateway) {

    var product = this.productGateway.getById(UUID.fromString(id));

    if(product == null)
      return false;

    int deleteFlag = this.productGateway.delete(UUID.fromString(id));
    return deleteFlag == 1;

  }

}
