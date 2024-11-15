package io.github.lsmcodes.inventorymanager.repository.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import io.github.lsmcodes.inventorymanager.enumeration.ProductStatus;
import io.github.lsmcodes.inventorymanager.model.product.Product;

@ActiveProfiles("test")
@DataJpaTest
public class ProductRepositoryTest {

    private UUID id;

    private final String code = "00000000";

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void saveProduct() {
        Product product = productRepository.save(getValidProduct());
        id = product.getId();
    }

    @Test
    @DisplayName("Should verify if a product exists by its id")
    public void existsByIdAndActiveTest() {
        boolean isProductPresent = productRepository.existsById(id);

        assertThat(isProductPresent).isTrue();
    }

    @Test
    @DisplayName("Should verify if a product exists by its code")
    public void existsByCodeAndActiveTest() {
        boolean isProductPresent = productRepository.existsByCodeAndActive(code);

        assertThat(isProductPresent).isTrue();
    }

    @Test
    @DisplayName("Should find a product with the specified id")
    public void findByIdAndActiveTest() {
        Product expectedProduct = getValidProduct();

        Product foundProduct = productRepository.findByIdAndActive(id).get();

        assertThat(foundProduct).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Should find a product with the specified code")
    public void findByCodeAndActiveTest() {
        Product expectedProduct = getValidProduct();

        Product foundProduct = productRepository.findByCodeAndActive(code).get();

        assertThat(foundProduct).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Should find a product containing a specified name, ignoring case")
    public void findByNameContainingIgnoreCaseAndActiveTest() {
        Product expectedProduct = getValidProduct();
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> foundPage = productRepository.findByNameContainingIgnoreCaseAndActive("pen", pageable);

        assertThat(foundPage.getContent().getFirst()).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Should find all products")
    public void findAllActiveTest() {
        Product expectedProduct = getValidProduct();
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> foundPage = productRepository.findAllActive(pageable);

        assertThat(foundPage.getContent().getFirst()).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Should soft delete a product by id")
    public void softDeleteById() {
        productRepository.softDeleteById(id);

        boolean isProductPresent = productRepository.existsByIdAndActive(id);

        assertThat(isProductPresent).isFalse();
    }

    private Product getValidProduct() {
        return new Product(id, code, "Ballpoint Pen", new BigDecimal(0.13), 5, ProductStatus.ACTIVE);
    }

}
