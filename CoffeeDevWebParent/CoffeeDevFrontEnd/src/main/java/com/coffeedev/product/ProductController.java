package com.coffeedev.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coffeedev.category.CategoryNotFoundException;
import com.coffeedev.category.CategoryService;
import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.Product;
import com.coffeedev.common.exception.ProductNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductController {
	@Autowired private CategoryService categoryService;
	@Autowired private ProductService productService;


	@GetMapping("/c/{name}")
	public String viewCategoryByPage(@PathVariable("name") String name, Model model) throws CategoryNotFoundException {
	    Category category = categoryService.getCategory(name);
	    if (category == null) {
	        return "error/404";
	    }

	    List<Product> listProducts = productService.listProductsByCategoryName(name);

	    model.addAttribute("listProducts", listProducts);
	    model.addAttribute("category", category);

	    return "product/products_by_category";
	}


	@GetMapping("")
	public String viewHomePage(Model model) {
	        List<Product> listProducts = productService.listAll();
	        model.addAttribute("listProducts", listProducts);
	        return "index";
	    }
	
	@GetMapping("/product_detail/{alias}")
	public String viewProductDetail(@PathVariable("alias") String alias, Model model) throws ProductNotFoundException {
		 Optional<Product> product = productService.getProductByAlias(alias);
		    if (product.isEmpty()) {
		        return "error/404"; // Handle the case where the product is not found
		    }

		    model.addAttribute("product", product.get());
		    return "product/product_detail";
	}
	
}
