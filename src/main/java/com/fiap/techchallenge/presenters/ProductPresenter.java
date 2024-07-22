package com.fiap.techchallenge.presenters;

import com.fiap.techchallenge.handlers.api.dto.CreateProductDTO;
import com.fiap.techchallenge.handlers.api.dto.ProductDTO;
import com.fiap.techchallenge.domain.product.Product;

public abstract class ProductPresenter {

  public static Product toDomain(CreateProductDTO dto) {
    Product product = new Product();
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setImageUrl(dto.getImageUrl());
    product.setPrice(dto.getPrice());
    product.setCategory(dto.getCategory());
    return product;
  }

  public static ProductDTO toDto(Product product) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    productDTO.setDescription(product.getDescription());
    productDTO.setImageUrl(product.getImageUrl());
    productDTO.setPrice(product.getPrice());
    productDTO.setCategory(product.getCategory());
    return productDTO;
  }

}
