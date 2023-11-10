package com.coffeedev.product;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
	List<Product> findAllByCategoryName(@Param("categoryName") String categoryName);
	
    Optional<Product> findByAlias(String alias);



}
