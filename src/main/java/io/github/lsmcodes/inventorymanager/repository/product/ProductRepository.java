package io.github.lsmcodes.inventorymanager.repository.product;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.lsmcodes.inventorymanager.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT EXISTS (FROM Product p WHERE p.id = :id AND p.status = 'ACTIVE')")
    boolean existsByIdAndActive(UUID id);

    @Query("SELECT EXISTS (SELECT 1 FROM Product p WHERE p.code = :code AND p.status = 'ACTIVE')")
    boolean existsByCodeAndActive(String code);

    @Query("SELECT EXISTS (SELECT 1 FROM Product p WHERE p.code = :code AND p.status = 'ACTIVE' AND p.id <> :id)")
    boolean existsByCodeAndActiveAndNotId(String code, UUID id);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.id = :id")
    Optional<Product> findByIdAndActive(UUID id);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.code = :code")
    Optional<Product> findByCodeAndActive(String code);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByNameContainingIgnoreCaseAndActive(String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE'")
    Page<Product> findAllActive(Pageable pageable);

    @Modifying
    @Query("UPDATE Product SET status = 'INACTIVE' WHERE id = :id")
    void softDeleteById(UUID id);

}
