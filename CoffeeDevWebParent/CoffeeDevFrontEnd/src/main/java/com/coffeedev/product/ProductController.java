package com.coffeedev.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coffeedev.category.CategoryNotFoundException;
import com.coffeedev.category.CategoryService;
import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;

@Controller
public class ProductController {
	@Autowired private CategoryService categoryService;
	@Autowired private ProductService productService;


	@GetMapping("/{name}")
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
	
	
	
	@GetMapping("/trasua")
	public String viewTraSua(Model model) {
	        return "product/milktea";
	    }
	
	@GetMapping("/snack")
	public String viewSnack(Model model) {
	        return "product/snack";
	    }
	
	
}
