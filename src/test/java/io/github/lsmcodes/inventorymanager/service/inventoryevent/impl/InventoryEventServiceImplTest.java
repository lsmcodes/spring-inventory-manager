package io.github.lsmcodes.inventorymanager.service.inventoryevent.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventResponseDTO;
import io.github.lsmcodes.inventorymanager.dto.product.ProductResponseDTO;
import io.github.lsmcodes.inventorymanager.model.inventoryevent.InventoryEvent;
import io.github.lsmcodes.inventorymanager.repository.inventoryevent.InventoryEventRepository;
import io.github.lsmcodes.inventorymanager.service.product.ProductService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class InventoryEventServiceImplTest {

    private final UUID id = UUID.randomUUID();

    @Mock
    private InventoryEventRepository inventoryEventRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private InventoryEventServiceImpl inventoryEventServiceImpl;

    @Test
    @DisplayName("Should call the repository save method when creating a inventory event")
    public void createInventoryEventTest() {
        InventoryEventRequestDTO requestDTO = getValidRequestDTO();
        var product = new ProductResponseDTO();
        when(productService.findProductById(id)).thenReturn(product);
        when(inventoryEventRepository.save(any(InventoryEvent.class)))
                .thenReturn(requestDTO.toEntity());

        InventoryEventResponseDTO responseDTO = inventoryEventServiceImpl.createInventoryEvent(id, requestDTO);

        assertThat(responseDTO).usingRecursiveComparison().ignoringFields("id", "product", "createdAt")
                .isEqualTo(requestDTO);
    }

    @Test
    @DisplayName("Should call the repository findById method when searching an inventory event by id")
    public void findInventoryEventByIdTest() {
        InventoryEventRequestDTO requestDTO = getValidRequestDTO();
        when(inventoryEventRepository.findById(id)).thenReturn(Optional.of(requestDTO.toEntity()));

        InventoryEventResponseDTO responseDTO = inventoryEventServiceImpl.findInventoryEventById(id);

        assertThat(responseDTO).usingRecursiveComparison().ignoringFields("id", "product", "createdAt")
                .isEqualTo(requestDTO);
    }

    @Test
    @DisplayName("Should call the repository findAll method when searching all inventory events")
    public void findAllInventoryEventsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        InventoryEventRequestDTO requestDTO = getValidRequestDTO();
        when(inventoryEventRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(requestDTO.toEntity())));

        Page<InventoryEventResponseDTO> foundPage = inventoryEventServiceImpl.findAllInventoryEvents(pageable);

        assertThat(foundPage.getContent().getFirst()).usingRecursiveComparison()
                .ignoringFields("id", "product", "createdAt")
                .isEqualTo(requestDTO);
    }

    private InventoryEventRequestDTO getValidRequestDTO() {
        return new InventoryEventRequestDTO("STOCK_IN", 3);
    }

}
