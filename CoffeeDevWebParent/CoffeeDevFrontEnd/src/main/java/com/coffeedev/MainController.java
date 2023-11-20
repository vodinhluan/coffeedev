package com.coffeedev;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffeedev.common.entity.Product;
import com.coffeedev.product.ProductService;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller
public class MainController {
	@GetMapping("/index")
	public String viewHomePage() {
		return "index";
	}
	
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}	
	
	
	@GetMapping("/about")
	public String viewAbout() {
		return "about";
	}
	
	@GetMapping("/shop")
	public String viewShop() {
		return "shop";
	}
	
	@GetMapping("/contact")
	public String viewServices() {
		return "contact";
	}
	
	
	
}