package io.github.lsmcodes.inventorymanager.model.inventoryevent;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.modelmapper.ModelMapper;

import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventResponseDTO;
import io.github.lsmcodes.inventorymanager.enumeration.InventoryEventType;
import io.github.lsmcodes.inventorymanager.model.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "inventory_events")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private InventoryEventType eventType;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Positive
    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public InventoryEventResponseDTO toResponseDTO() {
        return new ModelMapper().map(this, InventoryEventResponseDTO.class);
    }

}
