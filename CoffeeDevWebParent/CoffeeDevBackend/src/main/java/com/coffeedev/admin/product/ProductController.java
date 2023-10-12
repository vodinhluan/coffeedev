package com.coffeedev.admin.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.coffeedev.admin.FileUploadUtil;
import com.coffeedev.admin.category.CategoryNotFoundException;
import com.coffeedev.common.entity.Category;
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
	
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		List<Category> listCategories = service.listCategories(); 
		Product product = new Product();
		product.setEnabled(true);
		model.addAttribute("product", product);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Create New Product");
		return "products/product_form";
	}
	
	
	@PostMapping("/products/save")
	public String saveProduct(Product product, 
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		product.setImage(fileName);

		Product savedProduct = service.save(product);
		String uploadDir = "../product-images/" + savedProduct.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		ra.addFlashAttribute("message", "Sản phẩm được lưu thành công!");
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable


		try {
			Product product = service.get(id);
			List<Category> listCategories = service.listCategories(); 

			model.addAttribute("product", product);
			model.addAttribute("listCategories", listCategories);

			model.addAttribute("pageTitle", "Edit Product (ID = "+id+")");
			return "products/product_form";

		} catch(ProductNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
	 		return "redirect:/products";
		}
	}	

	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		try {
			service.delete(id);
			String productDir = "../product-images/" + id;
			FileUploadUtil.removeDir(productDir);

			redirectAttributes.addFlashAttribute("message", 
					"Sản phẩm với ID " + id + " đã được xóa thành công");
		} catch (ProductNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		} 
 		return "redirect:/products";
	}

	@GetMapping("/products/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "kích hoạt" : "vô hiệu hóa";
		String message = "Sản phẩm với ID " + id + " đã " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/products";
	}

	
	
}
