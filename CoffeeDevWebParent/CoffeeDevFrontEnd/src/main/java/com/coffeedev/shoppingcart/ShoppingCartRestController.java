package com.coffeedev.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.exception.CustomerNotFoundException;
import com.coffeedev.customer.CustomerService;
import com.coffeedev.setting.Utilities;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ShoppingCartRestController {
	@Autowired private ShoppingCartService cartService;
	@Autowired private CustomerService customerService;

	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {

		try {
			Customer customer = getAuthenticatedCustomer(request);
			Integer updatedQuantity = cartService.addProduct(productId, quantity, customer);

			return updatedQuantity + " item(s) of this product were added to your shopping cart.";
		} catch (CustomerNotFoundException ex) {
			return "You must login to add this product to cart.";
		} catch (ShoppingCartException ex) {
			return ex.getMessage();
		}

	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) 
			throws CustomerNotFoundException {
		String email = Utilities.getEmailOfAuthenticatedCustomer(request);
		if (email == null) {
			throw new CustomerNotFoundException("No authenticated customer");
		}

		return customerService.getCustomerByEmail(email);
	}
	
	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		try {
			Customer customer = getAuthenticatedCustomer(request);
			float subtotal = cartService.updateQuantity(productId, quantity, customer);

			return String.valueOf(subtotal);
		} catch (CustomerNotFoundException ex) {
			return "You must login to change quantity of product.";
		}	
	}
	
	@DeleteMapping("/cart/remove/{productId}")
 	public String removeProduct(@PathVariable("productId") Integer productId,
 			HttpServletRequest request) {
		System.out.println("ABCXYZ");
 		try {
 			Customer customer = getAuthenticatedCustomer(request);
 			cartService.removeProduct(productId, customer);
 			System.out.println("Xoa san pham thanh cong");
 			return "The product has been removed from your shopping cart.";

 		} catch (CustomerNotFoundException e) {
 			System.out.println("Khong xoa san pham thanh cong");
 			return "You must login to remove product.";
 		}
 	}
}
