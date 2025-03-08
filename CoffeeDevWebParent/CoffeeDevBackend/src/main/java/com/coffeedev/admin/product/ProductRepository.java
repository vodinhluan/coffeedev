package com.coffeedev.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.coffeedev.common.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    // Find product by name
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:name)")
    Product getProductByName(@Param("name") String name);

    // Count by ID
    Long countById(Integer id);

    // Full-text search with case-insensitive matching
    @Query("SELECT p FROM Product p WHERE LOWER(CONCAT(p.id, ' ', p.name)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findAll(@Param("keyword") String keyword, Pageable pageable);

    // Update enabled status
    @Modifying
    @Query("UPDATE Product p SET p.enabled = :enabled WHERE p.id = :id")
    void updateEnabledStatus(@Param("id") Integer id, @Param("enabled") boolean enabled);
}
