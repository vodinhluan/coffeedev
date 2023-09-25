package com.coffeedev.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffeedev.common.entity.User;

import org.springframework.ui.Model;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listUsers(Model model) {
//		List<User> listUsers = service.listAll();
//		model.addAttribute(listUsers);
		return "users"; 
	}
}
