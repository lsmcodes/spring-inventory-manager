package io.github.lsmcodes.inventorymanager.service.product.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.inventorymanager.dto.product.ProductRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.product.ProductResponseDTO;
import io.github.lsmcodes.inventorymanager.exception.CodeAlreadyExistsException;
import io.github.lsmcodes.inventorymanager.exception.InvalidInventoryEventException;
import io.github.lsmcodes.inventorymanager.exception.ProductNotFoundException;
import io.github.lsmcodes.inventorymanager.model.product.Product;
import io.github.lsmcodes.inventorymanager.repository.product.ProductRepository;
import io.github.lsmcodes.inventorymanager.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        if (productRepository.existsByCodeAndActive(dto.getCode())) {
            throw new CodeAlreadyExistsException("The provided code is already in use by another product");
        }
        Product newProduct = dto.toEntity();
        return productRepository.save(newProduct).toResponseDTO();
    }

    @Override
    public ProductResponseDTO findProductById(UUID id) {
        Product foundProduct = findProductOrThrow(id);
        return foundProduct.toResponseDTO();
    }

    @Override
    public ProductResponseDTO findProductByCode(String code) {
        Product foundProduct = productRepository.findByCodeAndActive(code)
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the provided code"));
        return foundProduct.toResponseDTO();
    }

    @Override
    public Page<ProductResponseDTO> findProductsByName(String name, Pageable pageable) {
        Page<Product> foundProducts = productRepository.findByNameContainingIgnoreCaseAndActive(name, pageable);
        return foundProducts.map(product -> product.toResponseDTO());
    }

    @Override
    public Page<ProductResponseDTO> findAllProducts(Pageable pageable) {
        Page<Product> foundProducts = productRepository.findAllActive(pageable);
        return foundProducts.map(product -> product.toResponseDTO());
    }

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductRequestDTO dto) {
        verifyProductExists(id);
        if (productRepository.existsByCodeAndActiveAndNotId(dto.getCode(), id)) {
            throw new CodeAlreadyExistsException("The provided code is already in use by another product");
        }
        Product updatedProduct = dto.toEntity();
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct).toResponseDTO();
    }

    @Transactional
    @Override
    public void deleteProductById(UUID id) {
        verifyProductExists(id);
        productRepository.softDeleteById(id);
    }

    @Override
    public ProductResponseDTO increaseProductQuantity(UUID id, int quantity) {
        Product product = findProductOrThrow(id);
        product.setQuantity(product.getQuantity() + quantity);
        return productRepository.save(product).toResponseDTO();
    }

    @Override
    public ProductResponseDTO decreaseProductQuantity(UUID id, int quantity) {
        Product product = findProductOrThrow(id);
        if (product.getQuantity() < quantity) {
            throw new InvalidInventoryEventException("Insufficient stock: unable to proccess the requested quantity");
        }
        product.setQuantity(product.getQuantity() - quantity);
        return productRepository.save(product).toResponseDTO();
    }

    private void verifyProductExists(UUID id) {
        if (!productRepository.existsByIdAndActive(id)) {
            throw new ProductNotFoundException("There is no product with the provided id");
        }
    }

    private Product findProductOrThrow(UUID id) {
        return productRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the provided id"));
    }

}
