package com.coffeedev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/index.html")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/menu.html")
	public String viewMenu() {
		return "menu";
	}
	
	@GetMapping("/services.html")
	public String viewService() {
		return "services";
	}
	
	@GetMapping("/cart.html")
	public String viewCart() {
		return "cart";
	}
	
	@GetMapping("/blog.html")
	public String viewBlog() {
		return "blog";
	}
	
	@GetMapping("/about.html")
	public String viewAbout() {
		return "about";
	}
	
	@GetMapping("/checkout.html")
	public String viewCheckout() {
		return "checkout";
	}
	
	@GetMapping("/contact.html")
	public String viewContact() {
		return "contact";
	}
	
	@GetMapping("/shop.html")
	public String viewShop() {
		return "shop";
	}
	
	@GetMapping("/product-single.html")
	public String viewProductSingle() {
		return "product-single";
	}
	
	@GetMapping("/login.html")
	public String viewLogin() {
		return "login";
	}
	
	
	
}
