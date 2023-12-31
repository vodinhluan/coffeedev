package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.annotation.Rollback;
import com.coffeedev.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	private RoleRepository repo;
	
	@Autowired
	public RoleRepositoryTests(RoleRepository repo) {
	     this.repo = repo;
	}
	
	@Test
	public void testCreateFirstRole()
	{
		Role roleAdmin = new Role("Admin", "Manage everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateRestRole()
	{
		Role roleStaffPerson = new Role("SalePerson", "manage product price, "
				+ "customers, shipping, orders and sales report");

	
		Role roleShipper = new Role("Shipper", "view products, view orders, "
				+ "and update order status");


		repo.saveAll(List.of(roleStaffPerson, roleShipper));
	}		
	



}
