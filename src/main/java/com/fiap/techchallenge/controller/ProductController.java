package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.ProductCategory;
import com.fiap.techchallenge.domain.product.usecase.ICreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IDeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IListAllProductsUseCase;
import com.fiap.techchallenge.handlers.rest.dto.CreateProductDTO;
import com.fiap.techchallenge.handlers.rest.dto.ProductDTO;
import com.fiap.techchallenge.presenters.ProductPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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


    public ResponseEntity<List<Product>> getProducts(String category)
    {

        ProductCategory productCategory = category != null
                ? ProductCategory.valueOf(category.toUpperCase())
                : null;

        Product.ProductFilters filters = new Product.ProductFilters(productCategory);
        var products = listAllProductsUseCase.execute(filters);

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }


    public ResponseEntity<ProductDTO> createProduct(CreateProductDTO createProductDTO)
    {

        Product product = ProductPresenter.toDomain(createProductDTO);
        Product createdProduct = createProductUseCase.execute(product);

        if(createdProduct != null){
            ProductDTO response = ProductPresenter.toDto(createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> deleteProduct (String id)
    {
        if(deleteProductUseCase.execute(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
