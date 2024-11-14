package io.github.lsmcodes.inventorymanager.dto.inventoryevent;

import org.modelmapper.ModelMapper;

import io.github.lsmcodes.inventorymanager.model.inventoryevent.InventoryEvent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEventRequestDTO {

    @NotNull(message = "EventType cannot be null")
    @Pattern(regexp = "^(STOCK_IN|STOCK_OUT)$", message = "Only \"STOCK_IN\" and \"STOCK_OUT\" values are allowed")
    private String eventType;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be one or greater")
    private int quantity;

    public InventoryEvent toEntity() {
        return new ModelMapper().map(this, InventoryEvent.class);
    }

}
