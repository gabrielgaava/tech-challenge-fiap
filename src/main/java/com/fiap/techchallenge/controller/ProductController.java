package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.usecase.ICreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IDeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IListAllProductsUseCase;

import java.util.List;

public class ProductController {

    private final ICreateProductUseCase createProductUseCase;
    private final IDeleteProductUseCase deleteProductUseCase;
    private final IListAllProductsUseCase listAllProductsUseCase;

    public ProductController
    (
        ICreateProductUseCase createProductUseCase,
        IDeleteProductUseCase deleteProductUseCase,
        IListAllProductsUseCase listAllProductsUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.listAllProductsUseCase = listAllProductsUseCase;
    }

    public List<Product> getProducts(Product.ProductFilters filters){
        return listAllProductsUseCase.execute(filters);
    }

    public Product createProduct(Product product){
        return createProductUseCase.execute(product);
    }

    public Boolean deleteProduct (String id){
        return (deleteProductUseCase.execute(id));
    }
}
