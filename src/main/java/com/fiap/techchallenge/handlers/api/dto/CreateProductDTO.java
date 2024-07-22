package com.fiap.techchallenge.handlers.api.dto;

import com.fiap.techchallenge.domain.product.ProductCategory;
import com.fiap.techchallenge.handlers.api.constants.FieldValidationConstants;
import jakarta.validation.constraints.*;
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

	@NotBlank(message = FieldValidationConstants.NOT_BLANK)
	String name;

	@NotBlank(message = FieldValidationConstants.NOT_BLANK)
	String description;

	@NotBlank(message = FieldValidationConstants.NOT_BLANK)
	String imageUrl;

	@DecimalMin(message = "must be greater than 0", value = "0", inclusive = false)
	BigDecimal price;

	ProductCategory category;
}
