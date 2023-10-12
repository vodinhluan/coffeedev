package com.coffeedev.admin.product;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coffeedev.admin.category.CategoryRepository;
import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;


@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository cateRepo;

	public List<Product> listAll() {
		return (List<Product>) productRepo.findAll();
	}

	public List<Category> listCategories() {
		return (List<Category>) cateRepo.findAll();
	}

	public Product save(Product product) {
		return productRepo.save(product);
	}

	// Edit Product
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return productRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Không thể tìm thấy sản phẩm nào với ID: " + id);
		}
	}

	// Delete Product
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = productRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Không thể tìm thấy sản phẩm nào với ID: " + id);
		}
		productRepo.deleteById(id);
	}

	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		productRepo.updateEnabledStatus(id, enabled);	
	}

}
