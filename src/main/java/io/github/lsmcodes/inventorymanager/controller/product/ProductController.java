package io.github.lsmcodes.inventorymanager.controller.product;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.lsmcodes.inventorymanager.dto.product.ProductRequestDTO;
import io.github.lsmcodes.inventorymanager.dto.product.ProductResponseDTO;
import io.github.lsmcodes.inventorymanager.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product endpoints")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Creates a new product")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO dto) {
        ProductResponseDTO createdProductDTO = productService.createProduct(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProductDTO.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(createdProductDTO);
    }

    @Operation(summary = "Retrieves a product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable UUID id) {
        ProductResponseDTO foundProductDTO = productService.findProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundProductDTO);
    }

    @Operation(summary = "Retrieves a product by code")
    @GetMapping("/code/{code}")
    public ResponseEntity<ProductResponseDTO> findProductByCode(@PathVariable String code) {
        ProductResponseDTO foundProductDTO = productService.findProductByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(foundProductDTO);
    }

    @Operation(summary = "Retrieves products")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") @NotNull int page,
            @RequestParam(defaultValue = "20") @NotNull int size,
            @RequestParam(defaultValue = "name") @NotNull @Pattern(regexp = "^(name|price|quantity)$") String property,
            @RequestParam(defaultValue = "asc") @NotNull @Pattern(regexp = "^(asc|desc)$") String sortDirection) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Direction.fromString(sortDirection.toUpperCase()), property));
        Page<ProductResponseDTO> foundProducts = name == null ? productService.findAllProducts(pageable)
                : productService.findProductsByName(name, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(foundProducts);
    }

    @Operation(summary = "Updates a product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id,
            @RequestBody @Valid ProductRequestDTO dto) {
        ProductResponseDTO updatedProductDTO = productService.updateProduct(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProductDTO);
    }

    @Operation(summary = "Deletes a product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
