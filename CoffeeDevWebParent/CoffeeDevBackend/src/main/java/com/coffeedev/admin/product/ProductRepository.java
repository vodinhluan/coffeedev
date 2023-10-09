package com.coffeedev.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.coffeedev.common.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, 
	CrudRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE p.name = :name")
	public Product getProductByName(@Param("name") String name);

	public Long countById(Integer id);

	@Query("SELECT p FROM Product p WHERE CONCAT(p.id, ' ', p.name) LIKE %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);

	// ?1: Đại diện cho tham số đầu tiên, tức là id của người dùng (Integer id).
	// ?2: Đại diện cho tham số thứ hai, tức là enabled - giá trị mới cho thuộc tính
	// enabled
	@Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
}
