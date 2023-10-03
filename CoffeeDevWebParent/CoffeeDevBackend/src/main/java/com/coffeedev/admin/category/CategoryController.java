package com.coffeedev.admin.category;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffeedev.admin.FileUploadUtil;
import com.coffeedev.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping("/categories")
	public String listAll(Model model) {
		List<Category> listCategories = service.listAll();
		model.addAttribute("listCategories",listCategories);
		return "categories/categories";
	}

//	@GetMapping("/categories/{id}/enabled/{status}")
//	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
//			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
//		service.updateCategoryEnabledStatus(id, enabled);
//		String status = enabled ? "enabled" : "disabled";
//		String message = "The category ID " + id + " has been " + status;
//		redirectAttributes.addFlashAttribute("message", message);
//		return "redirect:/categories";
//	}

	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		List<Category> listCategories = service.listCategoriesUsedInform();

		model.addAttribute("category", new Category());
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("pageTitle","Create New Category");
		return "categories/category_form";		
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(Category category, 
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(fileName);

		Category savedCategory = service.save(category);
		String uploadDir = "../category-images/" + savedCategory.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		ra.addFlashAttribute("message", "The category has been saved successfully.");
		return "redirect:/categories";
	}

	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		List<Category> listCategories = service.listCategoriesUsedInform();

		try {
			Category category = service.get(id);
			model.addAttribute("category", category);
			model.addAttribute("listCategories",listCategories);

			model.addAttribute("pageTitle", "Edit Category (ID = "+id+")");
			return "categories/category_form";

		} catch(CategoryNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
	 		return "redirect:/categories";
		}
	}	

	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		try {
			service.delete(id);
	 		redirectAttributes.addFlashAttribute("message", 
	 				"Category với ID " + id + " đã xóa thành công");
		} catch(CategoryNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
 		return "redirect:/categories";
	}

	@GetMapping("/categories/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCategoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The category ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/categories";
	}



}