package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.enums.ProductCategory;
import com.fiap.techchallenge.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String category) {

        ProductCategory productCategory = category != null
                ? ProductCategory.valueOf(category.toUpperCase())
                : null;

        Product.ProductFilters filters = new Product.ProductFilters(productCategory);
        var products = productService.getAllProducts(filters);

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if(productService.createProduct(product) != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(product);

        else return ResponseEntity.badRequest().build();
    }
}
