package com.coffeedev.admin.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.coffeedev.common.entity.Category;

import jakarta.transaction.Transactional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>
,CrudRepository<Category, Integer> {
	
//	void updateEnabledStatus(Integer id, boolean enabled);
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories(Sort sort);
	
	// Pagination
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public Page<Category> findRootCategories(Pageable pageable);
	
	@Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
	public Page<Category> search(String keyword, Pageable pageable);

// update enabled status
	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	@Transactional
	public void updateEnabledStatus(Integer id, boolean enabled);

// delete
	public Long countById(Integer id);

	public Category findByName(String name);
	
// check unique
	@Query("SELECT c FROM Category c WHERE c.name = :name")
	public Category getCategoryByName(@Param("name") String name);
	

// Thêm phương thức mới để lấy tất cả categories
  @Query("SELECT c FROM Category c ORDER BY c.name ASC")
  public List<Category> findAll();

// Thêm phương thức để lấy tất cả categories với sắp xếp
    @Query("SELECT c FROM Category c")
    public List<Category> findAllCategories(Sort sort);

}
