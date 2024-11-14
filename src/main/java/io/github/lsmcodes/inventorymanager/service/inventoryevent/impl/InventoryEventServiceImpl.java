package io.github.lsmcodes.inventorymanager.service.inventoryevent.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventResponseDTO;
import io.github.lsmcodes.inventorymanager.model.inventoryevent.InventoryEvent;
import io.github.lsmcodes.inventorymanager.model.product.Product;
import io.github.lsmcodes.inventorymanager.repository.inventoryevent.InventoryEventRepository;
import io.github.lsmcodes.inventorymanager.service.inventoryevent.InventoryEventService;
import io.github.lsmcodes.inventorymanager.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryEventServiceImpl implements InventoryEventService {

    private final InventoryEventRepository inventoryEventRepository;

    private final ProductService productService;

    @Override
    public InventoryEventResponseDTO createInventoryEvent(UUID productId, InventoryEventRequestDTO dto) {
        InventoryEvent inventoryEvent = dto.toEntity();
        Product product = new ModelMapper().map(productService.findProductById(productId), Product.class);

        if(dto.getEventType().equals("STOCK_IN")) {
            productService.increaseProductQuantity(product.getId(), dto.getQuantity());
        } else {
            productService.decreaseProductQuantity(product.getId(), dto.getQuantity());
        }

        inventoryEvent.setProduct(product);
        return inventoryEventRepository.save(inventoryEvent).toResponseDTO();
    }

    @Override
    public InventoryEventResponseDTO findInventoryEventById(UUID id) {
        InventoryEvent foundInventoryEvent = inventoryEventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return foundInventoryEvent.toResponseDTO();
    }

    @Override
    public Page<InventoryEventResponseDTO> findAllInventoryEvents(Pageable pageable) {
        Page<InventoryEvent> inventoryEvents = inventoryEventRepository.findAll(pageable);
        return inventoryEvents.map(event -> event.toResponseDTO());
    }

}
