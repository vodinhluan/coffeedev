package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.coffeedev.admin.category.CategoryRepository;
import com.coffeedev.admin.category.CategoryService;
import com.coffeedev.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	@MockBean
	private CategoryRepository repo;
	@InjectMocks
	private CategoryService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Samsung";
		String alias = "abc";

		Category category = new Category(id, name);
		Mockito.when(repo.findByName(name)).thenReturn(category);
		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("DuplicateName");
	}

	@Test
	public void testCheckUniqueInNewModeReturnReturnOK() {
		Integer id = null;
		String name = "anbc";

		Category category = new Category(id, name);
		Mockito.when(repo.findByName(name)).thenReturn(null);


		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("OK");
	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id = 1;
		String name = "Samsung";
		String alias = "abc";

		Category category = new Category(2, name);
		Mockito.when(repo.findByName(name)).thenReturn(category);
		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("DuplicateName");
	}


	@Test
	public void testCheckUniqueInEditModeReturnReturnOK() {
		Integer id = 1;
		String name = "anbc";

		Category category = new Category(2, name);
		Mockito.when(repo.findByName(name)).thenReturn(null);
		String result = service.checkUnique(id, name);
		assertThat(result).isEqualTo("OK");
	}

}
