package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.domain.entity.Product;
import com.fiap.techchallenge.domain.usecase.IProductUseCase;
import com.fiap.techchallenge.infrastructure.repository.postgres.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductUseCase {

    @Autowired
    @Qualifier("PGProductRepository")
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID());

        if(productRepository.create(product) == 1)
            return product;

        else return null;
    }

    @Override
    public List<Product> getAllProducts(Product.ProductFilters filters) {
        return productRepository.getAll(filters);
    }

}
