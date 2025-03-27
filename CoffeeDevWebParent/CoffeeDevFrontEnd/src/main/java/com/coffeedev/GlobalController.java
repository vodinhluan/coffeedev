package com.coffeedev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.coffeedev.category.CategoryService;
import com.coffeedev.common.entity.Category;

import org.springframework.ui.Model;
import java.util.List;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        List<Category> listCategories = categoryService.getAllParentCategories();
        model.addAttribute("listCategories", listCategories);
    }
}
