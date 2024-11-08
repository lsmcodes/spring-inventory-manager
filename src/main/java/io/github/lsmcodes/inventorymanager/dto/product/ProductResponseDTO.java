package io.github.lsmcodes.inventorymanager.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private UUID id;

    private String code;

    private String name;

    private BigDecimal price;

    private int quantity;

}
