package com.coffeedev.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.admin.category.CategoryRepository;
import com.coffeedev.common.entity.Product;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository cateRepo;
	
	public List<Product>listAll() {
		return (List<Product>) productRepo.findAll();
	}
	
	
}
