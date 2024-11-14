package io.github.lsmcodes.inventorymanager.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.lsmcodes.inventorymanager.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByCode(String code);

    Optional<Product> findByCode(String code);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
