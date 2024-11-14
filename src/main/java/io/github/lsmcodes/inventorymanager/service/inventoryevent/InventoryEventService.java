package io.github.lsmcodes.inventorymanager.service.inventoryevent;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventResponseDTO;

public interface InventoryEventService {
    
    public InventoryEventResponseDTO createInventoryEvent(UUID id, InventoryEventRequestDTO dto);

    public InventoryEventResponseDTO findInventoryEventById(UUID id);

    public Page<InventoryEventResponseDTO> findAllInventoryEvents(Pageable pageable);
    
}
