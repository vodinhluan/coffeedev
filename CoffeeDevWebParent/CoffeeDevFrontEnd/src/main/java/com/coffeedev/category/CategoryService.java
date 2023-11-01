package com.coffeedev.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	
//	public List<Category> listNoChildrenCategories() {
//		List<Category> listNoChildrenCategories = new ArrayList();
//		List<Category> listEnabledCategories = repo.findAllEnabled();
//
//		listEnabledCategories.forEach(category -> {
//			Set<Category> children = category.getChildren();
//			if (children == null || children.size() == 0) {
//				listNoChildrenCategories.add(category);
//			}
//		});
//		return listNoChildrenCategories;
//	}
//
	public Category getCategory(String name) throws CategoryNotFoundException {
		Category category = repo.findByNameEnabled(name);
		if (category == null) {
			throw new CategoryNotFoundException("Could not find any category.");
		}
		return category;
	}
//
//	public List<Category> getCategoryParents(Category child) {
//		List<Category> listParents = new ArrayList<>();
//
//		Category parent = child.getParent();
//		while (parent != null) {
//			listParents.add(0, parent);
//			parent = parent.getParent();
//		}
//		listParents.add(child);
//		return listParents;
//	}
//
//	public List<Category> listParentCategories() {
//		List<Category> parentCategories = new ArrayList<>();
//		List<Category> listEnabledCategories = repo.findAllEnabled();
//
//		listEnabledCategories.forEach(category -> {
//			if (category.getParent() == null) {
//				parentCategories.add(category);
//			}
//		});
//		return parentCategories;
//	}
	
	public List<Product> listProductsByCategoryName(String categoryName) {
	    Category category = repo.findByNameEnabled(categoryName);
	    if (category != null) {
	        return category.getProducts(); // Sử dụng getter phù hợp để lấy danh sách sản phẩm trong danh mục.
	    }
	    return Collections.emptyList(); // Trả về danh sách trống nếu không tìm thấy danh mục.
	}

	
	
}
