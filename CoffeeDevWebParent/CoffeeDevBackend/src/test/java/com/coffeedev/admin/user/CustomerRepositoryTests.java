package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.coffeedev.admin.customer.CustomerRepository;
import com.coffeedev.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

	@Autowired private CustomerRepository repo;
	@Autowired private TestEntityManager entityManager;

	@Test
	public void testCreateCustomer1() {

		Customer customer = new Customer();
		customer.setName("Nhat");
		customer.setPassword("minhnhat123");
		customer.setEmail("minhnhat@gmail.com.com");
		customer.setPhoneNumber("312-462-7518");
		customer.setAddress("Tan Phu");

		customer.setCreatedTime(new Date());

		Customer savedCustomer = repo.save(customer);

		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}

	

	@Test
	public void testListCustomers() {
		Iterable<Customer> customers = repo.findAll();
		customers.forEach(System.out::println);

		assertThat(customers).hasSizeGreaterThan(1);
	}

	@Test
	public void testUpdateCustomer() {
		Integer customerId = 1;
		String name = "Stanfield";

		Customer customer = repo.findById(customerId).get();
		customer.setName(name);
		customer.setEnabled(true);

		Customer updatedCustomer = repo.save(customer);
		assertThat(updatedCustomer.getName()).isEqualTo(name);
	}

	@Test
	public void testGetCustomer() {
		Integer customerId = 2;
		Optional<Customer> findById = repo.findById(customerId);

		assertThat(findById).isPresent();

		Customer customer = findById.get();
		System.out.println(customer);
	}

	@Test
	public void testDeleteCustomer() {
		Integer customerId = 2;
		repo.deleteById(customerId);

		Optional<Customer> findById = repo.findById(customerId);		
		assertThat(findById).isNotPresent();		
	}

	@Test
	public void testFindByEmail() {
		String email = "david.s.fountaine@gmail.com";
		Customer customer = repo.findByEmail(email);

		assertThat(customer).isNotNull();
		System.out.println(customer);		
	}

//	@Test
//	public void testFindByVerificationCode() {
//		String code = "code_123";
//		Customer customer = repo.findByVerificationCode(code);
//
//		assertThat(customer).isNotNull();
//		System.out.println(customer);		
//	}
//
//	@Test
//	public void testEnableCustomer() {
//		Integer customerId = 1;
//		repo.enable(customerId);
//
//		Customer customer = repo.findById(customerId).get();
//		assertThat(customer.isEnabled()).isTrue();
//	}
}
