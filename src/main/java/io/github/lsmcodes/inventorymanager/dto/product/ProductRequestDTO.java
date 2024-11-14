package io.github.lsmcodes.inventorymanager.dto.product;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import io.github.lsmcodes.inventorymanager.model.product.Product;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "Code cannot be null")
    @Length(max = 50, message = "Code must contain 50 characters or less")
    private String code;

    @NotNull(message = "Name cannot be null")
    @Length(max = 50, message = "Name must contain 100 characters or less")
    private String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", message = "Price cannot be negative", inclusive = true)
    @DecimalMax(value = "10000.00", message = "Price cannot exceed the value of 10000.00")
    private BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 10000, message = "Quantity cannot exceed the value of 10000")
    private int quantity;

    public Product toEntity() {
        return new ModelMapper().map(this, Product.class);
    }

}
