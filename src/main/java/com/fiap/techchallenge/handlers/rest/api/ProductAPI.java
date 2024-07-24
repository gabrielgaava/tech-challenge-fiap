package com.fiap.techchallenge.handlers.rest.api;

import com.fiap.techchallenge.controller.ProductController;
import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.ProductCategory;
import com.fiap.techchallenge.domain.product.usecase.ICreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IDeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IListAllProductsUseCase;
import com.fiap.techchallenge.handlers.rest.dto.CreateProductDTO;
import com.fiap.techchallenge.handlers.rest.dto.ProductDTO;
import com.fiap.techchallenge.presenters.ProductPresenter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product API")
@RestController
@RequestMapping("/products")
public class ProductAPI {

    private final ProductController productController;

    public ProductAPI(ProductController productController) {
        this.productController = productController;
    }

    @GetMapping
    @Operation(
        summary = "List all storage products",
        parameters = {@Parameter(name = "category", schema = @Schema(implementation = ProductCategory.class))}
    )
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String category)
    {

        ProductCategory productCategory = category != null
                ? ProductCategory.valueOf(category.toUpperCase())
                : null;

        Product.ProductFilters filters = new Product.ProductFilters(productCategory);
        var products = productController.getProducts(filters);

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Creates a new product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid CreateProductDTO createProductDTO)
    {

        Product product = ProductPresenter.toDomain(createProductDTO);
        Product createdProduct = productController.createProduct(product);

        if(createdProduct != null){
            ProductDTO response = ProductPresenter.toDto(createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Delete a product from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable String id)
    {
        if(productController.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
