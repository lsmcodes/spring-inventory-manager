package io.github.lsmcodes.inventorymanager.model.product;

import java.math.BigDecimal;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import io.github.lsmcodes.inventorymanager.dto.product.ProductResponseDTO;
import io.github.lsmcodes.inventorymanager.enumeration.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "products")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String code;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @PositiveOrZero
    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal price;

    @PositiveOrZero
    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(length = 8, nullable = false)
    private ProductStatus status = ProductStatus.ACTIVE;

    public ProductResponseDTO toResponseDTO() {
        return new ModelMapper().map(this, ProductResponseDTO.class);
    }

}
