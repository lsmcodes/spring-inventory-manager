package io.github.lsmcodes.inventorymanager.repository.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import io.github.lsmcodes.inventorymanager.model.product.Product;

@ActiveProfiles("test")
@DataJpaTest
public class ProductRepositoryTest {

    private final String code = "00000000";

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void saveProduct() {
        productRepository.save(getValidProduct());
    }

    @Test
    @DisplayName("Should verify if a product exists by its code")
    public void existsByCodeTest() {
        boolean isProductPresent = productRepository.existsByCode(code);

        assertThat(isProductPresent).isTrue();
    }

    @Test
    @DisplayName("Should find a product with the specified code")
    public void findByCodeTest() {
        Product expectedProduct = getValidProduct();

        Product foundProduct = productRepository.findByCode(code).get();

        assertThat(foundProduct).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Should find a product containing a specified name, ignoring case")
    public void findByNameContainingIgnoreCaseTest() {
        Product expectedProduct = getValidProduct();
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> foundPage = productRepository.findByNameContainingIgnoreCase("pen", pageable);

        assertThat(foundPage.getContent().getFirst()).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedProduct);
    }

    private Product getValidProduct() {
        return new Product(null, code, "Ballpoint Pen", new BigDecimal(0.13), 5);
    }

}
