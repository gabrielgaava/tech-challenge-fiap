package com.fiap.techchallenge.domain.product.usecase.impl;

import com.fiap.techchallenge.domain.product.ProductRepositoryPort;
import com.fiap.techchallenge.domain.product.usecase.IDeleteProductUseCase;
import java.util.UUID;

public class DeleteProductUseCase implements IDeleteProductUseCase {

  private final ProductRepositoryPort productRepository;

  public DeleteProductUseCase(ProductRepositoryPort productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Boolean execute(String id) {

    var product = productRepository.getById(UUID.fromString(id));

    if(product == null)
      return false;

    int deleteFlag = productRepository.delete(UUID.fromString(id));
    return deleteFlag == 1;

  }

}
