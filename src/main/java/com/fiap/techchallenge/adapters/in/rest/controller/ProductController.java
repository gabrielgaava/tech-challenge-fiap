package com.fiap.techchallenge.adapters.in.rest.controller;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateProductDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.ProductDTO;
import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.enums.ProductCategory;
import com.fiap.techchallenge.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Product Controller")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(
        summary = "List all storage products",
        parameters = {@Parameter(name = "category", schema = @Schema(implementation = ProductCategory.class))}
    )
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
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid CreateProductDTO createProductDTO) {

        Product product = productService.createProduct(createProductDTO);

        if(product != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(product));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Delete a product from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable String id) {
        if(productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
