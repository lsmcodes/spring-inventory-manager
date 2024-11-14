package io.github.lsmcodes.inventorymanager.repository.inventoryevent;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lsmcodes.inventorymanager.model.inventoryevent.InventoryEvent;

@Repository
public interface InventoryEventRepository extends JpaRepository<InventoryEvent, UUID> {
    
}
