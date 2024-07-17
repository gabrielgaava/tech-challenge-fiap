package com.fiap.techchallenge.adapters.in.rest.dto;

import com.fiap.techchallenge.domain.product.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static com.fiap.techchallenge.adapters.in.rest.constants.FieldValidationConstants.NOT_BLANK;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

	@NotBlank(message = NOT_BLANK)
	String name;

	@NotBlank(message = NOT_BLANK)
	String description;

	@NotBlank(message = NOT_BLANK)
	String imageUrl;

	@DecimalMin(message = "must be greater than 0", value = "0", inclusive = false)
	BigDecimal price;

	ProductCategory category;
}
