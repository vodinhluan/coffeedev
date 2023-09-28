package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.coffeedev.common.entity.Role;
import com.coffeedev.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole()
	{
		Role roleStaff = entityManager.find(Role.class, 2);
		User userTest = new User("tranphuluan@gmail.com","tranphuluan123", "Tran Phu Luan");
		userTest.addRole(roleStaff);

		User savedUser = repo.save(userTest);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateUserWithTwoRoles()
	{
		User userHan = new User("minhnhat@gmail.com", "minhnhat123", "Nhat");

		Role roleAdmin = new Role(1);
		Role roleStaffPerson = new Role(2);

		userHan.addRole(roleAdmin);
		userHan.addRole(roleStaffPerson);

		User savedUser = repo.save(userHan);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers()
	{
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));		
	}

	@Test
	public void testGetUserById() {
		User userBin = repo.findById(2).get();
		System.out.println(userBin);
		assertThat(userBin).isNotNull();
	}

	@Test
	public void testUpdateUserDetail() {
		User userBin = repo.findById(1).get();
		userBin.setEnabled(true);
		userBin.setEmail("luandinhvo2002@gmail.com");
		repo.save(userBin);
	}

	@Test
	public void testUpdateUserRoles() {
		User userHan = repo.findById(2).get();
		Role roleEditor = new Role(4);
		Role roleShipper = new Role(5);
		userHan.getRoles().remove(roleEditor);
		userHan.addRole(roleShipper);
		repo.save(userHan);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);

	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}

	@Test
	public void testUsableUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, true);
	}

}