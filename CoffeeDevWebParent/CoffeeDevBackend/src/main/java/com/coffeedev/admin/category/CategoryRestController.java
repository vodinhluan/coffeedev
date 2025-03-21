package com.coffeedev.admin.category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
        List<CategoryDTO> categories = categoryService.listAll();

        // Ánh xạ sang DTO, đảm bảo giữ nguyên quan hệ parent-child
		List<CategoryDTO> categoryDTOs = categories;

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
	public ResponseEntity<CategoryDTO> updateCategory(
			@PathVariable Integer id,
			@Valid @RequestBody CategoryDTO categoryDTO) {

		// Tìm danh mục hiện tại trong database
		Category existingCategory = categoryService.findById(id);
		if (existingCategory == null) {
			return ResponseEntity.notFound().build();
		}

		// Cập nhật thông tin danh mục
		existingCategory.setName(categoryDTO.getName());
		existingCategory.setImage(categoryDTO.getImage());
		existingCategory.setEnabled(categoryDTO.isEnabled());

		// Xử lý danh mục con và cập nhật `parent`
		Set<Category> childCategories = new HashSet<>();
		for (CategoryDTO childDTO : categoryDTO.getChildren()) {
			Category child = categoryService.findById(childDTO.getId());
			if (child != null) {
				child.setParent(existingCategory); 
				childCategories.add(child);
			}
		}
		existingCategory.setChildren(childCategories);

		// Lưu vào database
		Category updatedCategory = categoryService.save(existingCategory);

		// Chuyển đổi sang DTO để trả về
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