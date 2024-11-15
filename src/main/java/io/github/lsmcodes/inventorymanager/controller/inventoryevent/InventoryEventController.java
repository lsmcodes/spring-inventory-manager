package io.github.lsmcodes.inventorymanager.controller.inventoryevent;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventResponseDTO;
import io.github.lsmcodes.inventorymanager.service.inventoryevent.InventoryEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/inventory-events")
@RequiredArgsConstructor
@Tag(name = "Inventory Event", description = "Inventory event endpoints")
public class InventoryEventController {

    private final InventoryEventService inventoryEventService;

    @Operation(summary = "Creates a new inventory event")
    @PostMapping("/{product-id}")
    public ResponseEntity<InventoryEventResponseDTO> createInventoryEvent(@PathVariable("product-id") UUID productId,
            @RequestBody @Valid InventoryEventRequestDTO dto) {
        InventoryEventResponseDTO createdInventoryEvent = inventoryEventService.createInventoryEvent(productId, dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{product-id}")
                .buildAndExpand(createdInventoryEvent.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(createdInventoryEvent);
    }

    @Operation(summary = "Retrieves a inventory event by id")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryEventResponseDTO> findInventoryEventById(@PathVariable UUID id) {
        InventoryEventResponseDTO foundInventoryEvent = inventoryEventService.findInventoryEventById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundInventoryEvent);
    }

    @Operation(summary = "Retrieves inventory events")
    @GetMapping
    public ResponseEntity<Page<InventoryEventResponseDTO>> findInventoryEvents(
            @RequestParam(defaultValue = "0") @NotNull int page,
            @RequestParam(defaultValue = "20") @NotNull int size,
            @RequestParam(defaultValue = "createdAt") @NotNull @Pattern(regexp = "^(createdAt|quantity)$") String property,
            @RequestParam(defaultValue = "asc") @NotNull @Pattern(regexp = "^(asc|desc)$") String sortDirection) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Direction.fromString(sortDirection.toUpperCase()), property));
        Page<InventoryEventResponseDTO> foundInventoryEvents = inventoryEventService.findAllInventoryEvents(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(foundInventoryEvents);
    }

}
