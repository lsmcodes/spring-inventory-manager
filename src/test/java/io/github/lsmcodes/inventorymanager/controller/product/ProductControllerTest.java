package io.github.lsmcodes.inventorymanager.controller.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.lsmcodes.inventorymanager.dto.product.ProductRequestDTO;
import io.github.lsmcodes.inventorymanager.service.product.ProductService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    private final UUID id = UUID.randomUUID();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Should return created status and product")
    public void createProductTest() throws JsonProcessingException, Exception {
        ProductRequestDTO requestDTO = getValidRequestDTO();

        when(productService.createProduct(any(ProductRequestDTO.class)))
                .thenReturn(requestDTO.toEntity().toResponseDTO());

        mockMvc.perform(
                post("/api/products").content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(requestDTO.getCode()))
                .andExpect(jsonPath("$.name").value(requestDTO.getName()))
                .andExpect(jsonPath("$.price").value(requestDTO.getPrice()))
                .andExpect(jsonPath("$.quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return ok status and product found by id")
    public void findProductByIdTest() throws Exception {
        ProductRequestDTO requestDTO = getValidRequestDTO();

        when(productService.findProductById(id)).thenReturn(requestDTO.toEntity().toResponseDTO());

        mockMvc.perform(get("/api/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(requestDTO.getCode()))
                .andExpect(jsonPath("$.name").value(requestDTO.getName()))
                .andExpect(jsonPath("$.price").value(requestDTO.getPrice()))
                .andExpect(jsonPath("$.quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return ok status and product found by code")
    public void findProductByCodeTest() throws Exception {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        String code = requestDTO.getCode();

        when(productService.findProductByCode(code))
                .thenReturn(requestDTO.toEntity().toResponseDTO());

        mockMvc.perform(
                get("/api/products/code/{code}", code).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(requestDTO.getCode()))
                .andExpect(jsonPath("$.name").value(requestDTO.getName()))
                .andExpect(jsonPath("$.price").value(requestDTO.getPrice()))
                .andExpect(jsonPath("$.quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return ok status and all products")
    public void findProductsTest() throws Exception {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "name"));

        when(productService.findAllProducts(pageable))
                .thenReturn(new PageImpl<>(List.of(requestDTO.toEntity().toResponseDTO())));

        mockMvc.perform(
                get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].code").value(requestDTO.getCode()))
                .andExpect(jsonPath("$.content[0].name").value(requestDTO.getName()))
                .andExpect(jsonPath("$.content[0].price").value(requestDTO.getPrice()))
                .andExpect(jsonPath("$.content[0].quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return ok status and the updated product")
    public void updateProductTest() throws Exception {
        ProductRequestDTO requestDTO = getValidRequestDTO();

        when(productService.updateProduct(any(UUID.class), any(ProductRequestDTO.class)))
                .thenReturn(requestDTO.toEntity().toResponseDTO());

        mockMvc.perform(put("/api/products/{id}", id)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(requestDTO.getCode()))
                .andExpect(jsonPath("$.name").value(requestDTO.getName()))
                .andExpect(jsonPath("$.price").value(requestDTO.getPrice()))
                .andExpect(jsonPath("$.quantity").value(requestDTO.getQuantity()));
    }

    @Test
    @DisplayName("Should return no content")
    public void deleteProductByIdTest() throws Exception {
        mockMvc.perform(
                delete("/api/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private ProductRequestDTO getValidRequestDTO() {
        return new ProductRequestDTO("00000000", "Glue Stick", new BigDecimal(0.96), 3);
    }

}
