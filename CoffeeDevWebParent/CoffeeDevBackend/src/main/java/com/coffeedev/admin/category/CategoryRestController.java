package com.coffeedev.admin.category;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coffeedev.common.dto.CategoryDTO;
import com.coffeedev.common.entity.Category;
import jakarta.validation.Valid;
import com.coffeedev.admin.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<Category> categories = categoryService.listAll(); 
		List<CategoryDTO> categoryDTOs = categories.stream()
			.map(category -> modelMapper.map(category, CategoryDTO.class))
			.collect(Collectors.toList());
		return ResponseEntity.ok(categoryDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer id) {
		try {
			Category category = categoryService.get(id);
			CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
			return ResponseEntity.ok(categoryDTO);
		} catch (CategoryNotFoundException ex) {
			throw new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id);
		}
	}

	@PostMapping
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category savedCategory = categoryService.save(category);
		CategoryDTO savedDTO = modelMapper.map(savedCategory, CategoryDTO.class);
		return ResponseEntity.ok(savedDTO);
	}

	@PutMapping("/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> updateCategory(
			@PathVariable Integer id, 
			@Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		category.setId(id);
		Category updatedCategory = categoryService.save(category);
		CategoryDTO updatedDTO = modelMapper.map(updatedCategory, CategoryDTO.class);
		return ResponseEntity.ok(updatedDTO);
	}

	@DeleteMapping("/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		try {
			categoryService.delete(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id);
		}
	}

	@PostMapping("/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
		return categoryService.checkUnique(id, name) ? "OK" : "Duplicated";
	}
}