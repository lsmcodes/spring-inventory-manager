package io.github.lsmcodes.inventorymanager.controller.inventoryevent;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.lsmcodes.inventorymanager.dto.inventoryevent.InventoryEventRequestDTO;
import io.github.lsmcodes.inventorymanager.service.inventoryevent.InventoryEventService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(InventoryEventController.class)
public class InventoryEventControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryEventService inventoryEventService;

    @Test
    @DisplayName("Should return created status and inventory event")
    public void createInventoryEventTest() throws JsonProcessingException, Exception {
        UUID productId = UUID.randomUUID();
        InventoryEventRequestDTO requestDTO = getValidRequestDTO();

        Mockito.when(inventoryEventService.createInventoryEvent(Mockito.any(UUID.class), Mockito.any(InventoryEventRequestDTO.class))).thenReturn(requestDTO.toEntity().toResponseDTO());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/inventory-events/{product-id}", productId)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventType").value(requestDTO.getEventType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return ok status and inventory event found by id")
    public void findInventoryEventByIdTest() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();
        InventoryEventRequestDTO requestDTO = getValidRequestDTO();

        Mockito.when(inventoryEventService.findInventoryEventById(id))
                .thenReturn(requestDTO.toEntity().toResponseDTO());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/inventory-events/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventType").value(requestDTO.getEventType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return ok status and all inventory events")
    public void findInventoryEventsTest() throws Exception {
        InventoryEventRequestDTO requestDTO = getValidRequestDTO();
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "createdAt"));

        Mockito.when(inventoryEventService.findAllInventoryEvents(pageable))
                .thenReturn(new PageImpl<>(List.of(requestDTO.toEntity().toResponseDTO())));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/inventory-events").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].eventType").value(requestDTO.getEventType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].quantity").value(requestDTO.getQuantity()));
    }

    private InventoryEventRequestDTO getValidRequestDTO() {
        return new InventoryEventRequestDTO("STOCK_IN", 4);
    }

}
