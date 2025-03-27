package com.coffeedev.category;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public List<Category> getAllParentCategories() {
        return repo.findAllParentCategories();
    }

	public Category getCategory(String name) throws CategoryNotFoundException {
		Category category = repo.findByNameEnabled(name);
		if (category == null) {
			throw new CategoryNotFoundException("Could not find any category.");
		}
		return category;
	}

	public List<Product> listProductsByCategoryName(String categoryName) {
		Category category = repo.findByNameEnabled(categoryName);
		if (category != null) {
			return category.getProducts(); // Sử dụng getter phù hợp để lấy danh sách sản phẩm trong danh mục.
		}
		return Collections.emptyList(); // Trả về danh sách trống nếu không tìm thấy danh mục.
	}

}
