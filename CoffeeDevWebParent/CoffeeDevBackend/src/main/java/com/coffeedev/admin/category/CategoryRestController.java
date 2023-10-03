package com.coffeedev.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;

public class CategoryRestController {

	@Autowired
	public CategoryService service;

	@PostMapping("/categories/check_unique")
	public String checkUnique(@Param("id") Integer id,
			@Param("name") String name) {


		return service.checkUnique(id, name);
	}
}