package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fiap.techchallenge.domain.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

	String name;
	String description;
	String imageUrl;
	BigDecimal price;
	ProductCategory category;

}
