package com.coffeedev.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;
import com.coffeedev.common.exception.ProductNotFoundException;

@Service
public class ProductService {
	@Autowired private ProductRepository repo;
	

	 public List<Product> listProductsByCategoryName(String categoryName) {
	        return repo.findAllByCategoryName(categoryName);
	 }
	
	public List<Product> listAll() {
        return (List<Product>) repo.findAll();
    }
	
	public Optional<Product> getProductByAlias(String productAlias) {
	    return repo.findByAlias(productAlias);
	}


	

}
