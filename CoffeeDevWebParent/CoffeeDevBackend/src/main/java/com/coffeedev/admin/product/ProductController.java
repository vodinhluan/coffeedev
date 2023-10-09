package com.coffeedev.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffeedev.common.entity.Product;

@Controller
public class ProductController {
	@Autowired
	public ProductService service;
	
	@GetMapping("/products")	
	public String listFirstPage(Model model) {
		List<Product> listProducts = service.listAll();
 		model.addAttribute("listProducts", listProducts);
 		return "products/products";
//		return listByPage(1, model, "name", "desc", null);
	}
}
