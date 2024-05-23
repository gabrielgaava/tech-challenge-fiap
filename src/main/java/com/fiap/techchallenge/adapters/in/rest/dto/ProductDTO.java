package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fiap.techchallenge.domain.entity.Product;
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
public class ProductDTO extends CreateProductDTO {
	private UUID id;

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.imageUrl = product.getImageUrl();
		this.price = product.getPrice();
		this.category = product.getCategory();
	}

}