package io.github.lsmcodes.inventorymanager.service.product.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import io.github.lsmcodes.inventorymanager.dto.product.ProductRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.product.ProductResponseDTO;
import io.github.lsmcodes.inventorymanager.model.product.Product;
import io.github.lsmcodes.inventorymanager.repository.product.ProductRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private final UUID id = UUID.randomUUID();

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    @DisplayName("Should call the repository save method when creating a product")
    public void createProductTest() {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        when(productRepository.save(any(Product.class))).thenReturn(requestDTO.toEntity());

        ProductResponseDTO responseDTO = productServiceImpl.createProduct(requestDTO);

        assertThat(responseDTO).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(requestDTO);
    }

    @Test
    @DisplayName("Should call the repository findByIdAndActive method when searching a product by id")
    public void findProductByIdTest() {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        when(productRepository.findByIdAndActive(id)).thenReturn(Optional.of(requestDTO.toEntity()));

        ProductResponseDTO responseDTO = productServiceImpl.findProductById(id);

        assertThat(responseDTO).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(requestDTO);
    }

    @Test
    @DisplayName("Should call the repository findByCodeAndActive method when searching a product by code")
    public void findProductByCodeTest() {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        String code = requestDTO.getCode();
        when(productRepository.findByCodeAndActive(code)).thenReturn(Optional.of(requestDTO.toEntity()));

        ProductResponseDTO responseDTO = productServiceImpl.findProductByCode(code);

        assertThat(responseDTO).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(requestDTO);
    }

    @Test
    @DisplayName("Should call the repository findByNameContainingIgnoreCaseAndActive method when searching products by name")
    public void findProductsByNameTest() {
        String name = "notebook";
        Pageable pageable = PageRequest.of(0, 10);
        Product product = getValidRequestDTO().toEntity();
        when(productRepository.findByNameContainingIgnoreCaseAndActive(name, pageable))
                .thenReturn(new PageImpl<>(List.of(product)));

        Page<ProductResponseDTO> foundPage = productServiceImpl.findProductsByName(name, pageable);

        assertThat(product).usingRecursiveComparison().ignoringFields("id", "status")
                .isEqualTo(foundPage.getContent().getFirst());
    }

    @Test
    @DisplayName("Should call the repository findAllActive method when searching all products")
    public void findAllProductsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Product product = getValidRequestDTO().toEntity();
        when(productRepository.findAllActive(pageable))
                .thenReturn(new PageImpl<>(List.of(product)));

        Page<ProductResponseDTO> foundPage = productServiceImpl.findAllProducts(pageable);

        assertThat(product).usingRecursiveComparison().ignoringFields("id", "status")
                .isEqualTo(foundPage.getContent().getFirst());
    }

    @Test
    @DisplayName("Should call the repository save method when updating a product")
    public void updateProductTest() {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        when(productRepository.existsByIdAndActive(id)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(requestDTO.toEntity());

        ProductResponseDTO responseDTO = productServiceImpl.updateProduct(id, requestDTO);

        assertThat(responseDTO).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(requestDTO);
    }

    @Test
    @DisplayName("Should call the repository softDeleteById method when deleting a product by id")
    public void deleteProductByIdTest() {
        when(productRepository.existsByIdAndActive(id)).thenReturn(true);

        productServiceImpl.deleteProductById(id);

        verify(productRepository).softDeleteById(id);
    }

    @Test
    @DisplayName("Should call the repository findByIdAndActive and save methods when increasing a product quantity")
    public void increaseProductQuantity() {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        when(productRepository.findByIdAndActive(id)).thenReturn(Optional.of(requestDTO.toEntity()));

        requestDTO.setQuantity(14);
        when(productRepository.save(any(Product.class))).thenReturn(requestDTO.toEntity());

        ProductResponseDTO productResponseDTO = productServiceImpl.decreaseProductQuantity(id, 5);

        assertThat(productResponseDTO.getQuantity()).isEqualTo(14);
    }

    @Test
    @DisplayName("Should call the repository findByIdAndActive and save methods when decreasing a product quantity")
    public void decreaseProductQuantity() {
        ProductRequestDTO requestDTO = getValidRequestDTO();
        when(productRepository.findByIdAndActive(id)).thenReturn(Optional.of(requestDTO.toEntity()));

        requestDTO.setQuantity(6);
        when(productRepository.save(any(Product.class))).thenReturn(requestDTO.toEntity());

        ProductResponseDTO productResponseDTO = productServiceImpl.decreaseProductQuantity(id, 3);

        assertThat(productResponseDTO.getQuantity()).isEqualTo(6);
    }

    private ProductRequestDTO getValidRequestDTO() {
        return new ProductRequestDTO("00000000", "Spiral Notebook", new BigDecimal(14.99), 9);
    }

}
