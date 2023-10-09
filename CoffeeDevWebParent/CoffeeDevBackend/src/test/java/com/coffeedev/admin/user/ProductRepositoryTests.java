package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.data.domain.PageRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.coffeedev.admin.product.ProductRepository;
import com.coffeedev.common.entity.Product;
import com.coffeedev.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
	@Autowired
	private ProductRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		Category cate = entityManager.find(Category.class, 3);
		Product productTest = new Product();
		productTest.setName("Ca phe muoi");
		productTest.setDescription("Day la Ca phe muoi");
		productTest.setPrice(25.000);
		productTest.setCreateTime(new Date());
		productTest.setEnabled(true);
		productTest.setPhoto("4");
		productTest.setSize("S");
		productTest.setCategory(cate);

		Product savedProduct = repo.save(productTest);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllProduct() {
		Iterable<Product> iterableProducts=repo.findAll();
		iterableProducts.forEach(System.out::println);
	}
	
	@Test
	public void testGetProduct() {
		Integer id = 1;
		Product product=repo.findById(id).get();
		System.out.println(product);
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testUpdateProduct() {
		Integer id=1;
		Product product=repo.findById(id).get();
		product.setPrice(16.000);
		repo.save(product);
		
		Product updatedProduct=entityManager.find(Product.class, id);
		assertThat(updatedProduct.getPrice()).isEqualTo(16.000);
	}
	
	@Test
	public void testDeleteProduct() {
		Integer id=4;
		repo.deleteById(id);
		
		Optional<Product> result=repo.findById(id);
		assertThat(!result.isPresent());
	}
	
	@Test
	public void testSaveProductWithImage() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();
		product.setPhoto("main_image.png");
		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getPhoto()).isEqualTo("main_image.png");
	}
	
	
}