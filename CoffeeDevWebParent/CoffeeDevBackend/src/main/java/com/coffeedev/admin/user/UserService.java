package com.coffeedev.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.common.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	public List<User>listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
}
