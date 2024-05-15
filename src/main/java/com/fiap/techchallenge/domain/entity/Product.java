package com.fiap.techchallenge.domain.entity;

import com.fiap.techchallenge.domain.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private UUID id;

    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal price;

    private ProductCategory category;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductFilters {

        private ProductCategory category;

    }

}
