package com.fiap.techchallenge.domain.product.usecase;

import com.fiap.techchallenge.domain.product.Product;

import java.util.List;

public interface IListAllProductsUseCase {

  List<Product> execute(Product.ProductFilters filters);

}
