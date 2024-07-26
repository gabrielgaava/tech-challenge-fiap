package com.fiap.techchallenge.domain.product.usecase;

import com.fiap.techchallenge.gateway.ProductGateway;

import java.util.UUID;

public class DeleteProductUseCase {

  private final ProductGateway productRepository;

  public DeleteProductUseCase(ProductGateway productRepository) {
    this.productRepository = productRepository;
  }

  public Boolean execute(String id) {

    var product = productRepository.getById(UUID.fromString(id));

    if(product == null)
      return false;

    int deleteFlag = productRepository.delete(UUID.fromString(id));
    return deleteFlag == 1;

  }

}
