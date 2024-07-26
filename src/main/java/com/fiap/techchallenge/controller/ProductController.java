package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.usecase.CreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.DeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.ListAllProductsUseCase;
import com.fiap.techchallenge.gateway.ProductGateway;

import java.util.List;

public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ListAllProductsUseCase listAllProductsUseCase;

    public ProductController
    (
        CreateProductUseCase createProductUseCase,
        DeleteProductUseCase deleteProductUseCase,
        ListAllProductsUseCase listAllProductsUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.listAllProductsUseCase = listAllProductsUseCase;
    }

    public List<Product> getProducts(Product.ProductFilters filters, ProductGateway productGateway){
        return listAllProductsUseCase.execute(filters, productGateway);
    }

    public Product createProduct(Product product, ProductGateway productGateway){
        return createProductUseCase.execute(product, productGateway);
    }

    public Boolean deleteProduct (String id, ProductGateway productGateway){
        return (deleteProductUseCase.execute(id, productGateway));
    }
}
