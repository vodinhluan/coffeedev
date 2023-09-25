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
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userLuan = new User("luandinhvo2002@gmail.com","vodinhluan123", "Luan");
		userLuan.addRole(roleAdmin);

		User savedUser = repo.save(userLuan);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

//	@Test
//	public void testCreateUserWithTwoRoles()
//	{
//		User userHan = new User("dtgh1401@gmail.com", "dtgh2002","Dang Truong Gia", "Han");
//
//		Role roleEditor = new Role(4);
//		Role roleAssistant = new Role(6);
//
//		userHan.addRole(roleEditor);
//		userHan.addRole(roleAssistant);
//
//		User savedUser = repo.save(userHan);
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
//
//	@Test
//	public void testListAllUsers()
//	{
//		Iterable<User> listUsers = repo.findAll();
//		listUsers.forEach(user -> System.out.println(user));		
//	}
//
//	@Test
//	public void testGetUserById() {
//		User userBin = repo.findById(2).get();
//		System.out.println(userBin);
//		assertThat(userBin).isNotNull();
//	}
//
//	@Test
//	public void testUpdateUserDetail() {
//		User userBin = repo.findById(1).get();
//		userBin.setEnabled(true);
//		userBin.setEmail("luandeptrai@gmail.com");
//		repo.save(userBin);
//	}
//
//	@Test
//	public void testUpdateUserRoles() {
//		User userHan = repo.findById(2).get();
//		Role roleEditor = new Role(4);
//		Role roleShipper = new Role(5);
//		userHan.getRoles().remove(roleEditor);
//		userHan.addRole(roleShipper);
//		repo.save(userHan);
//	}
//
//	@Test
//	public void testDeleteUser() {
//		Integer userId = 2;
//		repo.deleteById(userId);
//
//	}

}