package io.github.lsmcodes.inventorymanager.dto.inventoryevent;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.lsmcodes.inventorymanager.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEventResponseDTO {

    private UUID id;

    private String eventType;

    private Product product;

    private int quantity;

    private LocalDateTime createdAt;

}
