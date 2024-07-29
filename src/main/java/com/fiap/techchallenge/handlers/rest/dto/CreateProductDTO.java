package com.fiap.techchallenge.handlers.rest.dto;

import com.fiap.techchallenge.domain.product.ProductCategory;
import com.fiap.techchallenge.handlers.rest.constants.FieldValidationConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

	@NotNull(message =  FieldValidationConstants.NOT_NULL)
	ProductCategory category;
}
