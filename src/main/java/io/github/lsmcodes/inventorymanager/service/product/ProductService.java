package io.github.lsmcodes.inventorymanager.service.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.lsmcodes.inventorymanager.dto.product.ProductRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.product.ProductResponseDTO;

public interface ProductService {

    public ProductResponseDTO createProduct(ProductRequestDTO dto);

    public ProductResponseDTO findProductById(UUID id);

    public ProductResponseDTO findProductByCode(String code);

    public Page<ProductResponseDTO> findProductsByName(String name, Pageable pageable);

    public Page<ProductResponseDTO> findAllProducts(Pageable pageable);

    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO dto);

    public void deleteProductById(UUID id);

    public ProductResponseDTO increaseProductQuantity(UUID id, int quantity);

    public ProductResponseDTO decreaseProductQuantity(UUID id, int quantity);

}
